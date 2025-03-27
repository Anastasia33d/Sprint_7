import client.OrderClient;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;


public abstract class BaseOrderTest {
    protected OrderClient orderClient;

    protected String orderTrack = null;
    @Before
    @Step("Инициализация клиента для работы с заказами")
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    @Step("Отмена заказа с треком: {orderTrack}")
    public void tearDown() {
        if (orderTrack != null) {
            cancelOrderStep(orderTrack);
        }
    }

    @Step("Отмена заказа с треком: {orderTrack}")
    protected void cancelOrderStep(String orderTrack) {
        orderClient.cancelOrder(orderTrack);
    }
}