package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {

    //base url
    final static String ROOT_URI = "https://petstore.swagger.io/v2/user";

    @Test(priority=1)
    public void addNewUserFromFile() throws IOException {
        // Import JSON file
        FileInputStream inputJSON = new FileInputStream("src/test/java/activities/userinfo.json");
        // Read JSON file as String
        String reqBody = new String(inputJSON.readAllBytes());

        Response response =
                given().contentType(ContentType.JSON)
                        .body(reqBody)
                        .when().post(ROOT_URI);

        inputJSON.close();

        System.out.println(response.getBody().asString());

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("1111"));
    }

    @Test(priority=2)
    public void getUserInfo() {
        // Import JSON file to write to
        File outputJSON = new File("src/test/java/activities/userGETResponse.json");

        Response response =
                given().contentType(ContentType.JSON)
                        .pathParam("username", "Swaytha")
                        .when().get(ROOT_URI + "/{username}");

        // Get response body
        String resBody = response.getBody().asPrettyString();
        System.out.println(response.getBody().asString());

        try {
            // Create JSON file
            outputJSON.createNewFile();
            // Write response body to external file
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assertion
        response.then().body("id", equalTo(1111));
        response.then().body("username", equalTo("Swaytha"));
        response.then().body("firstName", equalTo("Swaytha"));
        response.then().body("lastName", equalTo("K"));
        response.then().body("email", equalTo("abc@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9999999999"));
    }

    @Test(priority=3)
    public void deleteUser() throws IOException {
        Response response =
                given().contentType(ContentType.JSON)
                        .pathParam("username", "Swaytha")
                        .when().delete(ROOT_URI + "/{username}");

        System.out.println(response.getBody().asString());

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("Swaytha"));
    }

}
