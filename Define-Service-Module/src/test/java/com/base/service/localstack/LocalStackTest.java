package com.base.service.localstack;

import com.base.service.BaseTest;
import com.base.service.service.S3Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
public class LocalStackTest extends BaseTest {
    @Autowired
    private S3Service s3Service;
    static DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:0.10.6");
    @Container
    public static final LocalStackContainer localstack = new LocalStackContainer(localstackImage)
            .withServices(LocalStackContainer.Service.S3)
            .withEnv("SERVICES", "s3:4566")
            .withEnv("HOSTNAME_EXTERNAL", "localhost")
            .withEnv("DEBUG", "1")
            .withEnv("PORT_WEB_UI", "8082")
            .withEnv("AWS_DEFAULT_REGION", "us-east-1")
            .withEnv("AWS_ACCESS_KEY_ID", "AAAAAAAAAAAAAAAAAAAA")
            .withEnv("DOCKER_HOST", "unix:///var/run/docker.sock");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        Startables.deepStart( localstack).join();
        registry.add("s3.base-url", () -> localstack.getEndpointOverride(LocalStackContainer.Service.S3).toString());
        registry.add("s3.region", () -> localstack.getRegion());
    }

    @Test
    void testHelloServerOne() {
        String bucketName = "my-bucket";
        String nameFile = "file";
        String fileName = "example.txt";
        byte[] content = "Hello, World!".getBytes(StandardCharsets.UTF_8);
        MockMultipartFile file1 = new MockMultipartFile(nameFile, fileName, "text/plain", content);
        s3Service.createBucket(bucketName);
        s3Service.uploadFile(file1, bucketName);
        var file2 = s3Service.getFile(fileName, bucketName);
        Assertions.assertEquals(true, Arrays.equals(content, file2));

    }

}

