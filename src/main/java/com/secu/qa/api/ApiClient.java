package com.secu.qa.api;

import io.restassured.filter.cookie.CookieFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.config.CsrfConfig.csrfConfig;
import static io.restassured.config.RestAssuredConfig.config;

// Docs: REST Assured Usage wiki (CSRF, cookie propagation, CookieFilter)
public final class ApiClient {

    private final String baseUrl;
    private final CookieFilter session = new CookieFilter();

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // _token is Laravel's CSRF field; csrf() needs the full URL; follow(false) keeps the 302 with the login cookies
    public Response postForm(String path, Map<String, String> fields) {
        RequestSpecification request = given()
                .baseUri(baseUrl)
                .config(config().csrfConfig(csrfConfig().with().csrfInputFieldName("_token")))
                .filter(session)
                .csrf(baseUrl + path)
                .redirects().follow(false);
        fields.forEach((name, value) -> request.formParam(name, value));
        return request.when().post(path);
    }
}
