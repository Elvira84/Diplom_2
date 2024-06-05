package OrderTests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Api.OrdersApi;
import org.example.Api.UserApi;
import org.example.Models.User;
import org.example.Models.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetOrdersUserTests {

    private UserApi userApi;
    private User user;
    private String accessToken;
    private OrdersApi ordersApi;
    int statusCode;

    @Before
    public void setUp() {
        userApi = new UserApi();
        ordersApi = new OrdersApi();
        user = UserGenerator.random();
        ValidatableResponse createResponse = userApi.createUser(user);
        accessToken = createResponse
                .extract()
                .path("accessToken");
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
    @DisplayName("Получение заказа авторизованного пользователя")
    @Description("Get orders with auth user. POST/api/orders")
    public void GetOrdersWithAuthUserTest() {
        ValidatableResponse response = ordersApi.getOrderWithAuth(accessToken);
        statusCode = response.extract().statusCode();
        assertThat(statusCode, equalTo(HTTP_OK));
        assertThat(response.extract().path("success"), equalTo(true));

    }

    @Test
    @DisplayName("Получение заказа неавторизованного пользователя")
    @Description("Get orders without auth user. POST/api/orders")
    public void GetOrdersWithoutAuthUserTest() {
        ValidatableResponse response = ordersApi.getOrderWithoutAuth();
        statusCode = response.extract().statusCode();
        assertThat(statusCode, equalTo(HTTP_UNAUTHORIZED));
        assertThat(response.extract().path("success"), equalTo(false));
        assertThat(response.extract().path("message"), equalTo("You should be authorised"));

    }
}
