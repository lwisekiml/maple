package com.example.maple.service;

import com.example.maple.dto.*;
import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankerUnionChampionPopulationService {

    private final RankingService rankingService;
    private final OcidService ocidService;
    private final UnionService unionService;

    List<String> jobList = Arrays.asList(
            "나이트로드", "나이트워커", "다크나이트", "데몬슬레이어", "데몬어벤져",
            "듀얼블레이더", "라라", "루미너스", "메르세데스", "메카닉", "미하일",
            "바이퍼", "배틀메이지", "보우마스터", "블래스터", "비숍", "섀도어",
            "소울마스터", "스트라이커", "신궁", "아델", "아란", "아크", "아크메이지(불,독)",
            "아크메이지(썬,콜)", "에반", "엔젤릭버스터", "와일드헌터", "윈드브레이커", "은월",
            "일리움", "제논", "제로", "카데나", "카이저", "카인", "칼리", "캐논마스터", "캡틴",
            "키네시스", "팔라딘", "패스파인더", "팬텀", "플레임위자드", "호영", "히어로"
    );

    @ResponseBody
    @GetMapping
    public void analyzeUnionChampionPopulation(@RequestParam String date) throws InterruptedException {
        RankingOverallResponse rankingOverall = rankingService.getRankingOverall(date);
        List<String> characterNames = rankingOverall.getRanking().stream()
                .map(CharacterRanking::getCharacterName)
                .toList();

        RateLimiter rateLimiter = RateLimiter.create(3); // 초당 3회 제한

        List<OcidResponse> ocidList = characterNames.stream()
                .limit(10) // 10개 제한
                .map(name -> {
                    rateLimiter.acquire();
                    return ocidService.getOcid(name);
                })
                .toList();

        for (OcidResponse ocid : ocidList) {
            System.out.println("ocid: " + ocid.getOcid());
        }

        List<UnionChampionResponse> UCRList = new ArrayList<>();
        for(OcidResponse ocid : ocidList) {
//            rateLimiter.acquire(); // 적용 안됨...
            Thread.sleep(300);
            System.out.println("union : " + ocid.getOcid());
            UnionChampionResponse unionChampion = unionService.getUnionChampion(ocid.getOcid());
            UCRList.add(unionChampion);
        }

        List<String> championClassList = new ArrayList<>();
        for (UnionChampionResponse ucr : UCRList) {
            for (UnionChampion uc : ucr.getUnionChampion()) {
                championClassList.add(uc.getChampionClass());
            }
        }

        Map<String, Integer> jobCountMap = new HashMap<>();
        for (String job : championClassList) {
            jobCountMap.put(job, jobCountMap.getOrDefault(job, 0) + 1);
        }

        List<Map.Entry<String, Integer>> top5List = jobCountMap.entrySet().stream()
//        Map<String, Integer> top5Map = jobCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // comparingByValue() : 오름차순
                .limit(5)
//                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + "개"))
                .toList();
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                Map.Entry::getValue,
//                (e1, e2) -> e1,
//                LinkedHashMap::new
//        ));

        System.out.println(top5List);
//        System.out.println(top5Map);
    }
}
