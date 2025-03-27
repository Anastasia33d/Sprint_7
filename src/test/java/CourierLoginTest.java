import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Courier;
import org.apache.http.HttpStatus;
import org.junit.Test;
import utils.CourierGenerator;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest extends BaseCourierTest {
    private static final String MESSAGE_INSUFFICIENT_DATA = "Недостаточно данных для входа";
    private static final String MESSAGE_USER_NOT_FOUND = "Учетная запись не найдена";
    private Courier courier;

    @Test
    @DisplayName("Курьер может успешно авторизоваться")
    @Description("Проверяем успешную авторизацию курьера с валидными данными")
    public void courierCanLoginSuccessfully() {
        courier = CourierGenerator.getRandomCourier();
        createCourierStep(courier);
        Response loginResponse = loginCourierStep(courier.getLogin(), courier.getPassword());
        verifyStatusAndFieldStep(loginResponse, HttpStatus.SC_OK, "id", notNullValue());
        courierId = loginResponse.path("id");
    }

    @Test
    @DisplayName("Система возвращает ошибку при попытке входа без логина")
    @Description("Проверяем, что система возвращает ошибку, если не передан логин")
    public void loginWithoutLoginReturnsError() {
        Response response = loginCourierStep(null, "password");
        verifyStatusAndFieldStep(response, HttpStatus.SC_BAD_REQUEST, "message", equalTo(MESSAGE_INSUFFICIENT_DATA));
    }

    // Возвращается 504 с сервера (Баг?)
    @Test
    @DisplayName("Система возвращает ошибку при попытке входа без пароля")
    @Description("Проверяем, что система возвращает ошибку, если не передан пароль")
    public void loginWithoutPasswordReturnsError() {
        Response response = loginCourierStep("login", null);
        verifyStatusAndFieldStep(response, HttpStatus.SC_BAD_REQUEST, "message", equalTo(MESSAGE_INSUFFICIENT_DATA));
    }

    @Test
    @DisplayName("Система возвращает ошибку при неверном пароле")
    @Description("Проверяем, что система возвращает ошибку при неверном пароле")
    public void loginWithWrongPasswordReturnsError() {
        courier = CourierGenerator.getRandomCourier();
        createCourierStep(courier);
        Response loginResponse = loginCourierStep(courier.getLogin(), "wrongPassword");
        verifyStatusAndFieldStep(loginResponse, HttpStatus.SC_NOT_FOUND, "message", equalTo(MESSAGE_USER_NOT_FOUND));

        Response correctLoginResponse = loginCourierStep(courier.getLogin(), courier.getPassword());
        courierId = correctLoginResponse.path("id");
    }

    @Test
    @DisplayName("Система возвращает ошибку при попытке входа несуществующего пользователя")
    @Description("Проверяем, что система возвращает ошибку при попытке входа несуществующего пользователя")
    public void loginNonExistentUserReturnsError() {
        Response response = loginCourierStep("nonexistent", "password");
        verifyStatusAndFieldStep(response, HttpStatus.SC_NOT_FOUND, "message", equalTo(MESSAGE_USER_NOT_FOUND));
    }
}