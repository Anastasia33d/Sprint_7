import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Order;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.OrderGenerator;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
@DisplayName("Проверка ручки создания заказа с различными цветовыми параметрами")
public class OrderCreateParamTest extends BaseOrderTest {
    @Parameterized.Parameter
    public List<String> colors;

    @Parameterized.Parameters(name = "Тестовые данные: colors = {0}")
    public static Collection<Object[]> getColors() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {null}
        });
    }

    @Test
    @DisplayName("Проверка создания заказа с указанными вариантами цвета")
    @Description("Проверяем, что можно создать заказ, указывая один из цветов (BLACK или GREY), оба цвета одновременно или не указывая цвет вообще. " +
            "В теле ответа обязательно присутствует поле track.")
    public void createOrderTest() {
        Order order = OrderGenerator.getRandomOrder(colors);
        Response response = sendCreateOrderRequest(order);
        verifyOrderCreationResponse(response);

        orderTrack = response.jsonPath().getString("track");
    }

    @Step("Отправляем запрос на создание заказа: {order}")
    private Response sendCreateOrderRequest(Order order) {
        return orderClient.createOrder(order);
    }

    @Step("Проверяем, что статус ответа равен 201 и тело содержит поле track")
    private void verifyOrderCreationResponse(Response response) {
        response.then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue());
    }
}