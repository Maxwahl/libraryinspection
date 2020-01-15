package at.htl;


import at.htl.library.model.Person;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class RestAssuredTests {


    private static RequestSpecification spec;
    @BeforeAll
    public static void initSpec(){
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:8080/")
                .addFilter(new ResponseLoggingFilter())//log request and response for better debugging. You can also only log if a requests fails.
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void t01_GetDataTest(){
        JsonPath jsonPath = given().
                spec(spec).
        //put queryparams here
                when().
                get("/persons").
                then().
                statusCode(200).
                assertThat().
                extract().
                jsonPath();

        assertThat(jsonPath.getList("")).hasSize(2);
        assertThat(jsonPath.getList("id")).containsExactly(1,2);


    }

    @Test
    public void t02_PostDataAndDeleteTest(){
        String payload = "{\"name\":\"Neubauer\"}";

        int postedId = given()
                .spec(spec)
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

    @Test
    public void t03_GetSinglePersonTest(){
        Person person =given().spec(spec).when().
                get("/persons/1").then().statusCode(200).assertThat().extract().as(Person.class);
        assertThat(person.getId()).isEqualTo(1);
        assertThat(person.getName()).isEqualTo("Meier");
    }
}
