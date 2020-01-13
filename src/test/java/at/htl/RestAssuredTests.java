package at.htl;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class RestAssuredTests {


    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void t01_GetDataTest(){
        get("/persons").then().statusCode(200).assertThat()
                .body("size()", is(2))
                .body("id", containsInAnyOrder(1,2))
                .body("name",containsInAnyOrder("Meier","Hofer"));
    }

    @Test
    public void t02_PostDataAndDeleteTest(){
        String payload = "{\"name\":\"Neubauer\"}";

        int postedId = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/persons")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .body().path("id");
        given().delete("/persons/"+postedId).then().statusCode(204);
        get("/persons").then().statusCode(200).assertThat()
                .body("size()", is(2));
    }
}
