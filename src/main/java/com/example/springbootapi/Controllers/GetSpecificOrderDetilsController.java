package com.example.springbootapi.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class GetSpecificOrderDetilsController {

    @Value("${shiprocket.api.url}")
    private String shiprocketApiUrl;

    @Value("${shiprocket.api.token}")
    private String shiprocketApiToken;

    @GetMapping("/show/{orderId}")
    public ResponseEntity<String> showOrder(@PathVariable("orderId") String orderId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = shiprocketApiUrl + "/v1/external/orders/show/" + orderId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + shiprocketApiToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response;
        } catch (RestClientException e) {
            return ResponseEntity.status(500).body("Error fetching order details: " + e.getMessage());
        }
    }
}
