package com.saraylg.matchmaker.matchmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

/** DTO para https://api.steampowered.com/ISteamApps/GetAppList/v2 */
@Data
public class SteamAppListResponse {

    @JsonProperty("applist")          // <- nombre real en el JSON
    private AppList applist;

    @Data
    public static class AppList {
        private List<App> apps;       // <- lista de juegos
    }

    @Data
    public static class App {
        private Long   appid;
        private String name;
    }
}
