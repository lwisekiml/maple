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
