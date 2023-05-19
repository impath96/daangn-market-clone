package com.zerobase.daangnmarketclone.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.zerobase.daangnmarketclone.exception.CustomException;
import com.zerobase.daangnmarketclone.exception.ErrorCode;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader implements ImageUploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String DIR_NAME = "profile";

    /**
     * @param multipartFile 입력받은 multipartFile
     * @return 업로드된 이미지 경로(S3 url 경로)
     * @throws IOException
     */
    @Override
    public String upload(MultipartFile multipartFile) {

        ObjectMetadata objectMetadata = createObjectMetadata(multipartFile);

        return upload(multipartFile, objectMetadata);

    }

    private static ObjectMetadata createObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }

    private String upload(MultipartFile multipartFile, ObjectMetadata metadata) {

        String fileName = DIR_NAME + "/" + UUID.randomUUID() +  multipartFile.getOriginalFilename();

        return putS3(multipartFile, metadata, fileName);
    }

    private String putS3(MultipartFile multipartFile, ObjectMetadata metadata, String fileName) {

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            log.info("S3에 파일 업로드 실패" + e.getMessage());
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_FAIL);
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
