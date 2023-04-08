package com.base.service.http_interface;

import com.base.service.BaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.shaded.org.hamcrest.MatcherAssert;
import org.testcontainers.utility.DockerImageName;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Testcontainers
public class HttpInterfaceTest extends BaseTest {
    @Override
    @BeforeAll
    public void beforeClass() {
        super.beforeClass();
        mockServerOne.start();
        mockServerTwo.start();

        new MockServerClient(mockServerOne.getHost(), mockServerOne.getServerPort())
                .when(request()
                        .withPath("/api/v1/hello"))
                .respond(response().withBody("Hello from server one!"));
        new MockServerClient(mockServerTwo.getHost(), mockServerTwo.getServerPort())
                .when(request()
                        .withPath("/api/v1/hello")
                        .withQueryStringParameter("language", "vn")
                        .withHeader("authorization", "authorization")
                )
                .respond(response().withBody("Hello from server two!"));
    }

    public static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
            .parse("mockserver/mockserver")
            .withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());

    @Container
    private static final MockServerContainer mockServerOne = new MockServerContainer(MOCKSERVER_IMAGE)
            .withAccessToHost(true)
            .withReuse(true);
    @Container
    private static final MockServerContainer mockServerTwo = new MockServerContainer(MOCKSERVER_IMAGE)
            .withAccessToHost(true)
            .withReuse(true);
    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        Startables.deepStart(mockServerOne, mockServerTwo).join();
        registry.add("client.server-one.base-url", mockServerOne::getEndpoint);
        registry.add("client.server-two.base-url", mockServerTwo::getEndpoint);
    }

    @Test
    void testHelloServerOne() {
        String response = RestAssured.given()
                .when()
                .get("/api/v1/hi")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        MatcherAssert.assertThat(response, response.equals("Hello from server one!"));
    }

    @Test
    void testHelloServerTwo() {
        String response = RestAssured.given()
                .queryParam("language", "vn")
                .header("authorization", "authorization")
                .when()
                .get("/api/v2/hi")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        MatcherAssert.assertThat(response, response.equals("Hello from server two!"));
    }
}
