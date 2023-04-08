package com.base.service.api_first;

import com.base.service.BaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.testcontainers.shaded.org.hamcrest.MatcherAssert;

class OpenApiTest extends BaseTest {
    @Test
    void testHelloServerOne() {
        String response = RestAssured.given()
                .queryParam("departmentId", "vn")
                .header("Authorization", "authorization")
                .when()
                .get("/api/v3/randomEmployee")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        MatcherAssert.assertThat(response, response.contains("name"));
    }

}
