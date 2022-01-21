package Stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class MethodsForOrder extends RestClient {
    private static final String ORDER_PATH = "/api/orders/";

    @Step ("Создание заказа с авторизацией")
    public Response createOrderWithAuthorization(String requestBody, String accessToken){
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(requestBody)
                .when()
                .post(ORDER_PATH);
    }
    @Step ("Создание заказа без авторизации")
    public Response createOrderWithoutAuthorization(String requestBody){
        return given()
                .spec(getBaseSpec())
                .body(requestBody)
                .when()
                .post(ORDER_PATH);
    }
    @Step ("Создание заказа без ингридиентов")
    public Response createOrderWithoutIngredient(String accessToken){
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .post(ORDER_PATH);
    }
    @Step ("Получение заказов авторизованного пользователя")
    public Response getOrders (String accessToken) {
        return given()
                .spec(getBaseSpec())
                .headers("Authorization", accessToken)
                .when()
                .get(ORDER_PATH);
    }
    @Step ("Получение заказов не авторизованного пользовалетя")
    public Response getOrdersWithoutAuthorization () {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH);
    }
}
