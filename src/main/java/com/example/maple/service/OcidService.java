package com.example.maple.service;

import com.example.maple.configure.MapleProperties;
import com.example.maple.dto.OcidResponse;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OcidService {

    private final WebClient webClient;

    // 테스트 전용 생성자(spy 용동)
    public OcidService() {
        this.webClient = null; // 테스트에서는 사용하지 않으므로 null
    }

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

    public List<OcidResponse> getOcidList(List<String> characterNames) {
        RateLimiter rateLimiter = RateLimiter.create(3); // 초당 3회 제한

        List<OcidResponse> ocidList = characterNames.stream()
                .limit(10) // 10개 제한
                .map(name -> {
                    rateLimiter.acquire();
                    try {
                        log.debug("OCID 요청 중 : {}", name);
                        return Optional.of(this.getOcid(name));
                    } catch (Exception e) {
                        log.warn("OCID 요청 실패 : {}", name, e);
                        return Optional.<OcidResponse>empty();
                    }
                })
                .flatMap(Optional::stream) // Optional -> 실제 값으로 변환
                .toList();
        return ocidList;
    }
}
