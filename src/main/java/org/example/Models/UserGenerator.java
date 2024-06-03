package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.Models.User;

public class UserGenerator  {
    public static User random() {
        return new User(RandomStringUtils.randomAlphanumeric(10), RandomStringUtils.randomAlphanumeric(10) + "@yandex.ru", RandomStringUtils.randomAlphanumeric(10));
    }
}
