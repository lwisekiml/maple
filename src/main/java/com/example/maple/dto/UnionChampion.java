package com.example.maple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UnionChampion {

    @JsonProperty("champion_name")
    private String championName;

    @JsonProperty("champion_slot")
    private int championSlot;

    @JsonProperty("champion_grade")
    private String championGrade;

    @JsonProperty("champion_class")
    private String championClass;

    @JsonProperty("champion_badge_info")
    private List<ChampionBadgeInfo> championBadgeInfo;
}
