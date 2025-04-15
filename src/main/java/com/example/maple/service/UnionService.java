package com.example.maple.service;

import com.example.maple.configure.MapleProperties;
import com.example.maple.dto.UnionChampionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class UnionService {

    private final WebClient webClient;

    @Autowired
    public UnionService(WebClient.Builder webClientBuilder,
                          MapleProperties props
    ) {
        this.webClient = webClientBuilder
                .baseUrl(props.getUnion())
                .defaultHeader("x-nxopen-api-key", props.getApikey())
                .build();
    }

    public UnionChampionResponse getUnionChampion(String ocid) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/union-champion")
                            .queryParam("ocid", ocid)
                            .build())
                    .retrieve()
                    .bodyToMono(UnionChampionResponse.class)
                    .block();

        } catch (Exception e) {
            e.printStackTrace();

            UnionChampionResponse emptyResponse = new UnionChampionResponse();
            emptyResponse.setUnionChampion(Collections.emptyList());
            return emptyResponse;
        }
    }
}
