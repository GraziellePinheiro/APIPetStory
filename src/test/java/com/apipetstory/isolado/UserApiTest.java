package com.apipetstory.isolado;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.apipetstory.config.Configuracoes;
import com.apipetstory.factory.UsuarioDataFactory;
import com.apipetstory.pojo.Category;
import com.apipetstory.pojo.PetPojo;
import com.apipetstory.pojo.Tag;
import com.apipetstory.pojo.UsuarioPojo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiTest {
    private UsuarioPojo usuarioComum;

    @BeforeEach
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        // port = configuracoes.port();
        basePath = configuracoes.basePath();

        this.usuarioComum = UsuarioDataFactory.criarUsuarioPojo();
    }



    @Test
    @Order(1)
    public void testCadastrar(){

            int status = given()
                .contentType(ContentType.JSON)
                .body(usuarioComum)
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

        PetPojo newPet = new PetPojo();
        newPet.setId(1);

        Category category = new Category();
        category.setId(1);
        category.setName("Dogs");
        newPet.setCategory(category);

        newPet.setName("Rex");

        newPet.setPhotoUrls(List.of(" "));

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Friendly");
        newPet.setTags(List.of(tag));

        newPet.setStatus("available");



        Response response = given()
            .contentType(ContentType.JSON)
            .body(newPet)
        .when()
            .post("/pet");


        response.then().statusCode(200)
            .and()
            .body("name", equalTo("Rex"))
            .body("status", equalTo("available"));
    }
    


}
