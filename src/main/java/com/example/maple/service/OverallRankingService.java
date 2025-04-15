package com.example.maple.service;

import com.example.maple.configure.MapleProperties;
import com.example.maple.domain.RankingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
                                 MapleProperties props
    ) {
        this.webClient = webClientBuilder
                .baseUrl(props.getRanking())
                .defaultHeader("x-nxopen-api-key", props.getApikey())
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

            System.out.println("응답 결과 : " + response.getRanking().get(1).getCharacterName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
