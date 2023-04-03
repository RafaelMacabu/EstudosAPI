package com.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MyClass {
    RequestSpecification requestSpecification;

    @BeforeClass
    public void before_class(){
         /*requestSpecification = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-641f5b47015d090cf6d61f07-b3426baac58c3d9b17f3c1f536b3b02514").
                log().all();*/
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                        setBaseUri("https://api.postman.com").
                        addHeader("X-Api-Key","PMAK-641f5b47015d090cf6d61f07-b3426baac58c3d9b17f3c1f536b3b02514").
                        log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

    }

    @Test
    public void validate_status_code(){
        Response response = given().spec(requestSpecification).get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(),is(equalTo(200)));

    }
}
