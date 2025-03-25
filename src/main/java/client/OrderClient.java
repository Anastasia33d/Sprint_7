// В классе OrderClient добавляем метод для GET запроса списка заказов
package client;

import io.restassured.response.Response;
import model.Order;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderClient extends ClientConfig {
    private final String ORDER_PATH = "/orders";
    private final String ORDER_CANCEL_PATH = "cancel";

    public Response createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH);
    }

    public Response getOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH);
    }

    public Response cancelOrder(String track) {
        Map<String, String> payload = new HashMap<>();
        payload.put("track", track);

        return given()
                .spec(getBaseSpec())
                .body(payload)
                .when()
                .put(ORDER_CANCEL_PATH);
    }
}