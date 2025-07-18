package com.cqcskj.lbc.utils;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

@Component
public class NginxUtil {
    private static final String ECS_HOST = "47.108.29.91";
    private static final int ECS_PORT = 22;
    private static final String ECS_USER = "root";
    private static final String ECS_PASSWORD = "ADISON313.com.cn";
    private static final String NGINX_PATH = "/usr/share/nginx/html/";

    public String uploadToNginx(MultipartFile[] multipartFiles, String whatFile, String fileFolderName) {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            // 创建 SSH 会话
            session = jsch.getSession(ECS_USER, ECS_HOST, ECS_PORT);
            session.setPassword(ECS_PASSWORD);
            // 避免 SSH 密钥检查提示
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            // 连接到 SSH 服务器
            session.connect();
            // 打开 SFTP 通道
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // 构建最终存储路径
            String finalPath = NGINX_PATH + whatFile + "/" + fileFolderName;
            try {
                // 尝试切换到目标目录
                channelSftp.cd(finalPath);
            } catch (SftpException e) {
                // 若目录不存在则创建
                channelSftp.mkdir(finalPath);
                channelSftp.cd(finalPath);
            }

            String fileNameForDatabase = "";
            for (MultipartFile file : multipartFiles) {
                // 获取原始文件名
                String fileName = file.getOriginalFilename();
                // 生成唯一文件名
                String fileNameForNginx = UUID.randomUUID() + fileName;
                // 获取文件输入流
                InputStream inputStream = file.getInputStream();
                // 拼接存储到数据库的文件名
                fileNameForDatabase = fileNameForDatabase + fileNameForNginx + ";";
                // 上传文件到 SFTP 服务器
                channelSftp.put(inputStream, fileNameForNginx);
                inputStream.close();
            }
            return fileNameForDatabase;
        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
            return "失败";
        } finally {
            // 关闭 SFTP 通道
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            // 关闭 SSH 会话
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
    public boolean delFile(String newID,String whatFile){
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            session = jsch.getSession(ECS_USER, ECS_HOST, ECS_PORT);
            session.setPassword(ECS_PASSWORD);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            String folderPath=NGINX_PATH+whatFile+"/"+newID;
            System.out.println(folderPath);
            // 先删除文件夹内的所有内容
            deleteFolderContents(channelSftp, folderPath);
            // 再删除文件夹本身
            channelSftp.rmdir(folderPath);
            return true;
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (channelSftp!= null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session!= null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    private void deleteFolderContents(ChannelSftp channelSftp, String folderPath) throws SftpException {
        SftpATTRS attrs = channelSftp.lstat(folderPath);
        if (attrs.isDir()) {
            // 列出文件夹内容
            java.util.Vector<ChannelSftp.LsEntry> entries = channelSftp.ls(folderPath);
            for (ChannelSftp.LsEntry entry : entries) {
                if (!".".equals(entry.getFilename()) &&!"..".equals(entry.getFilename())) {
                    String subPath = folderPath + "/" + entry.getFilename();
                    if (entry.getAttrs().isDir()) {
                        deleteFolderContents(channelSftp, subPath);
                        channelSftp.rmdir(subPath);
                    } else {
                        channelSftp.rm(subPath);
                    }
                }
            }
        }
    }

    //删除单个文件
    public boolean delSingleFile(String filePath,String fileName) {
        Session session = null;
        try {
            // 创建 JSch 实例
            JSch jsch = new JSch();
            // 获取 SSH 会话
            session = jsch.getSession(ECS_USER, ECS_HOST, ECS_PORT);
            // 设置登录密码
            session.setPassword(ECS_PASSWORD);

            // 避免 SSH 连接时的主机密钥检查提示
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // 建立 SSH 会话连接
            session.connect();

            // 打开一个执行命令的通道
            Channel channel = session.openChannel("exec");
            // 设置要执行的删除文件命令
            ((ChannelExec) channel).setCommand("rm -f " + NGINX_PATH + filePath+"/"+fileName);

            try (InputStream in = channel.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                // 连接通道
                channel.connect();

                // 读取命令执行结果
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                // 等待命令执行完成
                while (!channel.isClosed()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("等待命令执行时线程被中断: " + e.getMessage());
                        return false;
                    }
                }

                // 获取命令执行的退出状态码
                int exitStatus = channel.getExitStatus();
                return exitStatus == 0;
            } finally {
                // 断开通道连接
                if (channel != null) {
                    channel.disconnect();
                }
            }
        } catch (JSchException | IOException e) {
            System.err.println("执行远程文件删除时发生错误: " + e.getMessage());
            return false;
        } finally {
            // 断开会话连接
            // 由于 session 在 try 块外部声明，这里可以确保在任何情况下都尝试断开
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}