package com.saraylg.matchmaker.matchmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * Representa la respuesta del endpoint /appdetails de Steam.
 * Steam devuelve un mapa appid -> AppDetails.
 * Nos quedamos solo con los campos relevantes: nombre, descripción, tipo, categorías, imagen y si es lanzamiento futuro.
 */
@Data
public class SteamAppDetailsResponse extends java.util.HashMap<String, SteamAppDetailsResponse.AppDetails> {

    @Data
    public static class AppDetails {
        private boolean success;
        private AppData data;
    }

    @Data
    public static class AppData {
        private String type;
        private String name;

        @JsonProperty("short_description")
        private String shortDescription;

        private List<Category> categories;

        @JsonProperty("header_image")
        private String headerImage;

        @JsonProperty("release_date")
        private ReleaseDate releaseDate;
    }

    @Data
    public static class ReleaseDate {
        @JsonProperty("coming_soon")
        private boolean comingSoon;
    }

    @Data
    public static class Category {
        private String description;
    }
}
