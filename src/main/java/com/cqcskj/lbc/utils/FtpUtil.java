    package com.cqcskj.lbc.utils;
    import jakarta.annotation.Resource;
    import org.apache.commons.net.ftp.FTPFile;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;
    import org.springframework.web.multipart.MultipartFile;
    import org.apache.commons.net.ftp.FTP;
    import org.apache.commons.net.ftp.FTPClient;
    import java.io.IOException;
    import java.io.InputStream;
    import java.net.SocketException;
    import java.net.URLEncoder;
    import java.nio.charset.StandardCharsets;
    import java.util.UUID;

    @Component
    public class FtpUtil {
        @Value("${ftp.server}")
        private String ftpServer;

        @Value("${ftp.port}")
        private int ftpPort;

        @Value("${ftp.username}")
        private String ftpUsername;

        @Value("${ftp.password}")
        private String ftpPassword;

        @Value("${ftp.remote.directory}")
        private String remoteDirectory;
        public String uploadToFtd(MultipartFile[] multipartFiles,String folderName,String whatFile){
            FTPClient ftpClient=new FTPClient();
            try {
                // 连接到 FTP 服务器
                ftpClient.connect(ftpServer, ftpPort);
                // 登录 FTP 服务器
                if (!ftpClient.login(ftpUsername, ftpPassword)) {
                    return "FTP登录失败";
                }
                // 设置文件传输类型为二进制
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
                // 切换到指定的远程目录
                String finalFile=remoteDirectory+"/"+whatFile+"/"+folderName;
                System.out.println(finalFile);
                if (!ftpClient.changeWorkingDirectory(finalFile)) {
                    if (ftpClient.makeDirectory(finalFile)) {
                        ftpClient.changeWorkingDirectory(finalFile);
                    } else {
                        System.out.println(finalFile);
                        return "目录出错";
                    }
                }
                String totalFileName="";
                for (MultipartFile file : multipartFiles) {
                    try (InputStream inputStream = file.getInputStream()) {
                        // 上传文件到 FTP 服务器
                        String encodedFileName = URLEncoder.encode(UUID.randomUUID().toString()+"_"+ file.getOriginalFilename(), StandardCharsets.UTF_8);
                        totalFileName=finalFile+"/"+encodedFileName+";"+totalFileName;
                        if (!ftpClient.storeFile(encodedFileName, inputStream)) {
                            return "文件 " + encodedFileName + " 上传失败";
                        }
                    }
                }

                // 从 FTP 服务器登出
                ftpClient.logout();
                return totalFileName;
            } catch (IOException e) {
                e.printStackTrace();
                return "I/O异常";
            } finally {
                if (ftpClient.isConnected()) {
                    try {
                        // 断开与 FTP 服务器的连接
                        ftpClient.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        public boolean deleteFolder(String newID,String whatFile) {
            String remoteFolderPath="/var/ftp/ADISON/"+whatFile+"/"+newID;
            System.out.println(remoteFolderPath);
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(ftpServer, ftpPort);
                // 登录 FTP 服务器
                if (!ftpClient.login(ftpUsername, ftpPassword)) {
                    return false;
                }
                ftpClient.enterLocalPassiveMode();
                // 先清空文件夹内容
                emptyFolder(ftpClient, remoteFolderPath);
                // 再删除文件夹
                boolean success = ftpClient.removeDirectory(remoteFolderPath);
                if (!success) {
                    System.err.println("删除文件夹失败: " + remoteFolderPath);
                }
                ftpClient.logout();
                return success;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (ftpClient.isConnected()) {
                    try {
                        ftpClient.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private static void emptyFolder(FTPClient ftpClient, String folderPath) throws IOException {
            FTPFile[] files = ftpClient.listFiles(folderPath);
            for (FTPFile file : files) {
                String filePath = folderPath + "/" + file.getName();
                if (file.isDirectory()) {
                    // 递归删除子文件夹
                    emptyFolder(ftpClient, filePath);
                    ftpClient.removeDirectory(filePath);
                } else {
                    // 删除文件
                    ftpClient.deleteFile(filePath);
                }
            }
        }
    }
