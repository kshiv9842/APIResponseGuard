package org.Shiv.API.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.Shiv.utils.ConfigLoader;
import org.testng.Assert;

public class SchemaGenerationRequest {
    public HttpResponse getSchemaGenerationRequest(String prompt)
        throws IOException, URISyntaxException, InterruptedException {

        // Set the base URI and base path from your configuration
        URI uri = new URI(ConfigLoader.getApiBaseURI() + "v1beta/models/" +
            ConfigLoader.getModel() + ":generateContent?key=" +
            ConfigLoader.getApiKey());

        // Set up HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Create request body
        Map<String, Object> bodyMap = Map.of(
            "contents", List.of(Map.of("parts", List.of(Map.of("text", "Generate Json schema for given JSON only avoid other info "+prompt))))
        );
        String requestBody = new Gson().toJson(bodyMap);

        // Create the POST request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        // Send the request and get the response
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());


        // Log the response details
        System.out.println("API Response - Status Code: " + response.statusCode ());

        // Validate the response status code
        Assert.assertEquals(response.statusCode (), 200);
        Assert.assertTrue (!response.body ().toString ().isEmpty ());

        return response;
    }
}
