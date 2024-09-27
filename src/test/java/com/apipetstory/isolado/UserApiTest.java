package com.apipetstory.isolado;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiTest {


    @BeforeEach
    public void setUp(){
        baseURI = "https://petstore.swagger.io";
        // port = 8080;
        basePath = "/v2";
    }



    @Test
    @Order(1)
    public void testCadastrar(){
            int status = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                "  \"id\": 1,\n" +
                "  \"username\": \"user1\",\n" +
                "  \"firstName\": \"user1\",\n" +
                "  \"lastName\": \"user1\",\n" +
                "  \"email\": \"user1@email.com\",\n" +
                "  \"password\": \"user1\",\n" +
                "  \"phone\": \"user1\",\n" +
                "  \"userStatus\": 1\n" +
                "}")
                .when()
                    .post("/user")
                .then()
                    .log()
                        .all()
                        .extract()
                            .statusCode();
                assertEquals(200, status);

    }
    @Test
    @Order(2)
    public void testFindUser(){

        Response response = given()
        .when()
            .get("/user/user1");

        response.then().statusCode(200)
            .and()
            .body("username", equalTo("user1"))
            .body("email", equalTo("user1@email.com"));
        
    }
    @Test
    @Order(3)
    public void testSubmeterLogin(){

        String message = given()
        .when()
            .get("/user/login?username=admin&password=admin")
        .then()
            .extract()
                .path("message");

        assertTrue(message.contains("logged in user session:"));
    }
    @Test
    @Order(4)
    public void testCadastroDePetALoja() throws IOException{


        String requestBody = "{\n" +
                "  \"id\": 123,\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Dogs\"\n" +
                "  },\n" +
                "  \"name\": \"Rex\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://example.com/photo1.jpg\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Friendly\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/pet");


        response.then().statusCode(200)
            .and()
            .body("name", equalTo("Rex"))
            .body("status", equalTo("available"));
    }
    


}
