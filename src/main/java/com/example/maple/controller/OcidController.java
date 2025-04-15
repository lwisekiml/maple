package com.example.maple.controller;

import com.example.maple.dto.OcidResponse;
import com.example.maple.service.OcidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/id")
@RequiredArgsConstructor
public class OcidController {

    private final OcidService ocidService;

    @ResponseBody
    @GetMapping
    public OcidResponse ocid(@RequestParam("character_name") String characterName) {
        return ocidService.getOcid(characterName);
    }
}
