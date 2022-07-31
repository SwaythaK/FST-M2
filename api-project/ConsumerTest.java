package liveproject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

// to tell this class has to be executed as consumer
// we have to run 2 test, consumer side and provider side

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    // global variables for headers
    // will make the headers as map
    Map<String, String> reqheaders = new HashMap<>();

    //API resource path
    String resourcePath="/api/users";

    //Creating the pact , pact needs 2 args - consumer  provider
    @Pact(consumer="UserConsumer", provider="UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder)
    {
        //set the headers
        reqheaders.put("Content-Type", "application/json");

        //Create request and response body
        //value can be anything here just its needs the name
       /* {
            id:
            123,
                    firstName :"String"
        }*/

        DslPart reqResBody=new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName")
                .stringType("lastName")
                .stringType("email");

        return builder.given("Request to create a user")
                .uponReceiving("Request to create a user")
                    .method("POST")
                    .path(resourcePath)
                    .headers(reqheaders)
                    .body(reqResBody)
                .willRespondWith()
                    .status(201)
                    .body(reqResBody)
                .toPact();



    }

    @Test
    @PactTestFor(providerName="UserProvider", port="8282")
    public void consumerTest()
    {
        //set BaseUri
        String baseURI = "http://localhost:8282";

      //define request body
      Map<String, Object> reqBody = new HashMap<>();
      reqBody.put("id", 123);
      reqBody.put("firstName","Swaytha");
      reqBody.put("lastName", "K");
      reqBody.put("email","swaytha.k@ibm.com");

      //Generate Response
      Response response =given().headers(reqheaders).body(reqBody)
              .when().post(baseURI+resourcePath);

      System.out.println(response.getBody().asPrettyString());

      //Assertions
        response.then().statusCode(201);





    }

}
