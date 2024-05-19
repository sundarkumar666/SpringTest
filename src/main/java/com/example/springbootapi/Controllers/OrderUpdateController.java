package com.example.springbootapi.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
@RequestMapping("/order")
public class OrderUpdateController {

    private static final String SHIPROCKET_API_URL = "https://apiv2.shiprocket.in/v1/external/orders/update/adhoc";
    private static final String AUTHORIZATION_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjQ2NjY1MDMsInNvdXJjZSI6InNyLWF1dGgtaW50IiwiZXhwIjoxNzE2ODI1NjIzLCJqdGkiOiJqRHcxQUxRV1Z3RjgzbE5KIiwiaWF0IjoxNzE1OTYxNjIzLCJpc3MiOiJodHRwczovL3NyLWF1dGguc2hpcHJvY2tldC5pbi9hdXRob3JpemUvdXNlciIsIm5iZiI6MTcxNTk2MTYyMywiY2lkIjo0NTEzNTU2LCJ0YyI6MzYwLCJ2ZXJib3NlIjpmYWxzZSwidmVuZG9yX2lkIjowLCJ2ZW5kb3JfY29kZSI6IiJ9.F7XtkhXNPra3lrznJ1govZ55lw5gb_12TeuSR_cAUCE";

    private final OkHttpClient httpClient = new OkHttpClient();

    @PostMapping("/update")
    public ResponseEntity<String> updateOrder(@RequestBody String body) {
        // Create request body
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);

        // Build the request
        Request request = new Request.Builder()
                .url(SHIPROCKET_API_URL)
                .header("Authorization", AUTHORIZATION_TOKEN)
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            // Check if the response is not successful and return the appropriate HTTP status and message
            if (!response.isSuccessful() && response.body() != null) {
                return ResponseEntity.status(response.code()).body(response.body().string());
            }

            // Return the response body if successful
            if (response.body() != null) {
                return ResponseEntity.ok(response.body().string());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Response body is null");
            }
        } catch (Exception e) {
            // Log the exception and return an internal server error status with the exception message
            e.printStackTrace(); // Optionally, use a logging framework
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}