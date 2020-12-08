package br.com.asilva.rest.test;

import br.com.asilva.rest.core.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BarrigaTest extends BaseTest {

    private String TOKEN;

    @Before
    public void login(){
        Map<String,String> login = new HashMap<String, String>();
        login.put("email", "anderson@silva");
        login.put("senha", "123456");

        TOKEN = given()
                .body(login)
                .when()
                .post("/signin")
                .then()
                .statusCode(200)
                .extract().path("token");
    }

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

        Map<String,String> conta = new HashMap<String, String>();
        conta.put("nome", "CONTA QUALQUER");
        given()
                .header("Authorization", "JWT " + TOKEN)
                .body(conta)
        .when()
                .post("/contas")
        .then()
            .statusCode(201);

    }

    @Test
    public void deveAlterarContaComSucesso(){

        Map<String,String> conta = new HashMap<String, String>();
        conta.put("nome", "CONTA ALTERADA");
        given()
                .header("Authorization", "JWT " + TOKEN)
                .body(conta)
        .when()
                .put("/contas/344272")
        .then()
                .statusCode(200)
                .body("nome", is("CONTA ALTERADA"));
    }

    @Test
    public void naoDeveInserirContaComMesmoNome(){

        Map<String,String> conta = new HashMap<String, String>();
        conta.put("nome", "CONTA ALTERADA");
        given()
                .header("Authorization", "JWT " + TOKEN)
                .body(conta)
        .when()
                .post("/contas")
        .then()
                .statusCode(400)
                .body("error", is("Já existe uma conta com esse nome!"));
    }

    @Test
    public void deveInserirMovimentacaoSucesso(){

        Movimentacao mov = new Movimentacao();
        mov.setConta_id(344272);
        //mov.setUsuario_id(usuario_id);
        mov.setDescricao("Descricao da movimentacao");
        mov.setEnvolvido("Envolvido da mov");
        mov.setTipo("REC");
        mov.setData_transacao("01/01/2020");
        mov.setData_pagamento("10/05/2020");
        mov.setValor(100f);
        mov.setStatus(true)
        ;


        given()
                .header("Authorization", "JWT " + TOKEN)
                .body(mov)
        .when()
                .post("/transacoes")
        .then()
                .statusCode(201);
    }

}
