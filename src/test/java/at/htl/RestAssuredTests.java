package at.htl;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class RestAssuredTests {


    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void t01_GetDataTest(){
        JsonPath jsonPath =get("/persons").then().statusCode(200).assertThat().extract().jsonPath();

        assertThat(jsonPath.getList("")).hasSize(2);
        assertThat(jsonPath.getList("id")).containsExactly(1,2);


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
                .jsonPath()
                .getInt("id");
        given().delete("/persons/"+postedId).then().statusCode(204);
        JsonPath jsonPath = get("/persons").then().statusCode(200).assertThat().extract().jsonPath();
        assertThat(jsonPath.getList("")).hasSize(2);

    }
}
