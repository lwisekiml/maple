package com.example.maple.service;

import com.example.maple.domain.RankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;

@Service
public class OverallRankingService {

    private final WebClient webClient;

//    @Value("${nexon.maple.apikey}")
//    private String API_KEY;
//
//    @Value("${nexon.maple.ranking}")
//    private String rankingUrl;

    // @Value값이 생성자 기반 주입후에 실행되어 API_KEY, rankingUrl 값이 없다.
    // 그래서 파라미터로 넣어줌
    @Autowired
    public OverallRankingService(WebClient.Builder webClientBuilder,
                                 @Value("${nexon.maple.apikey}") String API_KEY,
                                 @Value("${nexon.maple.ranking}") String rankingUrl
    ) {
        this.webClient = webClientBuilder
                .baseUrl(rankingUrl)
                .defaultHeader("x-nxopen-api-key", API_KEY)
                .build();
    }

    public void getRanking(String date) {
//        try {
//            WebClient webClient = WebClient.builder()
//                    .baseUrl("https://open.api.nexon.com/maplestory/v1/ranking")
//                    .defaultHeader("x-nxopen-api-key", API_KEY)
//                    .build();
//
//            RankingResponse response = webClient.get()
//                    .uri(uriBuilder -> uriBuilder
//                            .path("/overall")
//                            .queryParam("date", "2025-03-01")
//                            .build())
//                    .retrieve()
//                    .bodyToMono(RankingResponse.class)
//                    .block();
//
//            // Subscribe or block to get the result
//            System.out.println(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        try {
            RankingResponse response = webClient.get()
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

            System.out.println("응답 결과 : " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
