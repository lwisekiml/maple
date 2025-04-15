package com.example.maple.service;

import com.example.maple.configure.MapleProperties;
import com.example.maple.dto.RankingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class RankingService {

    private final WebClient webClient;

    @Autowired
    public RankingService(WebClient.Builder webClientBuilder,
                          MapleProperties props
    ) {
        this.webClient = webClientBuilder
                .baseUrl(props.getRanking())
                .defaultHeader("x-nxopen-api-key", props.getApikey())
                .build();
    }

    public RankingResponse getRanking(String date) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/overall")
                            .queryParam("date", date)
                            .build())
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::is4xxClientError,
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("요청 오류 (4xx): " + body))
                    )
                    .onStatus(
                            HttpStatusCode::is5xxServerError,
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("서버 오류 (5xx): " + body))
                    )
                    .bodyToMono(RankingResponse.class)
                    .block();

//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.writeValueAsString(response);
        } catch (Exception e) {
            e.printStackTrace();

            // 항상 RankingResponse 객체를 반환하므로 Controller에서 JSON 변환도 자연스럽게 처리된다.
            // Postman에서 테스트 시에도 항상 일관된 응답 구조를 얻을 수 있다.
            RankingResponse emptyResponse = new RankingResponse();
            emptyResponse.setRanking(Collections.emptyList());
            return emptyResponse;
        }
    }
}
