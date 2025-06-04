package com.saraylg.matchmaker.matchmaker.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SteamAppOutputDTO {
    private Long appid;

    private String name;

    private String shortDescription;

    private List<String> categories;

    private String headerImage;

    private Instant lastUpdated;
}
