package org.Shiv;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.Shiv.utils.APIResponseSaver;
import org.hamcrest.MatcherAssert;
public class APIResponseGuard
{
    public String createBaseAPIResponse(Response apiresponse, String apiURL)
        throws IOException, URISyntaxException, InterruptedException {

        // setting up unique schema filename containing api url
        apiURL = apiURL.replace ("https","");
        apiURL =apiURL.replace ("/","");
        String filePath = apiURL + "_baseApiResponse.json";

        // checking api schema is existed or new api recorded
        boolean value = APIResponseSaver
            .verifySchemaExist (apiresponse, filePath);
        // incase value is true i.e schema existed and proceeding to comparison
        if(value) {
            // compare response with existing schema
            compareresponse (filePath, apiresponse);
        }
        return filePath;
    }

    public boolean compareresponse(String filepath, Response latestResponse)
        throws IOException, URISyntaxException, InterruptedException {
        try {
            File file = new File ("src/test/resources/" + filepath);
            // checking file exist in the directory
            assertThat ("The JSON schema file should exist in the classpath", file.exists ());

            // checking existing schema validation with latest response
            latestResponse.then ()
                .assertThat ()
                .body (JsonSchemaValidator.matchesJsonSchemaInClasspath (filepath));
            System.out.println ("Schema Matched Successfully ----- ");
            return true;
        } catch (AssertionError e) {
            System.err.println ("Assertion failed: " + e.getMessage ());
            return false;
        } catch (Exception e) {
            System.err.println ("An error occurred: " + e.getMessage ());
            return false;
        }
    }
    public String createBaseAPIResponse(String apiresponse, String apiURL)
        throws IOException, URISyntaxException, InterruptedException {

        // setting up unique schema filename containing api url
        apiURL = apiURL.replace ("https","");
        apiURL =apiURL.replace ("/","");
        String filePath = apiURL + "_baseApiResponse.json";

        // checking api schema is existed or new api recorded
        boolean value = APIResponseSaver
            .verifySchemaExist (apiresponse, filePath);
        // incase value is true i.e schema existed and proceeding to comparison
        if(value) {
            // compare response with existing schema
            compareresponse (filePath, apiresponse);
        }
        return filePath;
    }

    public boolean compareresponse(String filepath, String latestResponse)
        throws IOException, URISyntaxException, InterruptedException {
        try {
            File file = new File ("src/test/resources/" + filepath);
            // checking file exist in the directory
            assertThat ("The JSON schema file should exist in the classpath", file.exists ());

            // checking existing schema validation with latest response
            JsonPath jsonPath = new JsonPath(latestResponse);
            assertThat(jsonPath.prettify(), JsonSchemaValidator.matchesJsonSchemaInClasspath(filepath));

            System.out.println ("Schema Matched Successfully ----- ");
            return true;
        } catch (AssertionError e) {
            System.err.println ("Assertion failed: " + e.getMessage ());
            return false;
        } catch (Exception e) {
            System.err.println ("An error occurred: " + e.getMessage ());
            return false;
        }
    }
}
