package com.example.maple.controller;

import com.example.maple.dto.*;
import com.example.maple.service.OcidService;
import com.example.maple.service.RankerUnionChampionPopulationService;
import com.example.maple.service.RankingService;
import com.example.maple.service.UnionService;
import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/RankerUnionChampionPopulation")
@RequiredArgsConstructor
public class RankerUnionChampionPopulationController {

    private final RankerUnionChampionPopulationService rankerUnionChampionPopulationService;

    @ResponseBody
    @GetMapping
    public void rankerUnionChampionPopulation(@RequestParam String date) throws InterruptedException {
        rankerUnionChampionPopulationService.rankerUnionChampionPopulation(date);
    }
}
