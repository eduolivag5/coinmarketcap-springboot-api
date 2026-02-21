package com.example.coinmarketcap.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Map;

@Service
public class CryptoService {

    @Value("${coinmarketcap.api.key}")
    private String apiKey;

    @Value("${coinmarketcap.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public CryptoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Object> callCmc(String path, Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("X-CMC_PRO_API_KEY", apiKey);

        // Concatenamos primero para evitar que el builder se confunda con tipos no String
        String fullUrl = baseUrl + path;

        // Cambiamos fromHttpUrl por fromUriString
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fullUrl);

        if (params != null) {
            params.forEach((key, value) -> {
                if (value != null) {
                    builder.queryParam(key, value);
                }
            });
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // .build().toUri() es la forma más "profesional" y segura de pasarlo a RestTemplate
        return restTemplate.exchange(
                builder.build().toUri(),
                HttpMethod.GET,
                entity,
                Object.class
        );
    }
}