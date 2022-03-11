package com.thegreatapi.travelexample.travelagency;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusIntegrationTest
class GreetRestIT {

    @Test
    void testGreetRest() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"workflowdata\" : {\"name\" : \"John\", \"language\":\"English\"}}").when()
                .post("/jsongreet")
                .then()
                .statusCode(201)
                .body("workflowdata.greeting", containsString("Hello"));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"workflowdata\" : {\"name\" : \"Javierito\", \"language\":\"Spanish\"}}").when()
                .post("/jsongreet")
                .then()
                .statusCode(201)
                .body("workflowdata.greeting", containsString("Saludos"));
    }
}
