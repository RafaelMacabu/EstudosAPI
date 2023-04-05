package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class requestPayloadAsJsonArray {
    RequestSpecification customRequestSpec;
    ResponseSpecification customSpecification;
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://75d89141-4328-4463-a1ca-7ff3f87fc814.mock.pstmn.io").
                addHeader("x-mock-match-request-body","true").
                /*setConfig(config.encoderConfig(EncoderConfig.encoderConfig().
                        appendDefaultContentCharsetToContentTypeIfUndefined(false))).*/
                setContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);
        customRequestSpec = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_post_request_payload_json_array_as_list(){
        HashMap<String,String> obj5001 = new HashMap<>();
        obj5001.put("id","5001");
        obj5001.put("type","None");
        HashMap<String,String> obj4003 = new HashMap<>();
        obj4003.put("id","4003");
        obj4003.put("type","Glazed");

        List<Map> jsonList = new ArrayList<>();
        jsonList.add(obj5001);
        jsonList.add(obj4003);
        given(customRequestSpec).
                body(jsonList).
        when().
                post("/post").
        then().spec(customSpecification).
                assertThat().
                body("msg",equalTo("sucess"));
    }
}
