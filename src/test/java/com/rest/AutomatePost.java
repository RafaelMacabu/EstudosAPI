package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.text.MatchesPattern;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomatePost {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key","PMAK-641f5b47015d090cf6d61f07-b3426baac58c3d9b17f3c1f536b3b02514").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_post_request_bdd_style(){
        File file = new File("src/main/resources/CreateWorkspacePayload.json");
        given().
                body(file).
        when().
                post("/workspaces").
        then().
                assertThat().
                body("workspace.name",equalTo("RestAssuredWorkspace"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"));


    }

    @Test
    public void validate_post_request_non_bdd_style(){
        File file = new File("src/main/resources/CreateWorkspacePayload.json");
        Response response = given().
                body(file).
                post("/workspaces");

        assertThat(response.<String>path("workspace.name"),equalTo("RestAssuredWorkspace"));
        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));


    }

    @Test
    public void validate_post_request_non_bdd_style_objetct_mapping(){
        HashMap<String,Object> mainObject = new HashMap<>();
        HashMap <String,String> nestedObject = new HashMap<>();
        nestedObject.put("name","ObjectMapping");
        nestedObject.put("type","personal");
        nestedObject.put("description","created with hashmap");
        mainObject.put("workspace",nestedObject);
        Response response = given().
                body(mainObject).
                post("/workspaces");

        assertThat(response.<String>path("workspace.name"),equalTo("ObjectMapping"));
        assertThat(response.<String>path("workspace.id"),matchesPattern("^[a-z0-9-]{36}$"));


    }
}
