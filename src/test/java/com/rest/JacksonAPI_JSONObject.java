package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rest.pojo.simple.SimplePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
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

    @Test
    public void serialize_json_using_jackson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode nestedObjectNode = objectMapper.createObjectNode();
        nestedObjectNode.put("name","myThirrrdWorkspace");
        nestedObjectNode.put("type","personal");
        nestedObjectNode.put("description","Created By HashMap");

        ObjectNode mainObjectNode = objectMapper.createObjectNode();
        mainObjectNode.set("workspace",nestedObjectNode);


        given().
                body(mainObjectNode).
                when().
                post("/workspaces").
                then().spec(responseSpecification).
                assertThat().
                body("workspace.name", equalTo("myThirrrdWorkspace"));
    }

    @Test
    public void serialize_json_array() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNodeList = objectMapper.createArrayNode();

        ObjectNode obj5001Node = objectMapper.createObjectNode();
        obj5001Node.put("id","5001");
        obj5001Node.put("type","None");

        ObjectNode obj4003Node = objectMapper.createObjectNode();
        obj4003Node.put("id","4003");
        obj4003Node.put("type","Glazed");

        arrayNodeList.add(obj5001Node);
        arrayNodeList.add(obj4003Node);

        String jsonListStr = objectMapper.writeValueAsString(arrayNodeList);
   }

    @Test
    public void simple_pojo_example(){
//        SimplePojo simplePojo = new SimplePojo("value1","value2");
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.setKey1("value1");
        simplePojo.setKey2("value2");

        given(requestSpecification).
                baseUri("https://75d89141-4328-4463-a1ca-7ff3f87fc814.mock.pstmn.io").
                body(simplePojo).
                when().
                post("/postSimplePojo").
                then().spec(responseSpecification).
                assertThat().
                body("key1",equalTo("value1"),
                        "key2",equalTo("value2"));
    }

    @Test
    public void deserialize_simple_pojo_example() throws JsonProcessingException {
//        SimplePojo simplePojo = new SimplePojo("value1","value2");
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.setKey1("value1");
        simplePojo.setKey2("value2");

        SimplePojo deserializedPojo = given(requestSpecification).
                baseUri("https://75d89141-4328-4463-a1ca-7ff3f87fc814.mock.pstmn.io").
                body(simplePojo).
                when().
                post("/postSimplePojo").
                then().spec(responseSpecification).
                extract().
                response().as(SimplePojo.class);


        ObjectMapper objectMapper = new ObjectMapper();
        String deserializedPojoStr = objectMapper.writeValueAsString(deserializedPojo);
        String simplePojoStr = objectMapper.writeValueAsString(simplePojo);
        assertThat(objectMapper.readTree(deserializedPojoStr),equalTo(objectMapper.readTree(simplePojoStr)));
    }

}

