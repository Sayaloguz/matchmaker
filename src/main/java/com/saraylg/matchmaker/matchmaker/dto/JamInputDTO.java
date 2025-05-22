package com.saraylg.matchmaker.matchmaker.dto;

import com.saraylg.matchmaker.matchmaker.model.*;
import com.saraylg.matchmaker.matchmaker.model.enums.GameMode;
import com.saraylg.matchmaker.matchmaker.model.enums.JamState;
import com.saraylg.matchmaker.matchmaker.model.enums.Languages;
import com.saraylg.matchmaker.matchmaker.model.enums.VoiceMode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JamInputDTO {
    @NotBlank(message = "El título es obligatorio")
    private String title;
    private String description;
    private SteamAppEntity game;
    private String jamDate;
    private String jamTime;
    private JamState state;
    private UsuarioEntity createdBy;
    private String createdAt;
    private Integer maxPlayers;
    private ArrayList<UsuarioEntity> players;
    private GameMode gameMode;
    private VoiceMode voiceMode;
    private Languages language;
    private String duration;

}