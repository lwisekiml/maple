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
    public void rankerUnionChampionPopulation(@RequestParam String date) throws InterruptedException {
        RankingOverallResponse rankingOverall = rankingService.getRankingOverall(date);
        List<String> characterNames = rankingOverall.getRanking().stream()
                .map(CharacterRanking::getCharacterName)
                .toList();

        List<OcidResponse> ocidList = new ArrayList<>();

        RateLimiter rateLimiter = RateLimiter.create(3); // 초당 3회 제한
//        for (String name : characterNames) {
        for (int i=0; i < 10; i++) {
            String name = characterNames.get(i);

            rateLimiter.acquire();
            System.out.println("name : " + name);

            OcidResponse ocid = ocidService.getOcid(name);
            ocidList.add(ocid);

        }

        for (OcidResponse ocid : ocidList) {
            System.out.println("ocid: " + ocid.getOcid());
        }

        List<String> championClassList = new ArrayList<>();
        List<UnionChampionResponse> UCRList = new ArrayList<>();

        for(OcidResponse ocid : ocidList) {
//            rateLimiter.acquire(); // 적용 안됨...
            Thread.sleep(300);
            System.out.println("union : " + ocid.getOcid());
            UnionChampionResponse unionChampion = unionService.getUnionChampion(ocid.getOcid());
            UCRList.add(unionChampion);
        }

        for (UnionChampionResponse ucr : UCRList) {
            for (UnionChampion uc : ucr.getUnionChampion()) {
                championClassList.add(uc.getChampionClass());
            }
        }

        Map<String, Integer> jobCountMap = new HashMap<>();

        for (String job : championClassList) {
            jobCountMap.put(job, jobCountMap.getOrDefault(job, 0) + 1);
        }

        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(jobCountMap.entrySet());
        sortedList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue())); // 내림 차순

        for (Map.Entry<String, Integer> entry : sortedList) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + "개");
        }
    }
}
