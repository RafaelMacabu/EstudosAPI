package com.rest;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomateGet {

    @Test
    public void validate_get_status_code() {
        given().baseUri("https://api.postman.com").header("X-Api-Key", "PMAK-641f5b47015d090cf6d61f07-b3426baac58c3d9b17f3c1f536b3b02514").
                when().get("/workspaces").
                then().log().all().assertThat().statusCode(200);
        ;

    }

    @Test
    public void validate_response_body() {
        given().baseUri("https://api.postman.com").header("X-Api-Key", "PMAK-641f5b47015d090cf6d61f07-b3426baac58c3d9b17f3c1f536b3b02514").
                when().get("/workspaces").
                then().log().all().assertThat().statusCode(200).
                body("workspaces.name", hasItems("My Workspace", "Team Workspace"),
                        "workspaces.type", hasItems("personal", "team"),
                        "workspaces.size()",equalTo(2))
        ;

    }

    @Test
    public void extract_response() {
        Response res = given().baseUri("https://api.postman.com").header("X-Api-Key", "PMAK-641f5b47015d090cf6d61f07-b3426baac58c3d9b17f3c1f536b3b02514").
                when().get("/workspaces").
                then().assertThat().statusCode(200).
                extract().response();
        System.out.println("response: " + res.asString());


    }

    @Test
    public void extract_single_value_from_response() {
        String name = given().baseUri("https://api.postman.com").header("X-Api-Key", "PMAK-641f5b47015d090cf6d61f07-b3426baac58c3d9b17f3c1f536b3b02514").
                when().get("/workspaces").
                then().assertThat().statusCode(200).
                extract().response().path("workspaces[0].name");
        System.out.println("workspace name: " + name);


    }


    @Test
    public void hamcrest_assert_on_extracted_response() {
        String name = given().baseUri("https://api.postman.com").header("X-Api-Key", "PMAK-641f5b47015d090cf6d61f07-b3426baac58c3d9b17f3c1f536b3b02514").
                when().get("/workspaces").
                then().assertThat().statusCode(200).
                extract().response().path("workspaces[0].name");
        System.out.println("workspace name: " + name);

        assertThat(name,equalTo("My Workspace"));

        Assert.assertEquals(name,"My Workspace");


    }
}
