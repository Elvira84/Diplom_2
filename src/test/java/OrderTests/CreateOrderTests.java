package OrderTests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Api.IngredientsApi;
import org.example.Api.OrdersApi;
import org.example.Api.UserApi;
import org.example.Models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateOrderTests {

    private UserApi userApi;
    private IngredientsApi ingredientsApi;
    private OrdersApi ordersApi;
    private User user;
    private String accessToken;
    private List<String> ingredientIds;
    private Order order;
    int statusCode;


    @Before
    public void setUp() {
        userApi = new UserApi();
        ingredientsApi = new IngredientsApi();
        ordersApi = new OrdersApi();
        user = UserGenerator.random();
        ValidatableResponse createResponse = userApi.createUser(user);
        accessToken = createResponse
                .extract()
                .path("accessToken");
        ingredientIds = new ArrayList<>();
        order = new Order(ingredientIds);

    }

    @After
    public void deleteUser() {
        try {
            ValidatableResponse deleteResponse = userApi.deleteUser(accessToken);
            deleteResponse
                    .statusCode(HTTP_ACCEPTED)
                    .body("success", equalTo(true), "message", equalTo("User successfully removed"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Создание заказа с авторизацией с ингредиентами")
    @Description("Create order with auth with ingredients. POST/api/orders")
    public void CreateOrderWithAuthWithIngredientsTest() {
        OrderIngredients orderIngredients = ingredientsApi.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id());
        ingredientIds.add(orderIngredients.getData().get(1).get_id());


        ValidatableResponse createOrderResponse = ordersApi.createOrderWithAuth(order, accessToken);
        statusCode = createOrderResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_OK));
        assertThat(createOrderResponse.extract().path("success"), equalTo(true));
        assertThat(createOrderResponse.extract().path("order "), notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации с ингредиентами")
    @Description("Create order without auth with ingredients. POST/api/orders")
    public void CreateOrderWithoutAuthWithIngredientsTest() {
        OrderIngredients orderIngredients = ingredientsApi.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id());
        ingredientIds.add(orderIngredients.getData().get(1).get_id());


        ValidatableResponse createOrderResponse = ordersApi.createOrderWithoutAuth(order);
        statusCode = createOrderResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_OK));
        assertThat(createOrderResponse.extract().path("success"), equalTo(true));
        assertThat(createOrderResponse.extract().path("order "), notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации без ингредиентов")
    @Description("Create order without auth without  ingredients. POST/api/orders")
    public void CreateOrderWithoutAuthWithoutIngredientsTest() {
        ValidatableResponse createOrderResponse = ordersApi.createOrderWithoutAuth(order);
        statusCode = createOrderResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_BAD_REQUEST));
        assertThat(createOrderResponse.extract().path("success"), equalTo(false));
        assertThat(createOrderResponse.extract().path("message "), equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией без ингредиентов")
    @Description("Create order with auth without  ingredients. POST/api/orders")
    public void CreateOrderWithAuthWithoutIngredientsTest() {
        ValidatableResponse createOrderResponse = ordersApi.createOrderWithAuth(order, accessToken);
        statusCode = createOrderResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_BAD_REQUEST));
        assertThat(createOrderResponse.extract().path("success"), equalTo(false));
        assertThat(createOrderResponse.extract().path("message "), equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией с неверным хешем ингредиентов")
    @Description("Create order with auth with invalid ingredients. POST/api/orders")
    public void CreateOrderWithAuthWitInvalidIngredientsTest() {
        OrderIngredients orderIngredients = ingredientsApi.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id() + "123Qwe");
        ingredientIds.add(orderIngredients.getData().get(1).get_id() + "456Asd");


        ValidatableResponse createOrderResponse = ordersApi.createOrderWithAuth(order, accessToken);
        statusCode = createOrderResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_INTERNAL_ERROR));

    }

    @Test
    @DisplayName("Создание заказа без авторизации с неверным хешем ингредиентов")
    @Description("Create order without auth with invalidingredients. POST/api/orders")
    public void CreateOrderWithoutAuthWithInvalidIngredientsTest() {
        OrderIngredients orderIngredients = ingredientsApi.getIngredients();
        ingredientIds.add(orderIngredients.getData().get(0).get_id() + "123Qwe");
        ingredientIds.add(orderIngredients.getData().get(1).get_id() + "456Asd");


        ValidatableResponse createOrderResponse = ordersApi.createOrderWithoutAuth(order);
        statusCode = createOrderResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_INTERNAL_ERROR));

    }
}
