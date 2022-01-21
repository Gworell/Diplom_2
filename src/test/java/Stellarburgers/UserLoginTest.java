package Stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class UserLoginTest {

    private MethodsForUser methodsForUser;

    @Before
    public void setUp() {
        methodsForUser = new MethodsForUser();
    }

    @Test
    @DisplayName("Корректная авторизация")
    public void correctUserAuthorizationTest() {
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUser.createUser(userDataForRegistration);
        Response response = methodsForUser.userAuthorization(UserDataForAuthorization.from(userDataForRegistration));
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(SC_OK);
    }
    @Test
    @DisplayName("Авторизация с некорректными данными")
    public void authorizationWithIncorrectDataTest() {
        UserDataForAuthorization userDataForAuthorization = UserDataForAuthorization.getRandomDataForAuthorization();
        Response response = methodsForUser.userAuthorization(userDataForAuthorization);
        response.then().assertThat().body("message", equalTo("email or password are incorrect")).and().statusCode(SC_UNAUTHORIZED);
    }

}
