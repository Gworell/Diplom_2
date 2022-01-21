package Stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class EditUserTest {
    private MethodsForUser methodsForUser;

    @Before
    public void setUp(){ methodsForUser = new MethodsForUser();}

    @Test
    @DisplayName("Изменения регистрационных данных с авторизацией")
    public void editUserDataWithAuthorizationTest(){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        UserDataForRegistration newUserDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUser.createUser(userDataForRegistration);
        String accessToken = methodsForUser.userAccessToken(UserDataForAuthorization.from(userDataForRegistration));
        Response response = methodsForUser.editUserAuthorizationData(newUserDataForRegistration, accessToken);
        response.then().assertThat().body("success", equalTo(true)).and().statusCode(SC_OK);
    }
    @Test
    @DisplayName("Изменения регистрационных данных без авторизации")
    public void editUserDataWithoutAuthorizationTest(){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        UserDataForRegistration newUserDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUser.createUser(userDataForRegistration);
        Response response = methodsForUser.editUserDataWithoutAuthorization(newUserDataForRegistration);
        response.then().assertThat().body("message", equalTo("You should be authorised")).and().statusCode(SC_UNAUTHORIZED);
    }
}
