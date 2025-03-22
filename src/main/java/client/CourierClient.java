package client;

import io.restassured.response.Response;
import model.Courier;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CourierClient extends ClientConfig {
    private final String COURIER_PATH = "/courier";

    public Response create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH);
    }

    public Response login(String login, String password) {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("login", login);
        credentials.put("password", password);
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "/login");
    }

    public void delete(int courierId) {
        given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + '/' + courierId);
    }
}