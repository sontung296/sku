package com.base.service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class BaseTest {
    @LocalServerPort
    private int port;

    @BeforeAll
    public void beforeClass() {
        setBaseURI("http://localhost:" + port);
    }

    private static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }
}
