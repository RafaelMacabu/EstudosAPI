package com.rest;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class RequestParameters {

    @Test
    public void single_value_query_parameter(){
        HashMap<String,String> queryParams = new HashMap<>();
        queryParams.put("foo1","bar1");
        queryParams.put("foo2","bar2");
        given().baseUri("https://postman-echo.com").
                queryParams(queryParams).
                log().all().
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);


    }

    @Test
    public void multi_value_query_parameter(){
        given().baseUri("https://postman-echo.com").
                queryParams("foo1","bar1;bar2;bar3").
                log().all().
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);


    }

    @Test
    public void path_param(){
        given().baseUri("https://reqres.in").
                pathParam("userId","1").
                log().all().
                when().
                get("/api/users/{userId}").
                then().
                log().all().
                assertThat().
                statusCode(200);


    }

    @Test
    public void multipart_form_data(){
        given().
                baseUri("https://postman-echo.com").
                multiPart("foo1","bar1").
                log().all().
                when().
                post("/post").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @Test
    public void upload_file_multipart_form_data(){
        String attributes = "{\"name\" : \"temp.txt.txt\",\"parent\" :{\"id\" : \"123456\"}";
        given().
                baseUri("https://postman-echo.com").
                multiPart("file",new File("temp.txt")).
                multiPart("attributes",attributes,"application/json").
                log().all().
                when().
                post("/post").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @Test
    public void download_file() throws IOException {
        byte[] bytes = given().
                baseUri("BASE URL DO GITHUB").
                log().all().
                when().
                post("LINK DO ARQUIVO").
                then().
                log().all().
                extract().response().asByteArray();

        OutputStream os = new FileOutputStream(new File("NOME DO ARQUIVO"));
        os.write(bytes);
        os.close();

    }


}
