package org.example.Api;

import io.qameta.allure.Step;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import org.example.ApiUrls;
import org.example.Request.UserRequest;


public class UserApiClient extends ApiUrls {

   @Step("Отправка запроса на создание пользователя")
    public ValidatableResponse createUser(String email, String password, String name) {
        return spec()
                .body(new UserRequest(email, password, name))
                .post(CREATE_USER)
                .then().log().all();
    }

    @Step("Отправка запроса на логин пользователя")
    public ValidatableResponse loginUser(String email, String password) {
        return spec()
                .body(new UserRequest(email, password))
                .post(LOGIN_USER)
                .then().log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String token) {
        return (ValidatableResponse) spec()
                .delete(CHANGE_USER)
                .header(String.valueOf(new Header("Authorization", "Bearer " + token)))
                .then().log().all();
    }


}
