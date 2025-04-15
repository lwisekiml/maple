package com.example.maple.controller;

import com.example.maple.dto.RankingOverallResponse;
import com.example.maple.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService overallRankingService;

    @ResponseBody
    @GetMapping("/overall")
    public RankingOverallResponse getRankingOverall(@RequestParam String date) {
        return overallRankingService.getRankingOverall(date);
    }
}
