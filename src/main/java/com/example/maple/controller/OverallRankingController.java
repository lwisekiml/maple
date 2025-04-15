package com.example.maple.controller;

import com.example.maple.service.OverallRankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class OverallRankingController {

    private final OverallRankingService overallRankingService;

    @GetMapping("/overall")
    public void getRankingOverall(@RequestParam String date) {
        overallRankingService.getRanking(date);

    }
}
