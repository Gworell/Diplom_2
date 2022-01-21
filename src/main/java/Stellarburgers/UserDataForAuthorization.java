package Stellarburgers;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserDataForAuthorization {

    public final String email;
    public final String password;

    public UserDataForAuthorization(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Step("Получение данных для регистрации.")
    public static UserDataForAuthorization getRandomDataForAuthorization () {
        final String email = RandomStringUtils.randomAlphabetic(5) + "@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(5);
        return new UserDataForAuthorization(email, password);
    }
    @Step("Получение логина и пароля из данных о регистрации.")
    public static UserDataForAuthorization from (UserDataForRegistration userDataForRegistration) {
        return new UserDataForAuthorization(userDataForRegistration.email, userDataForRegistration.password);
    }

}
