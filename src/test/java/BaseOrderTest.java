import client.OrderClient;
import org.junit.Before;

public abstract class BaseOrderTest {
    protected OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }
}