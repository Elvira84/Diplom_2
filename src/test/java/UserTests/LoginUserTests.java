package UserTests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Api.UserApi;
import org.example.Models.User;
import org.example.Models.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginUserTests {

    private UserApi userApi;
    private User user;
    private String accessToken;


    @Before
    public void setUp() {
        userApi = new UserApi();
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
    @DisplayName("Логин под существующим пользователем")
    @Description("Login under an existing user. POST/api/auth/login")
    public void LoginUnderAnExistingUserTest() {
        User existingUser = new User(user.getEmail(), user.getPassword(), user.getName());
        ValidatableResponse loginResponse = UserApi.loginUser(existingUser);
        int statusCodeLogin = loginResponse
                .extract()
                .statusCode();
        assertThat(statusCodeLogin, equalTo(HTTP_OK));
        assertThat(loginResponse.extract().path("success"), equalTo(true));
        assertThat(loginResponse.extract().path("user"), notNullValue());
    }

    @Test
    @DisplayName("Логин с неверным Email")
    @Description("Login with an invalid Email. POST/api/auth/login")
    public void LoginWithInvalidEmailTest() {
        User existingUser = new User("invalid Email", user.getPassword(), user.getName());
        ValidatableResponse loginResponse = UserApi.loginUser(existingUser);
        int statusCode = loginResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_UNAUTHORIZED));
        assertThat(loginResponse.extract().path("success"), equalTo(false));
        assertThat(loginResponse.extract().path("message"), equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("Login with an invalid Password. POST/api/auth/login")
    public void LoginWithInvalidPasswordTest() {
        User existingUser = new User(user.getEmail(), "invalid Password", user.getName());
        ValidatableResponse loginResponse = UserApi.loginUser(existingUser);
        int statusCode = loginResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_UNAUTHORIZED));
        assertThat(loginResponse.extract().path("success"), equalTo(false));
        assertThat(loginResponse.extract().path("message"), equalTo("email or password are incorrect"));
    }

}
