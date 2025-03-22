// В классе OrderClient добавляем метод для GET запроса списка заказов
package client;

import io.restassured.response.Response;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends ClientConfig {
    private final String ORDER_PATH = "/orders";

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
}