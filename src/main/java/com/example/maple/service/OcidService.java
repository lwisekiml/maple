package com.example.maple.service;

import com.example.maple.configure.MapleProperties;
import com.example.maple.dto.OcidResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class OcidService {

    private final WebClient webClient;

    @Autowired
    public OcidService(WebClient.Builder webClientBuilder,
                       MapleProperties props) {
        this.webClient = webClientBuilder
                .baseUrl(props.getId())
                .defaultHeader("x-nxopen-api-key", props.getApikey())
                .build();
    }

    public OcidResponse getOcid(String charaterName) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("character_name", charaterName)
                            .build())
                    .retrieve()
                    .bodyToMono(OcidResponse.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();

            OcidResponse emptyResponse = new OcidResponse();
            emptyResponse.setOcid(Collections.emptyList().toString());
            return emptyResponse;
        }
    }
}
