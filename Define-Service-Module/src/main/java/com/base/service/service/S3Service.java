package com.base.service.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    public String createBucket(String bucketName){
        if(!amazonS3.doesBucketExistV2(bucketName)){
            amazonS3.createBucket(bucketName);
        }
        return bucketName;
    }

    public String deleteBucket(String bucketName){
        if(amazonS3.doesBucketExistV2(bucketName)){
            amazonS3.deleteBucket(bucketName);
        }
        return bucketName;
    }

    public void uploadFile(MultipartFile file, String bucketName) {
        try {
            PutObjectRequest request = new PutObjectRequest(bucketName, file.getOriginalFilename(), file.getInputStream(), null);
            amazonS3.putObject(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getFile(String fileName, String bucketName) {
        try {
            GetObjectRequest request = new GetObjectRequest(bucketName, fileName);
            S3Object s3Object = amazonS3.getObject(request);
            return IOUtils.toByteArray(s3Object.getObjectContent());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
