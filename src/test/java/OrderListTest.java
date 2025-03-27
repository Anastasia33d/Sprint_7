import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Проверка ручки получения списка заказов")
public class OrderListTest extends BaseOrderTest {
    @Test
    @DisplayName("Получение списка заказов без передачи фильтров")
    @Description("Проверяем, что ручка /api/v1/orders возвращает в теле ответа список заказов (поле 'orders') и информацию о пагинации.")
    public void getOrderListTest() {
        Response response = sendGetOrdersRequest();
        verifyGetOrdersResponse(response);
    }

    @Step("Отправляем GET запрос на получение списка заказов")
    private Response sendGetOrdersRequest() {
        return orderClient.getOrders();
    }

    @Step("Проверяем, что статус ответа равен 200 и в теле ответа содержится поле 'orders'")
    private void verifyGetOrdersResponse(Response response) {
        response.then()
                .statusCode(HttpStatus.SC_OK)
                .body("orders", notNullValue());
    }
}