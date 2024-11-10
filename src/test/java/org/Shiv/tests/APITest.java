package org.Shiv.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import io.restassured.response.Response;
import org.Shiv.API.request.MediaGenerationRequest;
import org.Shiv.APIResponseGuard;
import org.testng.annotations.Test;


public class APITest {
    @Test
    public void getapiresult() throws IOException, URISyntaxException, InterruptedException {
        MediaGenerationRequest mediaGenerationRequest = new MediaGenerationRequest ();
        Response mediaresponse = mediaGenerationRequest.getImageGenerationRequest ("Natural landsacpe");

        APIResponseGuard apiResponseGuard = new APIResponseGuard ();
        String file = apiResponseGuard.createBaseAPIResponse (mediaresponse,"https://api.pexels.com/videos/search");
        System.out.println ("---"+file);
    }
}
