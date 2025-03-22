package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ClientConfig {
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";
    public static final String API_VERSION = "api/v1";

    public static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(API_VERSION)
                .setContentType(ContentType.JSON)
                .build();
    }
}