package com.example.maple.domain;

import com.example.maple.dto.CharacterRanking;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RankingResponse {
    private List<CharacterRanking> ranking;
}
