import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import org.apache.http.HttpStatus;
import org.junit.Test;
import utils.CourierGenerator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierCreateTest extends BaseCourierTest {
    private static final String MESSAGE_INSUFFICIENT_DATA = "Недостаточно данных для создания учетной записи";
    // По факту приходит другой текст -  "Этот логин уже используется. Попробуйте другой."
    private static final String MESSAGE_LOGIN_ALREADY_USED = "Этот логин уже используется.";
    private Courier courier;

    @Test
    @DisplayName("Курьера можно создать")
    @Description("Проверяем, что курьера можно создать с валидными данными и успешный запрос возвращает ok: true")
    public void courierCanBeCreatedSuccessfully() {
        courier = CourierGenerator.getRandomCourier();
        Response createResponse = createCourierStep(courier);
        verifyStatusAndFieldStep(createResponse, HttpStatus.SC_CREATED, "ok", equalTo(true));
        Response loginResponse = loginCourierStep(courier.getLogin(), courier.getPassword());
        loginResponse.then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue());
        courierId = loginResponse.path("id");
    }

    // Возвращается текст, отличающийся от текста в документации
    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Проверяем, что повторное создание курьера с уже используемым логином возвращает ошибку")
    public void cannotCreateDuplicateCourier() {
        courier = CourierGenerator.getRandomCourier();
        Response firstResponse = createCourierStep(courier);
        verifyStatusAndFieldStep(firstResponse, HttpStatus.SC_CREATED, "ok", equalTo(true));
        Response loginResponse = loginCourierStep(courier.getLogin(), courier.getPassword());
        loginResponse.then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue());
        courierId = loginResponse.path("id");

        Response duplicateResponse = createCourierStep(courier);
        verifyStatusAndFieldStep(duplicateResponse, HttpStatus.SC_CONFLICT, "message", equalTo(MESSAGE_LOGIN_ALREADY_USED));
    }

    @Test
    @DisplayName("Создание курьера без обязательного поля (логин) возвращает ошибку")
    @Description("Проверяем, что если не передан логин, ручка создания курьера возвращает ошибку")
    public void creatingCourierWithoutLoginReturnsError() {
        courier = CourierGenerator.getCourierWithoutLogin();
        Response response = createCourierStep(courier);
        verifyStatusAndFieldStep(response, HttpStatus.SC_BAD_REQUEST, "message", equalTo(MESSAGE_INSUFFICIENT_DATA));
    }

    @Test
    @DisplayName("Создание курьера без обязательного поля (пароль) возвращает ошибку")
    @Description("Проверяем, что если не передан пароль, ручка создания курьера возвращает ошибку")
    public void creatingCourierWithoutPasswordReturnsError() {
        courier = CourierGenerator.getCourierWithoutPassword();
        Response response = createCourierStep(courier);
        verifyStatusAndFieldStep(response, HttpStatus.SC_BAD_REQUEST, "message", equalTo(MESSAGE_INSUFFICIENT_DATA));
    }

    @Test
    @DisplayName("Успешное создание курьера без поля 'имя'")
    @Description("Проверяем, что если не передано имя (firstName), ручка создания курьера создает курьера и успешный запрос возвращает ok: true")
    public void creatingCourierWithoutFirstNameReturnsTrackField() {
        courier = CourierGenerator.getCourierWithoutFirstName();
        Response response = createCourierStep(courier);

        verifyStatusAndFieldStep(response, HttpStatus.SC_CREATED, "ok", equalTo(true));
    }
}