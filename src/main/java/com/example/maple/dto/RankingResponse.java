package com.example.maple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RankingResponse {
    private List<CharacterRanking> ranking;
}

@Getter @Setter
class CharacterRanking {

    private String date;
    private int ranking;

    @JsonProperty("character_name")
    private String characterName;

    @JsonProperty("world_name")
    private String worldName;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("sub_class_name")
    private String subClassName;

    @JsonProperty("character_level")
    private int characterLevel;

    @JsonProperty("character_exp")
    private long characterExp;

    @JsonProperty("character_popularity")
    private int characterPopularity;

    @JsonProperty("character_guildname")
    private String characterGuildname;
}
