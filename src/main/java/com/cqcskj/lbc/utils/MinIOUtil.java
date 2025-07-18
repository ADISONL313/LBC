package com.cqcskj.lbc.utils;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import io.minio.RemoveObjectArgs;
import io.minio.messages.Item;

@Component
public class MinIOUtil {
    @Resource
    private MinioClient minioClient;

    public String uploadFiles(MultipartFile[] files,String bucketName,String fileId) {
        // 输入参数检查
        if (files == null || files.length == 0) {
            System.out.println("传入的文件数组为空，无法进行上传操作。");
            return "错误";
        }

        // 检查存储桶是否存在，不存在则创建
        try {
            if (!minioClient.bucketExists(io.minio.BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(io.minio.MakeBucketArgs.builder().bucket(bucketName).build());

                System.out.println("存储桶"+bucketName+"不存在，已成功创建。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("检查或创建存储桶时发生错误");
            return "错误";
        }

        StringBuilder fileNames = new StringBuilder();
        String returnName="";
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                System.out.println("文件为空跳过上传");
                continue;
            }
            String objectName = generateUniqueObjectName(file.getOriginalFilename());
            returnName=returnName+objectName+";";
            objectName=fileId+"/"+objectName;
            try (InputStream inputStream = file.getInputStream()) {
                PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build();
                minioClient.putObject(putObjectArgs);
            } catch (Exception e) {
                System.out.println("上传文件时出错");
                return "错误";
            }
        }
        return returnName;
    }

    private String generateUniqueObjectName(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }

    //删除
    public boolean delFolder(String bucketName,String fileId) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // 列出指定文件夹下的所有对象
            Iterable<Result<Item>> results = minioClient.listObjects(
                    io.minio.ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(fileId + "/") // 指定前缀，用于筛选特定文件夹下的对象
                            .recursive(true) // 递归列出所有子文件夹中的对象
                            .build());

            // 遍历对象列表并删除每个对象
            for (Result<Item> result : results) {
                Item item = result.get();
                String objectName = item.objectName();
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .build());
                System.out.println("已删除对象: " + objectName);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteFile(String bucketName,String objectName) {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build();
            minioClient.removeObject(removeObjectArgs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
