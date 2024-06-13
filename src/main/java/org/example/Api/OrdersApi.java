package org.example.Api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.ApiUrls;
import org.example.Models.Order;

public class OrdersApi extends ApiUrls {

    @Step("Создание заказа авторизованным пользователем")
    public ValidatableResponse createOrderWithAuth (Order order, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Создание заказа неавторизованным пользователем")
    public ValidatableResponse createOrderWithoutAuth (Order order) {
        return spec()
                .body(order)
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Получение заказа авторизованным пользователем")
    public ValidatableResponse getOrderWithAuth (String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .get(ORDERS)
                .then().log().all();
    }

    @Step("Получение заказа неавторизованным пользователем")
    public ValidatableResponse getOrderWithoutAuth () {
        return spec()
                .get(ORDERS)
                .then().log().all();
    }

}
