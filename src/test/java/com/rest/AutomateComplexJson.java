package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomateComplexJson {

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://75d89141-4328-4463-a1ca-7ff3f87fc814.mock.pstmn.io").
                addHeader("x-mock-match-request-body","true").
                setContentType("application/json;charset=utf-8").
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
        List<Integer> idArrayList = new ArrayList<>();
        idArrayList.add(5);
        idArrayList.add(9);

        HashMap<String,Object> batterHashmap2 = new HashMap<>();
        batterHashmap2.put("id",idArrayList);
        batterHashmap2.put("type","Chocolate");

        HashMap<String,Object> batterHashmap1 = new HashMap<>();
        batterHashmap1.put("id","1001");
        batterHashmap1.put("type","Regular");

        List<HashMap<String,Object>> batterArrayList = new ArrayList<>();
        batterArrayList.add(batterHashmap1);
        batterArrayList.add(batterHashmap2);

        HashMap<String,List<HashMap<String,Object>>> battersHashmap = new HashMap<>();
        battersHashmap.put("batter",batterArrayList);

        List<String> typeArrayList = new ArrayList<>();
        typeArrayList.add("test1");
        typeArrayList.add("test2");

        HashMap<String,Object> toppingHashmap2 = new HashMap<>();
        toppingHashmap2.put("id","5002");
        toppingHashmap2.put("type",typeArrayList);

        HashMap<String,Object> toppingHashmap1 = new HashMap<>();
        toppingHashmap1.put("id","5001");
        toppingHashmap1.put("type","None");

        List<HashMap<String,Object>> toppingArrayList = new ArrayList<>();
        toppingArrayList.add(toppingHashmap1);
        toppingArrayList.add(toppingHashmap2);

        HashMap<String,Object> mainHashmap = new HashMap<>();
        mainHashmap.put("id","0001");
        mainHashmap.put("type","donut");
        mainHashmap.put("name","Cake");
        mainHashmap.put("ppu",0.55);
        mainHashmap.put("batters",battersHashmap);
        mainHashmap.put("topping",toppingArrayList);
        given().
                body(mainHashmap).
                when().
                post("/postComplexJson").
                then().spec(responseSpecification).
                assertThat().
                body("msg",equalTo("sucess"));


    }
}
