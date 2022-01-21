package Stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class MethodsForUser extends RestClient {

    private static final String USER_PATH = "/api/auth";

    @Step("Создание пользователя")
    public Response createUser(UserDataForRegistration userDataForRegistration) {
        return given()
                .spec(getBaseSpec())
                .body(userDataForRegistration)
                .when()
                .post(USER_PATH + "/register/");
    }

    @Step("Создание курьера с неполнымми данными")
    public Response createNewCourierWithIncorrectData(String registrationData) {
        return given()
                .spec(getBaseSpec())
                .body(registrationData)
                .when()
                .post(USER_PATH + "/register/");
    }

    @Step("Авторизация в системе")
    public Response userAuthorization(UserDataForAuthorization userDataForAuthorization) {
        return given()
                .spec(getBaseSpec())
                .body(userDataForAuthorization)
                .post(USER_PATH + "/login/");
    }

    @Step("Изменение данных пользователя")
    public Response editUserAuthorizationData(UserDataForRegistration userDataForRegistration, String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(userDataForRegistration)
                .when()
                .patch(USER_PATH + "/user/");
    }

    @Step("Изменение данных пользователя без авторизации")
    public Response editUserDataWithoutAuthorization(UserDataForRegistration userDataForRegistration) {
        return given()
                .spec(getBaseSpec())
                .body(userDataForRegistration)
                .when()
                .patch(USER_PATH + "/user/");

    }

    @Step("Авторизация и получение accessToken")
    public String userAccessToken(UserDataForAuthorization userDataForAuthorization) {
        return given()
                .spec(getBaseSpec())
                .body(userDataForAuthorization)
                .when()
                .post(USER_PATH + "/login/")
                .then().extract()
                .path("accessToken");
    }
}
