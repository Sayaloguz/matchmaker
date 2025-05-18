package com.saraylg.matchmaker.matchmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 * SteamAppDetailsResponse extiende HashMap<String, AppDetails> porque el JSON es un map de appid -> AppDetails.
 * AppDetails tiene el flag success y un objeto data con la info del juego.
 * AppData tiene los campos que quieres: tipo, nombre, descripción corta, categorías e imagen.
 * Category es la lista de categorías con id y descripción.
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
    }

    @Data
    public static class Category {
        private int id;
        private String description;
    }
}

