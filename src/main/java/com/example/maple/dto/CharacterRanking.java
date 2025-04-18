package com.example.maple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterRanking {

    private String date;

    @JsonProperty("world_name")
    private String worldName;

    private int ranking;

    @JsonProperty("character_name")
    private String characterName;

    @JsonProperty("character_level")
    private int characterLevel;

    @JsonProperty("character_exp")
    private long characterExp;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("sub_class_name")
    private String subClassName;

    @JsonProperty("character_popularity")
    private int characterPopularity;

    @JsonProperty("character_guildname")
    private String characterGuildname;
}
