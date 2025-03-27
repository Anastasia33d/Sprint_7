package utils;

import com.github.javafaker.Faker;
import model.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderGenerator {
    private static final Faker faker = new Faker();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Order getRandomOrder(List<String> colors) {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress() + ", " + faker.address().city();
        int metroStation = faker.number().numberBetween(1, 100);
        String phone = faker.phoneNumber().phoneNumber();
        int rentTime = faker.number().numberBetween(1, 10);
        LocalDate deliveryDate = LocalDate.now().plusDays(faker.number().numberBetween(1, 30));
        String comment = faker.lorem().sentence();
        return new Order(
                firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate.format(formatter),
                comment,
                colors
        );
    }
}