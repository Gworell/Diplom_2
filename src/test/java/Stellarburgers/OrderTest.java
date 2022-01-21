package Stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class OrderTest {
    private MethodsForOrder methodsForOrder;
    private MethodsForUser methodsForUser;
    private MethodsForIngredient methodsForIngredient;

    @Before
    public void setUp() {
        methodsForUser = new MethodsForUser();
        methodsForOrder = new MethodsForOrder();
        methodsForIngredient = new MethodsForIngredient();
    }
    @Test
    @DisplayName("Авторизация и создание заказа с ингредиентами")
    public void createOrderTest(){
        Response responseIngredients = methodsForIngredient.getIngredients();
        List<String> ingredients = responseIngredients.path("data._id");
        JSONObject jo = new JSONObject();
                String requestBody = jo
                .put("ingredients", ingredients)
                .toString();
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUser.createUser(userDataForRegistration);
        String accessToken = methodsForUser.userAccessToken(UserDataForAuthorization.from(userDataForRegistration));
        Response response = methodsForOrder.createOrderWithAuthorization(requestBody, accessToken);
        response.then().assertThat()
                .body("success", equalTo(true)).and().statusCode(SC_OK);
    }
    //Это ошибка. Всегда возвращается статус 200. В документации должны получать 400 статус и фолз.
    @Test
    @DisplayName("Создание заказа с игредиентами без авторизации")
    public void createOrderWithIngredientAndWithoutAuthorizationTest (){
        Response responseIngredients = methodsForIngredient.getIngredients();
        List<String> ingredients = responseIngredients.path("data._id");
        JSONObject jo = new JSONObject();
        String requestBody = jo
                .put("ingredients", ingredients)
                .toString();
        Response response = methodsForOrder.createOrderWithoutAuthorization(requestBody);
        response.then().assertThat()
                .body("success", equalTo(false)).and().statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Создание заказа без игредиентов с авторизацией")
    public void createOrderWithoutIngredientAndWithAuthorizationTest () {
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUser.createUser(userDataForRegistration);
        String accessToken = methodsForUser.userAccessToken(UserDataForAuthorization.from(userDataForRegistration));
        Response response = methodsForOrder.createOrderWithoutIngredient(accessToken);
        response.then().assertThat()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Создание заказа с авторизацией и с неверным хешем")
    public void createOrderWithWrongIngredientAndWithAuthorizationTest () {
        JSONObject jo = new JSONObject();
        String requestBody = jo
                .put("ingredients", "ingredients")
                .toString();
        UserDataForRegistration userDataForRegistration =UserDataForRegistration.getRandomDataForRegistration();
        methodsForUser.createUser(userDataForRegistration);
        String accessToken = methodsForUser.userAccessToken(UserDataForAuthorization.from(userDataForRegistration));
        Response response = methodsForOrder.createOrderWithAuthorization(requestBody, accessToken);
        response.then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);

    }
    @Test
    @DisplayName("Получение заказов с авторизацией")
    public void getOrderAfterAuthorizationTest () {
        Response responseIngredients = methodsForIngredient.getIngredients();
        List<String> ingredients = responseIngredients.path("data._id");
        JSONObject jo = new JSONObject();
        String requestBody = jo
                .put("ingredients", ingredients)
                .toString();
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUser.createUser(userDataForRegistration);
        String accessToken = methodsForUser.userAccessToken(UserDataForAuthorization.from(userDataForRegistration));
        methodsForOrder.createOrderWithAuthorization(requestBody, accessToken);
        Response response = methodsForOrder.getOrders(accessToken);
        response.then().assertThat().body("success", equalTo(true)).and().body("orders", notNullValue()).statusCode(SC_OK);
    }
    @Test
    @DisplayName("Попытка получения заказов без авторизации")
    public void getOrderWithoutAuthorizationTest () {
        Response response = methodsForOrder.getOrdersWithoutAuthorization();
        response.then().assertThat()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
}
