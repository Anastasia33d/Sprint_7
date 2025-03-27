import client.CourierClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;

public abstract class BaseCourierTest {
    protected CourierClient courierClient;
    protected int courierId = 0;

    @Before
    @Step("Инициализация клиента для работы с курьерами")
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            deleteCourierStep(courierId);
        }
    }

    @Step("Удаляем курьера с id: {courierId}")
    protected void deleteCourierStep(int courierId) {
        courierClient.delete(courierId);
    }

    @Step("Выполняем авторизацию курьера с логином: {login} и паролем: {password}")
    protected Response loginCourierStep(String login, String password) {
        return courierClient.login(login, password);
    }

    @Step("Проверяем, что статус-код равен {expectedStatusCode} и поле {fieldName} соответствует условию")
    protected void verifyStatusAndFieldStep(Response response, int expectedStatusCode, String fieldName, Object matcher) {
        response.then()
                .statusCode(expectedStatusCode)
                .body(fieldName, (org.hamcrest.Matcher<?>) matcher);
    }

    @Step("Создаем курьера: {courier}")
    protected Response createCourierStep(Courier courier) {
        return courierClient.create(courier);
    }
}