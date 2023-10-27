package io.quarkiverse.semantickernel.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SemanticKernelResourceTest {

    @Test
    public void testHelloWithSemanticKernelEndpoint() {
        given()
                .when().get("/semantic-kernel/hello")
                .then()
                .statusCode(200)
                .body(is("Hello with Semantic Kernel"));
    }

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/semantic-kernel")
                .then()
                .statusCode(200)
                .body(is("Hello semantic-kernel"));
    }
}
