package com.example.maple.controller;

import com.example.maple.dto.RankingResponse;
import com.example.maple.service.OverallRankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class OverallRankingController {

    private final OverallRankingService overallRankingService;

    @ResponseBody
    @GetMapping("/overall")
    public RankingResponse getRankingOverall(@RequestParam String date) {
        return overallRankingService.getRanking(date);
    }
}
