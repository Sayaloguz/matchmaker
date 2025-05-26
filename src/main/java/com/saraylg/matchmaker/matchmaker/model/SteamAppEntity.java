package com.saraylg.matchmaker.matchmaker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

/**
 *  Utilizaremos SteamAppEntity para guardar los juegos y dlc de steam en nuestra base de datos.
 */
@Document(collection = "steam_games")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SteamAppEntity {

    @Id
    private Long appid;

    private String name;

    private String shortDescription;

    private List<String> categories;

    private String headerImage;

    private Instant lastUpdated;
}