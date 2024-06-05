package org.example.Api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.ApiUrls;
import org.example.Models.User;



public class UserApi extends ApiUrls {


    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .post(CREATE_USER)
                .then().log().all();
    }

    @Step("Отправка запроса на логин пользователя")
    public static ValidatableResponse loginUser(User user) {
        return spec()
                .body(user)
                .post(LOGIN_USER)
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return spec()
                .auth().oauth2(accessToken.substring(accessToken.indexOf(" ") + 1))
                .delete(CHANGE_USER)
                .then().log().all();
    }

    @Step("Обновление данных авторизованного пользователя")
    public ValidatableResponse updateUserWithAuth(User user, String accessToken) {
        return spec()
//                .header("Authorization", accessToken)
                .auth().oauth2(accessToken.substring(accessToken.indexOf(" ") + 1))
                .body(user)
                .patch(CHANGE_USER)
                .then().log().all();
    }

    @Step("Обновление данных неавторизованного пользователя")
    public ValidatableResponse updateUserWithoutAuth(User user) {
        return spec()
                .body(user)
                .patch(CHANGE_USER)
                .then().log().all();
    }

}
