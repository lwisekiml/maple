package com.example.maple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class UnionChampionResponse {
    private OffsetDateTime date;

    @JsonProperty("union_champion")
    private List<UnionChampion> unionChampion;

    @JsonProperty("champion_badge_total_info")
    private List<ChampionBadgeInfo> championBadgeTotalInfo;
}

@Getter
@Setter
class UnionChampion {

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

@Getter
@Setter
class ChampionBadgeInfo {
    private String stat;
}