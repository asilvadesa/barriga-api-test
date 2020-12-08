package br.com.asilva.rest.test;

import br.com.asilva.rest.core.BaseTest;
import io.restassured.RestAssured;
import org.junit.Test;

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
}
