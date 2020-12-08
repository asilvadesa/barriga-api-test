package br.com.asilva.rest.test;

import br.com.asilva.rest.core.BaseTest;
import io.restassured.RestAssured;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class BarrigaTest extends BaseTest {

    @Test
    public void naoDeveAcessarAPISemToken(){
        given()
        .when()
                .get("/contas")
        .then()
                .statusCode(401)
        ;
    }

    @Test
    public void deveIncluirContaComSucesso(){
        Map<String,String> login = new HashMap<String, String>();
        login.put("email", "anderson@silva");
        login.put("senha", "123456");


        String token = given()
                .body(login)
        .when()
                .post("/signin")
        .then()
                .statusCode(200)
                .extract().path("token");


        Map<String,String> conta = new HashMap<String, String>();
        conta.put("nome", "CONTA QUALQUER");
        given()
                .header("Authorization", "JWT " + token)
                .body(conta)
        .when()
                .post("/contas")
        .then()
            .statusCode(201);




    }

}
