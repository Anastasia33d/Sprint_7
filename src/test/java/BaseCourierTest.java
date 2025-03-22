import client.CourierClient;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;

public abstract class BaseCourierTest {
    protected CourierClient courierClient;
    protected int courierId = 0;

    @Before
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
}