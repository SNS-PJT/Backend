package com.project.sns.infra.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.project.sns.global.exception.S3UploadFailException;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    // S3 업로드 인터페이스
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(String directory, MultipartFile multipartFile) {

        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        String fileName = makeFileName(directory, multipartFile);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(),
                            objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new S3UploadFailException("S3 업로드에 실패했습니다.");
        }

        return fileName;
    }

    private String makeFileName(String directory, MultipartFile multipartFile) {
        if (multipartFile == null) {
            return null;
        }
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        return directory + UUID.randomUUID() + "." + extension;
    }

}
