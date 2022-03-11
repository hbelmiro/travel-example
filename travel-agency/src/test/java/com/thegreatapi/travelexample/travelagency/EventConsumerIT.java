package com.thegreatapi.travelexample.travelagency;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.jackson.JsonFormat;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusIntegrationTest
class EventConsumerIT {

    private static final String CHANNEL_NAME = "move";

    @BeforeAll
    static void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void testGreetRest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(generateCloudEvent())
                .post("/")
                .then()
                .statusCode(202);
    }

    private static String generateCloudEvent() {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(JsonFormat.getCloudEventJacksonModule())
                                                      .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
                                                              true);

        try {
            return objectMapper.writeValueAsString(
                    CloudEventBuilder.v1()
                                     .withId(UUID.randomUUID().toString())
                                     .withSource(URI.create(""))
                                     .withType(CHANNEL_NAME)
                                     .withTime(OffsetDateTime.now())
                                     .withData(objectMapper.writeValueAsBytes(Collections.singletonMap(CHANNEL_NAME,
                                             "Hello Serverless Workflow")))
                                     .build());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
