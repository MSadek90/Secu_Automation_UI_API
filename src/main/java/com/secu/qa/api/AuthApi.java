package com.secu.qa.api;

import com.secu.qa.routes.Routes;
import io.qameta.allure.Step;

import java.util.Map;

public final class AuthApi {

    private final ApiClient client;

    public AuthApi(ApiClient client) {
        this.client = client;
    }

    @Step("Log in as admin through the API")
    public Map<String, String> loginAsAdmin(String email, String password) {
        return client.postForm(Routes.ADMIN_LOGIN, Map.of("email", email, "password", password))
                .getCookies();
    }
}
