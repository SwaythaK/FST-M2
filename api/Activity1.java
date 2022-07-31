package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class Activity1 {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    int petId;


    // Base URL
    final static String ROOT_URI = "https://petstore.swagger.io/v2/pet";



    @Test(priority = 1)
    public void addNewPet()
    {
        String reqBody = "{\"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";

        Response response = given().contentType(ContentType.JSON)
                .body(reqBody)
                .when().post(ROOT_URI);

        System.out.println(response.getBody().asString());

        //extract petID
        petId = response.then().extract().path("id");

        //Assertions
        // Assertion
        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test(priority = 2)
    public void getPet()
    {
        Response response =given().contentType(ContentType.JSON)
                .pathParam("petId", 77232)
                .when().get(ROOT_URI+"/{petId}");

        System.out.println(response.getBody().asString());
        // Assertion
        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test(priority = 3)
    public void removePet()
    {
        Response response = given().contentType(ContentType.JSON)
                .when().pathParam("petId", "77232")
                .delete(ROOT_URI + "/{petId}");
        System.out.println(response.getBody().asString());

        //Assertions
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo(""+petId));

    }

}
