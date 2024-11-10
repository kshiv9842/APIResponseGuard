package org.Shiv.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import io.restassured.response.Response;
import org.Shiv.API.request.SchemaGenerationRequest;
import org.Shiv.API.response.SchemaGenerationResponse;

public class APIResponseSaver {
    public static boolean verifySchemaExist(Response apiResponse, String filePath)
        throws IOException, URISyntaxException, InterruptedException {
        String filedirectory = "src/test/resources/" + filePath;

        File file = new File (filedirectory);
        // checking json schema for requested api is available..if exist return true else false
        if (file.exists ()) {
            System.out.println ("Schema already exists - " + filePath+" Checking Comparison is any modification...");
            return true;
        }
        //create schema file for not existing api...
        else {
            System.out.println ("Seems new API Recorded...");
            //--- instantiation of schema generation
            SchemaGenerationRequest schemaGenerationRequest = new SchemaGenerationRequest ();
            //--- Schema generation of given api
            HttpResponse response = schemaGenerationRequest.getSchemaGenerationRequest (apiResponse.body ()
                .asString ());

            Gson gson = new GsonBuilder ().disableHtmlEscaping ()
                .create ();

            // parsing response into pojo
            SchemaGenerationResponse schemaGenerationResponse = gson.fromJson (response.body ().toString (),
                SchemaGenerationResponse.class);
            // extracting only schema part from schema genrated api
            String jsonString = schemaGenerationResponse.getCandidates ()
                .get (0)
                .getContent ()
                .getParts ()
                .get (0)
                .getText ()
                .toString ();
            jsonString = jsonString.replace ("```json", "");
            jsonString = jsonString.replace ("```", "");
            JsonObject jsonObject = gson.fromJson (jsonString, JsonObject.class);

            // writing generated schema into file at "src/test/resources" directory
            Path path = Paths.get (filedirectory);
            try {
                Files.createDirectories (path.getParent ());
                try (FileWriter writer = new FileWriter (path.toFile ())) {
                    gson.toJson (jsonObject, writer);
                }
                System.out.println ("Schema saved successfully at: " + filePath);
            } catch (IOException e) {
                e.printStackTrace ();
            }
            return false;
        }
    }
    public static boolean verifySchemaExist(String apiResponse, String filePath)
        throws IOException, URISyntaxException, InterruptedException {
        String filedirectory = "src/test/resources/" + filePath;

        File file = new File (filedirectory);
        // checking json schema for requested api is available..if exist return true else false
        if (file.exists ()) {
            System.out.println ("Schema already exists - " + filePath+" Checking Comparison is any modification...");
            return true;
        }
        //create schema file for not existing api...
        else {
            System.out.println ("Seems new API Recorded...");
            //--- instantiation of schema generation
            SchemaGenerationRequest schemaGenerationRequest = new SchemaGenerationRequest ();
            //--- Schema generation of given api
            HttpResponse response = schemaGenerationRequest.getSchemaGenerationRequest (apiResponse);

            Gson gson = new GsonBuilder ().disableHtmlEscaping ()
                .create ();

            // parsing response into pojo
            SchemaGenerationResponse schemaGenerationResponse = gson.fromJson (response.body ().toString (),
                SchemaGenerationResponse.class);
            // extracting only schema part from schema genrated api
            String jsonString = schemaGenerationResponse.getCandidates ()
                .get (0)
                .getContent ()
                .getParts ()
                .get (0)
                .getText ()
                .toString ();
            jsonString = jsonString.replace ("```json", "");
            jsonString = jsonString.replace ("```", "");
            JsonObject jsonObject = gson.fromJson (jsonString, JsonObject.class);

            // writing generated schema into file at "src/test/resources" directory
            Path path = Paths.get (filedirectory);
            try {
                Files.createDirectories (path.getParent ());
                try (FileWriter writer = new FileWriter (path.toFile ())) {
                    gson.toJson (jsonObject, writer);
                }
                System.out.println ("Schema saved successfully at: " + filePath);
            } catch (IOException e) {
                e.printStackTrace ();
            }
            return false;
        }
    }

}
