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


public class CreateUserTests {
    private UserApi userApi;
    private User user;
    private String accessToken;
    int statusCode;


    @Before
    public void setUp() {
        userApi = new UserApi();
        user = UserGenerator.random();
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
    @DisplayName("Создание уникального пользователя")
    @Description("Create user. POST/api/auth/register")
    public void createUserTest() {
        ValidatableResponse response = userApi.createUser(user);
        statusCode = response
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_OK));
        assertThat(response.extract().path("success"), equalTo(true));
        assertThat(response.extract().path("user"), notNullValue());
        accessToken = response
                .extract()
                .path("accessToken");

    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    @Description("Create user already exists. POST/api/auth/register")
    public void createUserAlreadyExistsTest() {
        ValidatableResponse response = userApi.createUser(user);
        statusCode = response
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_OK));
        accessToken = response
                .extract()
                .path("accessToken");

        User existingUser = user;

        ValidatableResponse doubleResponse = userApi.createUser(existingUser);
        statusCode = doubleResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_FORBIDDEN));
        assertThat(doubleResponse.extract().path("success"), equalTo(false));
        assertThat(doubleResponse.extract().path("message"), equalTo("User already exists"));
    }


    @Test
    @DisplayName("Создание пользователя без заполнения обязательного поля Email")
    @Description("Create user without email. POST/api/auth/register")
    public void createUserWithoutEmailTest() {
        user.setEmail("");
        user.setPassword(UserGenerator.random().getPassword());
        user.setName(UserGenerator.random().getName());

        ValidatableResponse response = userApi.createUser(user);
        statusCode = response.extract().statusCode();

        assertThat(statusCode, equalTo(HTTP_FORBIDDEN));
        assertThat(response.extract().path("success"), equalTo(false));
        assertThat(response.extract().path("message"), equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без заполнения обязательного поля Password")
    @Description("Create user without password. POST/api/auth/register")
    public void createUserWithoutPasswordTest() {
        user.setEmail(UserGenerator.random().getEmail());
        user.setPassword("");
        user.setName(UserGenerator.random().getName());

        ValidatableResponse response = userApi.createUser(user);
        statusCode = response.extract().statusCode();

        assertThat(statusCode, equalTo(HTTP_FORBIDDEN));
        assertThat(response.extract().path("success"), equalTo(false));
        assertThat(response.extract().path("message"), equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без заполнения обязательного поля Name")
    @Description("Create user without name. POST/api/auth/register")
    public void createUserWithoutNameTest() {
        user.setEmail(UserGenerator.random().getEmail());
        user.setPassword(UserGenerator.random().getPassword());
        user.setName("");

        ValidatableResponse response = userApi.createUser(user);
        statusCode = response.extract().statusCode();

        assertThat(statusCode, equalTo(HTTP_FORBIDDEN));
        assertThat(response.extract().path("success"), equalTo(false));
        assertThat(response.extract().path("message"), equalTo("Email, password and name are required fields"));
    }
}
