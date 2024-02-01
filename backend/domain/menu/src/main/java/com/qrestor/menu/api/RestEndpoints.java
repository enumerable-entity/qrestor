package com.qrestor.menu.api;

public interface RestEndpoints {
    String MANAGEMENT = "/management";
    String MENU_ITEMS = "/menu-items";
    String MENU_ITEM_OPTIONS = "/item-options";
    String MENU_ITEM_OPTION_POSTIONS = "/options-positions";
    String MENU = "/menu";
    String CATEGORY = "/category";
    String INGREDIENTS = "/ingredients";
    String CATEGORY_COMBO = "/categoryCombo";
    String INGREDIENTS_COMBO = "/ingredientsCombo";
    String ITEM_OPTIONS_COMBO = "/itemOptionsCombo";
    String ITEM_OPTION_POSITIONS_COMBO = "/itemOptionPositionsCombo";
    String DICTIONARY = "/dictionary";
    String MENU_MANAGEMENT = MANAGEMENT + MENU;
    String INGREDIENTS_MANAGEMENT = MANAGEMENT + INGREDIENTS;
    String MENU_ITEMS_MANAGEMENT = MANAGEMENT + MENU_ITEMS;
    String MENU_ITEM_OPTIONS_MANAGEMENT = MANAGEMENT + MENU_ITEM_OPTIONS;
    String MENU_ITEM_OPTION_POSITIONS_MANAGEMENT = MANAGEMENT + MENU_ITEM_OPTION_POSTIONS;

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
            CATEGORY,
            MENU + "/{id}/{itemId}",
            MENU + "/{id}",
            "/error/**"};

}
