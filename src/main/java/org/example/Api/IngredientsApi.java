package org.example.Api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.ApiUrls;
import org.example.Models.OrderIngredients;

import static org.example.ApiUrls.spec;

public class IngredientsApi {

    @Step("Получение списка ингредиентов")
    public OrderIngredients getIngredients() {
        return spec()
                .get(ApiUrls.INGREDIENTS)
                .then().log().all()
                .extract()
                .body()
                .as(OrderIngredients.class);
    }
}
