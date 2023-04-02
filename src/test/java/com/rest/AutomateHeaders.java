package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomateHeaders {

    @Test
    public void multiple_headers(){
        Header header = new Header("headerName","value1");
        Header matchHeader = new Header("x-mock-match-request-headers","headerName");
        Headers headers = new Headers(header,matchHeader);
        given().
                baseUri("https://75d89141-4328-4463-a1ca-7ff3f87fc814.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200).
                header("responseHeader","resValue1");
    }

    @Test
    public void extract_headers(){
        Header header = new Header("headerName","value1");
        Header matchHeader = new Header("x-mock-match-request-headers","headerName");
        Headers headers = new Headers(header,matchHeader);
        Headers extractedHeaders = given().
                baseUri("https://75d89141-4328-4463-a1ca-7ff3f87fc814.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200).
                header("responseHeader","resValue1").
                extract().
                headers();


        for (Header header1 : extractedHeaders){
            System.out.println("header name = " + header1.getName());
            System.out.println("header value = " + header1.getValue());
        }

       /* System.out.println("header name = " + extractedHeaders.get("responseHeader").getName());
        System.out.println("header value = " + extractedHeaders.get("responseHeader").getValue());*/
    }
}
