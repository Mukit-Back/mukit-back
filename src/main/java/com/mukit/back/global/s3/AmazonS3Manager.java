package com.mukit.back.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mukit.back.global.apiPayload.code.S3ErrorCode;
import com.mukit.back.global.apiPayload.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

    private final AmazonS3 amazonS3;
    private final AmazonConfig amazonConfig;

    public String uploadMenuImage(MultipartFile file) {
        return uploadFile(
                generateKeyName(S3Directory.MENU, file.getOriginalFilename()),
                file);
    }

    public String uploadShopImage(MultipartFile file) {
        return uploadFile(
                generateKeyName(S3Directory.SHOP, file.getOriginalFilename()), file);
    }

    public void deleteFileByUrl(String url) {
        deleteFile(getKeyName(url));
    }

    private String uploadFile(String keyName, MultipartFile file) throws S3Exception {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(
                    new PutObjectRequest(
                            amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));

            return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
        } catch (IOException e) {
            throw new S3Exception(S3ErrorCode.S3_UPLOAD_FAIL);
        }
    }

    private void deleteFile(String keyName) throws S3Exception {
        try {
            if (amazonS3.doesObjectExist(amazonConfig.getBucket(), keyName)) {
                amazonS3.deleteObject(amazonConfig.getBucket(), keyName);
            } else {
                throw new IOException();
            }
        } catch (Exception e) {
            throw new S3Exception(S3ErrorCode.S3_OBJECT_NOT_FOUND);
        }
    }

    private String getKeyName(String fileUrl) {
        Pattern regex = Pattern.compile(getPattern());
        Matcher matcher = regex.matcher(fileUrl);

        String keyName = null;
        if (matcher.find()) {
            keyName = matcher.group(1).substring(1);
        }

        return keyName;
    }

    private String getPattern() {
        return "https://"
                + amazonConfig.getBucket()
                + "\\.s3\\."
                + amazonConfig.getRegion()
                + "\\.amazonaws\\.com(.*)";
    }

    private String generateKeyName(S3Directory dir, String filename) {
        return dir.getDirectory() + "/" + UUID.randomUUID() + "_" + filename;
    }
}
