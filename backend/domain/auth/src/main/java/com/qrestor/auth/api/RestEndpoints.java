package com.qrestor.auth.api;

public interface RestEndpoints {

    String AUTH = "/authentication";
    String LOGIN = "/login";
    String ME = "/me";
    String LOGOUT = "/logout";
    String REFRESH = "/refresh";
    String REGISTRATION = "/registration";
    String VERIFY_EMAIL = "/verifyEmail/";
    String FORGOT_PASSWORD = "/forgot-password/";
    String FORGOT_PASSWORD_RESET = "/forgot-password/reset";
    String PASSWORD_CHANGE = "/password/change";


    String ACTUATOR = "/actuator/**";
    String SWAGGER = "/swagger-ui/**";
    String SWAGGER_API_DOCS = "/v3/api-docs/**";
    String SWAGGER_WEBJARS = "/webjars/**";
    String SWAGGER_RESOURCES = "/swagger-resources/**";
    String SWAGGER_RESOURCES_CONFIGURATION = "/swagger-resources/configuration/**";
    String SWAGGER_RESOURCES_SECURITY = "/swagger-resources/configuration/security/**";


    String[] SERVICE_ENDPOINTS_LIST = {ACTUATOR, SWAGGER, SWAGGER_API_DOCS, SWAGGER_WEBJARS, SWAGGER_RESOURCES,
            SWAGGER_RESOURCES_CONFIGURATION, SWAGGER_RESOURCES_SECURITY
    };

    String[] PUBLIC_ENDPOINTS_LIST = {
            REGISTRATION,
            REGISTRATION + VERIFY_EMAIL + "*",
            AUTH + LOGIN,
            AUTH + FORGOT_PASSWORD + "**",
            AUTH + FORGOT_PASSWORD_RESET + "/**"};

}
