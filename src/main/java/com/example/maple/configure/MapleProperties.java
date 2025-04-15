package com.example.maple.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties(prefix = "nexon.maple")
public class MapleProperties {

    private String apikey;
    private String ranking;
    private String id;
    private String union;
}
