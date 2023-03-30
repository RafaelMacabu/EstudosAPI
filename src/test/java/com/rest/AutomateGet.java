package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class AutomateGet {

    @Test
    public void validate_get_status_code(){
        given().baseUri("https://api.postman.com").header("X-Api-Key","PMAK-641f5b47015d090cf6d61f07-b3426baac58c3d9b17f3c1f536b3b02514").
                when().get("/workspaces").
                then().log().all().assertThat().statusCode(200);

    }
}
