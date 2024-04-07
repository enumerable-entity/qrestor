package com.qrestor.sellpoint.api;

public interface RestEndpoints {

    String RESTAURANT = "/restaurant";
    String INTEGRATION = "/integration";
    String RESTAURANT_COMBO = "/restaurantCombo";
    String DICTIONARY = "/dictionary";
    String MANAGEMENT = "/management" + RESTAURANT;

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
            RESTAURANT + "/{id}",
            "/integration/getRestaurantOwnerId",
            "/error/**"};

}
