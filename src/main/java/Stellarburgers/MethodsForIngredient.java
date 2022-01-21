package Stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class MethodsForIngredient extends RestClient {

    private static final String INGREDIENTS_PATH = "/api/ingredients/";

    @Step ("Получение списка ингредиентов")
    public Response getIngredients() {
        return given()
                .spec(getBaseSpec())
                .get(INGREDIENTS_PATH);

    }
}


