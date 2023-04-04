package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomatePut {

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
        String workspaceId = "71b91363-56c9-43ff-a204-a4a3c816a210";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"RestAssuredWorkspaceEditadoo\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"Criado pelo RestAssured e Editado\"\n" +
                "    \n" +
                "    }\n" +
                "}";
        given().
                body(payload).
                pathParam("workspaceId",workspaceId).
                when().
                put("/workspaces/{workspaceId}").
                then().
                assertThat().
                body("workspace.name",equalTo("RestAssuredWorkspaceEditadoo"),
                        "workspace.id",matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id",equalTo(workspaceId));


    }

}
