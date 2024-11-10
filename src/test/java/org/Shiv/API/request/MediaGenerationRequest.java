package org.Shiv.API.request;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.Shiv.utils.ConfigLoader;
import org.testng.Assert;

    public class MediaGenerationRequest {
        public Response getImageGenerationRequest (String prompt) {
            RestAssured.baseURI = "https://api.pexels.com";
            RestAssured.basePath = "/videos/search";

            final RequestSpecification request = given();
            request.header ("Authorization", ConfigLoader.getImageApiKey ());
            request.queryParam ("query", prompt);
            request.queryParam ("per_page", "1");

            Response response = request.get();
            Assert.assertEquals (response.getStatusCode (),200,"Need to verify that everything is still in order.");
            System.out.println ("Your API is working expected as API response is "+response.statusCode ());

            Assert.assertEquals (response.getStatusCode (),200);
            return response;
    }
}
