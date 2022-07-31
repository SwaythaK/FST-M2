package liveproject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class GitHubProject {

    RequestSpecification requestSpec;

    String sshKey;

    int sshKeyId;

    String baseURI = "https://api.github.com";
    @BeforeClass
    public void setUp() {

        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","token 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" )
                .build();
        sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCbX+XWt41axqD+V2av9By5TQz8WKkv2892BQbmE4yTtFoAc4rnf+bMGVdZ6d1sYqp2CUv2OtSUmJq7sd74OKzlTeRDdU5HOglGvFya7P/vON+pJbOIW3pWHr7D5Hhw2XEoLz7AxxB9ExQffA/uBx6bkPV46A7MlyJOE9QzPoBLub/XpNN6pBqj06a/QN+b2rfsY2fozRcxv0tA4E9a/eYvfk18iEDFiWxZBwJQ5K7MbWvdlrK/Rrdu8OImmgkQkPcdXU+oiioWiwhij7ZM7WRHrWlzBF5egMTk3zJcaB7MKjmhLJPf6tOYigR1mCv5aUmqGBx65pkQt67Dn3buISG5";


    }

    @Test(priority = 1)
    public void addSSHKey()
    {
        String reqBody = "{\"title\": \"TestAPIKey\" , \"key\": \""+sshKey+"\"}";

        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post(baseURI+"/user/keys");

        System.out.println(response.getBody().asString());

        //extract SSHKEYID
        sshKeyId = response.then().extract().path("id");

        //Assertions
        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getSSH()
    {
        Response response =given().spec(requestSpec)
                .pathParam("sshKeyId", sshKeyId)
                .when().get(baseURI+"/user/keys/{sshKeyId}");

        System.out.println(response.getBody().asString());

        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteSSH()
    {
        Response response = given().spec(requestSpec)
                .pathParam("sshKeyId", sshKeyId)
                .when().delete(baseURI+"/user/keys/{sshKeyId}");
        System.out.println(response.getBody().asString());

        //Assertions
        response.then().statusCode(204);

    }


}
