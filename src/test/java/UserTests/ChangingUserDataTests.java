package UserTests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.Api.UserApi;
import org.example.Models.User;
import org.example.Models.UserGenerator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChangingUserDataTests {

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
    @DisplayName("Изменение данных пользователя с авторизацией")
    @Description("Changing user data with authorization. PATCH/api/auth/user")
    public void changingUserDataWithAuthorizationTest() {
        User existingUser = new User(user.getEmail(), user.getPassword(), user.getName());
        ValidatableResponse loginResponse = UserApi.loginUser(existingUser);
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse updateResponse = userApi.updateUserWithAuth(UserGenerator.random(), accessToken);
        int statusCode = updateResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_OK));
        assertThat(updateResponse.extract().path("success"), equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Changing user data Without authorization. PATCH/api/auth/user")
    public void changingUserDataWithoutAuthorizationTest() {
        ValidatableResponse updateResponse = userApi.updateUserWithoutAuth(UserGenerator.random());
        int statusCode = updateResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_UNAUTHORIZED));
        assertThat(updateResponse.extract().path("success"), equalTo(false));
        assertThat(updateResponse.extract().path("message"), equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией без поля Email")
    @Description("Changing user data with authorization without Email. PATCH/api/auth/user")
    public void changingUserDataWithAuthorizationWithoutEmailTest() {
        User existingUser = new User(user.getEmail(), user.getPassword(), user.getName());
        ValidatableResponse loginResponse = UserApi.loginUser(existingUser);
        accessToken = loginResponse.extract().path("accessToken");
        User updatedUser = new User(null, RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10));
        ValidatableResponse updateResponse = userApi.updateUserWithAuth(updatedUser, accessToken);
        int statusCode = updateResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_OK));
        assertThat(updateResponse.extract().path("success"), equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией без поля Password")
    @Description("Changing user data with authorization without Password. PATCH/api/auth/user")
    public void changingUserDataWithAuthorizationWithoutPasswordTest() {
        User existingUser = new User(user.getEmail(), user.getPassword(), user.getName());
        ValidatableResponse loginResponse = UserApi.loginUser(existingUser);
        accessToken = loginResponse.extract().path("accessToken");
        User updatedUser = new User(RandomStringUtils.randomAlphanumeric(10),null, RandomStringUtils.randomAlphanumeric(10));
        ValidatableResponse updateResponse = userApi.updateUserWithAuth(updatedUser, accessToken);
        int statusCode = updateResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_OK));
        assertThat(updateResponse.extract().path("success"), equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией без поля Name")
    @Description("Changing user data with authorization without Name. PATCH/api/auth/user")
    public void changingUserDataWithAuthorizationWithoutNameTest() {
        User existingUser = new User(user.getEmail(), user.getPassword(), user.getName());
        ValidatableResponse loginResponse = UserApi.loginUser(existingUser);
        accessToken = loginResponse.extract().path("accessToken");
        User updatedUser = new User(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10), null);
        ValidatableResponse updateResponse = userApi.updateUserWithAuth(updatedUser, accessToken);
        int statusCode = updateResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_OK));
        assertThat(updateResponse.extract().path("success"), equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией без полей Password и Name")
    @Description("Changing user data with authorization without Name. PATCH/api/auth/user")
    public void changingUserDataWithAuthorizationWithoutPasswordAndNameTest() {
        User existingUser = new User(user.getEmail(), user.getPassword(), user.getName());
        ValidatableResponse loginResponse = UserApi.loginUser(existingUser);
        accessToken = loginResponse.extract().path("accessToken");
        User updatedUser = new User(RandomStringUtils.randomAlphanumeric(10), null, null);
        ValidatableResponse updateResponse = userApi.updateUserWithAuth(updatedUser, accessToken);
        int statusCode = updateResponse
                .extract()
                .statusCode();
        assertThat(statusCode, equalTo(HTTP_OK));
        assertThat(updateResponse.extract().path("success"), equalTo(true));
    }

}
