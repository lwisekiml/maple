package com.example.maple.controller;

import com.example.maple.dto.UnionChampionResponse;
import com.example.maple.service.UnionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UnionController {

    private final UnionService unionService;

    @ResponseBody
    @GetMapping("/union")
    public UnionChampionResponse getUnionChampion(@RequestParam String ocid) {
        return unionService.getUnionChampion(ocid);
    }
}
