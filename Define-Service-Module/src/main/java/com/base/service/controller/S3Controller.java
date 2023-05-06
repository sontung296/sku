package com.base.service.controller;

import com.base.service.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v4/localstack")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping(value = "/create")
    public String createBucket(
            @RequestParam(name = "bucketName") String bucketName
    ){
        s3Service.createBucket(bucketName);
        return "Successfully to created bucket!";
    }

    @DeleteMapping(value = "/delete")
    public String deleteBucket(
            @RequestParam(name = "bucketName") String bucketName
    ){
        s3Service.deleteBucket(bucketName);
        return "Successfully to deleted bucket!";
    }

    @PostMapping(value = "/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "bucketName") String bucketName
    ){
        s3Service.uploadFile(file, bucketName);
        return "Successfully to upload file!";
    }

    @GetMapping(value = "/get-file", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getFile(
            @RequestParam(name = "fileName") String fileName,
            @RequestParam(name = "bucketName") String bucketName
    ){
        return s3Service.getFile(fileName, bucketName);
    }
}
