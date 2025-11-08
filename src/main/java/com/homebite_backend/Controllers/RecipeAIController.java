package com.homebite_backend.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@CrossOrigin(origins = "http://localhost:8080") // Adjust to your frontend origin
@RestController
@RequestMapping("/api")
public class RecipeAIController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeAIController.class);
    private static final String API_KEY = "0JvUPRvHOGv8EFDi8ksFLg==lUW2PKdKpX2MmDB0";

    @PostMapping("/generateRecipe")
    public ResponseEntity<String> generateRecipe(@RequestBody String ingredients) {
        logger.info("Received ingredients: {}", ingredients);
        try {
            String encodedIngredients = URLEncoder.encode(ingredients, StandardCharsets.UTF_8);
            String baseUrl = "https://api.api-ninjas.com/v1/recipe?query=" + encodedIngredients;

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", API_KEY);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);

            return ResponseEntity.ok(response.getBody());

        } catch (HttpStatusCodeException e) {
            logger.error("API error: {}", e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("API call error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API call error");
        }
    }
}
