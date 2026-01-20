package com.ideaforge.api.infra.ai.gemini;

import com.ideaforge.api.infra.ai.AiGenerationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;

@Service
public class GeminiService implements AiGenerationService {

    private final RestClient restClient;
    private final String apiUrl;
    private final String apiKey;

    public GeminiService(
            RestClient.Builder builder,
            @Value("${gemini.api.url}") String apiUrl,
            @Value("${gemini.api.key}") String apiKey
    ) {
        this.restClient = builder.build();
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    @Override
    public String generateContent(String prompt) {
        System.out.println("Enviando request para o Google Gemini...");

        String finalUrl = apiUrl + "?key=" + apiKey;

        var requestBody = new GeminiRequest(
                List.of(new Content(List.of(new Part(prompt))))
        );

        GeminiResponse response = restClient.post()
                .uri(URI.create(finalUrl))
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(GeminiResponse.class);

        try {
            if (response != null && !response.candidates().isEmpty()) {
                return response.candidates().get(0)
                        .content()
                        .parts().get(0)
                        .text();
            }
        } catch (Exception e) {
            System.err.println("[GeminiService] Error parsing response: " + e.getMessage());
        }

        return "Gemini could not generate the idea. Please try again.";
    }


    record GeminiRequest(List<Content> contents) {}
    record Content(List<Part> parts) {}
    record Part(String text) {}

    record GeminiResponse(List<Candidate> candidates) {}
    record Candidate(Content content) {}
}