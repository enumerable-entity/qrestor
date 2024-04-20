package com.qrestor.sellpoint.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    @Value("${app.minio.server.url}")
    private String serverUrl;

    @Value("${app.minio.server.secret-key}")
    private String minIoSecretKey;

    @Value("${app.minio.server.access-key}")
    private String minIoAccessKey;

    public String uploadFile(MultipartFile file, String bucketName) {
        String extension = getFileExtension(file);

        var savedFileName = UUID.randomUUID().toString().concat(".").concat(extension);
        MinioClient minioClient = MinioClient.builder()
                                             .endpoint(serverUrl)
                                             .credentials(minIoAccessKey, minIoSecretKey)
                                             .build();

        try {
            minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket(bucketName)
                    .object(savedFileName)
                    .stream(file.getInputStream(), file.getSize(), -1).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedFileName;
    }

    private String getFileExtension(MultipartFile file) {
        String extension = "";
        int lastIndex = file.getOriginalFilename().lastIndexOf(".");

        if (lastIndex > 0) {
            extension = file.getOriginalFilename().substring(lastIndex + 1);
        }
        return extension;
    }
}
