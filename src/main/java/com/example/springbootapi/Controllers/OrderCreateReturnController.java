package com.example.springbootapi.Controllers;

import okhttp3.*;
import okhttp3.RequestBody;



import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController

@RequestMapping("/orders")
public class OrderCreateReturnController {

    private static final String SHIPROCKET_API_URL = "https://apiv2.shiprocket.in/v1/external/orders/create/return";
    private static final String SHIPROCKET_API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjQ2NjY1MDMsInNvdXJjZSI6InNyLWF1dGgtaW50IiwiZXhwIjoxNzE2ODI1NjIzLCJqdGkiOiJqRHcxQUxRV1Z3RjgzbE5KIiwiaWF0IjoxNzE1OTYxNjIzLCJpc3MiOiJodHRwczovL3NyLWF1dGguc2hpcHJvY2tldC5pbi9hdXRob3JpemUvdXNlciIsIm5iZiI6MTcxNTk2MTYyMywiY2lkIjo0NTEzNTU2LCJ0YyI6MzYwLCJ2ZXJib3NlIjpmYWxzZSwidmVuZG9yX2lkIjowLCJ2ZW5kb3JfY29kZSI6IiJ9.F7XtkhXNPra3lrznJ1govZ55lw5gb_12TeuSR_cAUCE";

    @PostMapping("/create/return")
    public String createReturnOrder(@org.springframework.web.bind.annotation.RequestBody String body) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(SHIPROCKET_API_URL)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + SHIPROCKET_API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
}