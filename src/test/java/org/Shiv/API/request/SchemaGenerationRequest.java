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

        //Gson gson = new GsonBuilder ().disableHtmlEscaping().create();
        //JsonObject promptJson = gson.fromJson (prompt, JsonObject.class);

        // Set the base URI and base path from your configuration
        /*RestAssured.baseURI = ConfigLoader.getApiBaseURI(); // "https://generativelanguage.googleapis.com/"
        RestAssured.basePath = "v1beta/models/"; // Only set basePath

        // Retrieve the model name
        String model = ConfigLoader.getModel(); // e.g., "gemini-1.5-flash"
        String action = "generateContent"; // Fixed action name

        // Create the full endpoint URL without URL encoding
        String endpoint = model + ":" + action; // Ensure the colon is not encoded

        // Construct the complete URL
        String fullUrl = "https://generativelanguage.googleapis.com/v1beta/models/"+"gemini-1.5-flash:generateContent";

        System.out.println("Full URL: " + fullUrl);
        // Create the request specification
        RequestSpecification request = RestAssured.given();

        // Set headers
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        request.headers(header);

        // Set query parameters
        request.queryParam("key", ConfigLoader.getApiKey()); // Adds the API key as a query parameter

        // Create request body
        Map<String, String> bodymap = new HashMap<>();
        bodymap.put("text", "Generate schema for given json");

        List<Object> parts = new ArrayList<>();
        parts.add(bodymap);

        Map<String, Object> partsMap = new HashMap<>();
        partsMap.put("parts", parts);

        List<Object> contents = new ArrayList<>();
        contents.add(partsMap);

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("contents", contents);

        // Convert the content map to JSON
        String requestBody = gson.toJson(contentMap);
        request.body(requestBody);

        // Print the request details
        System.out.println("Request Body: " + requestBody);

        // Construct the full URL for the request
        System.out.println("Full URL: " + fullUrl);
        System.out.println("Request URL before sending: " + request.log ().uri ());


        // Send the POST request
        Response response = request.post(fullUrl.replace ("%3A",""));
*/
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
