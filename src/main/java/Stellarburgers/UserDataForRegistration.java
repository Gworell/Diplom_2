package Stellarburgers;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class UserDataForRegistration {

    public final String email;
    public final String password;
    public final String name;

    public UserDataForRegistration(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Получение данных для регистрации.")
    public static UserDataForRegistration getRandomDataForRegistration () {
        final String email = RandomStringUtils.randomAlphabetic(5) + "@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(5);
        final String name = RandomStringUtils.randomAlphabetic(5);
        return new UserDataForRegistration(email, password, name);
    }


}
