package Stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class UserRegistrationTest {

   private MethodsForUser methodsForUser;

    @Before
    public void setUp() {
        methodsForUser = new MethodsForUser();
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    public void createNewUserTest (){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        Response response = methodsForUser.createUser(userDataForRegistration);
        response.then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }
    @Test
    @DisplayName("Повторная регистрация пользователя с теме же параметрами")
    public void createUserWithSameParametersTest (){
        UserDataForRegistration userDataForRegistration = UserDataForRegistration.getRandomDataForRegistration();
        methodsForUser.createUser(userDataForRegistration);
        Response response = methodsForUser.createUser(userDataForRegistration);
        response.then().assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }
    @Test
    @DisplayName("Регистрация пользователя без пароля")
    public void createUserWithoutPasswordTest() {
        JSONObject jo = new JSONObject();
        String email = RandomStringUtils.randomAlphabetic(5) + "@yandex.ru";
        String name = RandomStringUtils.randomAlphabetic(5);
        String requestBody = jo
                .put("email", email)
                .put("name", name)
                .toString();
        Response response = methodsForUser.createNewCourierWithIncorrectData(requestBody);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }
    @Test
    @DisplayName("Регистрация пользователя без почты")
    public void createUserWithoutEmailTest() {
        JSONObject jo = new JSONObject();
        String password = RandomStringUtils.randomAlphabetic(5);
        String name = RandomStringUtils.randomAlphabetic(5);
        String requestBody = jo
                .put("password", password)
                .put("name", name)
                .toString();
        Response response = methodsForUser.createNewCourierWithIncorrectData(requestBody);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }
    @Test
    @DisplayName("Регистрация пользователя без имени")
    public void createUserWithoutNameTest() {
        JSONObject jo = new JSONObject();
        String email = RandomStringUtils.randomAlphabetic(5) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphabetic(5);
        String requestBody = jo
                .put("email", email)
                .put("password", password)
                .toString();
        Response response = methodsForUser.createNewCourierWithIncorrectData(requestBody);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

}