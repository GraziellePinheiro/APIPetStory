package com.apipetstory.isolado;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
import com.apipetstory.factory.PetDataFactory;
import com.apipetstory.factory.UsuarioDataFactory;
import com.apipetstory.pojo.Category;
import com.apipetstory.pojo.PetPojo;
import com.apipetstory.pojo.Tag;
import com.apipetstory.pojo.UsuarioPojo;
import com.apipetstory.utils.ApiUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiTest {
    private UsuarioPojo usuarioComum;
    private PetPojo newPet;

    @BeforeEach
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        // port = configuracoes.port();
        basePath = configuracoes.basePath();

        this.usuarioComum = UsuarioDataFactory.criarUsuarioPojo();
        this.newPet = PetDataFactory.criarPetPojo();
    }



    @Test
    @Order(1)
    public void testCadastrarUsuarioComSucesso(){

        try {
            Response response = ApiUtils.postCadastrarUsuario(usuarioComum, "/user");
            ApiUtils.validarStatusCode(response, 200);
            ApiUtils.validarTempoResposta(response, 4000L);
        } catch (Exception e) {
            fail("Erro ao cadastrar usuário: " + e.getMessage());
        }


    }
    @Test
    @Order(2)
    public void testBuscarUsuarioExistente(){

        Response response = given()
        .when()
            .get("/user/user1");

        ApiUtils.validarStatusCode(response, 200);
            response.then().and()
            .body("username", equalTo("user1"))
            .body("email", equalTo("user1@email.com"))
            .body("firstName", equalTo("user1"));
        
    }
    @Test
    @Order(3)
    public void testSubmeterLoginComSucesso(){

        Response response = given()
        .when()
            .get("/user/login?username=admin&password=admin");

            ApiUtils.validarStatusCode(response, 200);

            String message = response
                .then()
                .extract()
                    .path("message");

        assertTrue(message.contains("logged in user session:"));

    }
    @Test
    @Order(4)
    public void testCadastroDeNovoPetALoja(){

        try {
        Response response = ApiUtils.postCadastrarPet(newPet, "/pet");
        ApiUtils.validarStatusCode(response, 200);
        ApiUtils.validarCorpoDaRespostaCadastroPet(response, "Rex", "available", newPet.getCategory().getName());
        } catch (Exception e) {
            fail("Falha ao cadastrar o pet: " + e.getMessage());
        }
    }
    


}
