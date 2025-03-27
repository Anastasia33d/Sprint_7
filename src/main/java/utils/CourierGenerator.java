package utils;

import com.github.javafaker.Faker;
import model.Courier;

public class CourierGenerator {
    private static final Faker faker = new Faker();

    public static Courier getRandomCourier() {
        String login = faker.name().username();
        String password = faker.internet().password();
        String firstName = faker.name().firstName();
        return new Courier(login, password, firstName);
    }

    public static Courier getCourierWithoutLogin() {
        String password = faker.internet().password();
        String firstName = faker.name().firstName();
        return new Courier(null, password, firstName);
    }

    public static Courier getCourierWithoutPassword() {
        String login = faker.name().username();
        String firstName = faker.name().firstName();
        return new Courier(login, null, firstName);
    }

    public static Courier getCourierWithoutFirstName() {
        String login = faker.name().username();
        String password = faker.internet().password();
        return new Courier(login, password, null);
    }
}