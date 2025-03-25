# Sprint 7 - API Автотесты
Проект автоматизированного тестирования API сервиса доставки. Содержит набор тестов для проверки функционала работы с курьерами и заказами.
## Технологии
- Java 11
- Maven 3.8.1
- JUnit 4.13.2
- Rest Assured 5.4.0
- Allure 2.15.0
- JavaFaker 1.0.2
- Jackson Databind 2.13.0
- Logback Classic 1.2.3
- Hamcrest 1.3
## Структура проекта
- `src/main/java/client` - HTTP-клиенты для работы с API
- `src/main/java/model` - модели данных (Courier, Order)
- `src/main/java/utils` - вспомогательные классы для генерации тестовых данных
- `src/test/java` - тестовые классы
## Запуск тестов
### Предварительные требования
- установленный Maven
- установленная Java 11
### Запуск всех тестов
`mvn clean test`
### Генерация Allure-отчёта
`mvn allure:serve`
## Покрытие кода
Для анализа покрытия кода используется JaCoCo. Отчёт о покрытии генерируется автоматически при выполнении команды `mvn verify`.
## Описание API методов
### Курьер
- POST /api/v1/courier - создание курьера
- POST /api/v1/courier/login - логин курьера
- DELETE /api/v1/courier/:id - удаление курьер
### Заказ
- POST /api/v1/orders - создание заказа
- GET /api/v1/orders - получение списка заказов