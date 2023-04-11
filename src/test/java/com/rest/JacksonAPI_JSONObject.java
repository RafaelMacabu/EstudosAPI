package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;

public class JacksonAPI_JSONObject {
    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-641f5b47015d090cf6d61f07-b3426baac58c3d9b17f3c1f536b3b02514").
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
    public void validate_post_request_payload_as_map() throws JsonProcessingException {
        HashMap<String,Object> mainObject = new HashMap<>();

        HashMap<String,String> nestedObject = new HashMap<>();
        nestedObject.put("name","myThirdWorkspace");
        nestedObject.put("type","personal");
        nestedObject.put("description","Created By HashMap");

        mainObject.put("workspace",nestedObject);

        ObjectMapper objectMapper = new ObjectMapper();
        String mainObjectStr = objectMapper.writeValueAsString(mainObject);

        given().
                body(mainObjectStr).
                when().
                post("/workspaces").
                then().spec(responseSpecification).
                assertThat().
                body("workspace.name", equalTo("myThirdWorkspace"));
    }
}
