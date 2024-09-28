package com.apipetstory.utils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import com.apipetstory.pojo.PetPojo;
import com.apipetstory.pojo.UsuarioPojo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiUtils {
    
    public static Response postCadastrarUsuario(UsuarioPojo usuario, String endpoint){
        return given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                    .post(endpoint);
    }
    public static Response postCadastrarPet(PetPojo pet, String endpoint){
        return given()
            .contentType(ContentType.JSON)
            .body(pet)
        .when()
            .post(endpoint);
    }

    public static void validarStatusCode(Response response, int expectedStatusCode){
        response.then().statusCode(expectedStatusCode);
    }

    public static void validarTempoResposta(Response response, long tempoMaximo){
        response.then().time(lessThan(tempoMaximo));
    }

    public static void validarCorpoDaRespostaCadastroPet(Response response, String expectedName, String expectedStatus, String expectedCategory){
        response.then().and()
            .body("name", equalTo(expectedName))
            .body("status", equalTo(expectedStatus))
            .body("category.name", equalTo(expectedCategory));
    }
}
