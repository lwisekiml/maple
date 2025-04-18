package com.example.maple.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RankingOverallResponse {
    private List<CharacterRanking> ranking;
}

