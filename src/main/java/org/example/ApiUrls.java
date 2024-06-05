package org.example;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiUrls {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    public static final String CREATE_USER = "/api/auth/register";
    public static final String LOGIN_USER = "/api/auth/login";
    public static final String CHANGE_USER = "/api/auth/user";
    public static final String ORDERS = "/api/orders";
    public static final String INGREDIENTS = "/api/ingredients";

    public static RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI);
    }

}
