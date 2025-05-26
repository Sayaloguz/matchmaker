package com.saraylg.matchmaker.matchmaker.model;

import com.saraylg.matchmaker.matchmaker.model.enums.GameMode;
import com.saraylg.matchmaker.matchmaker.model.enums.JamState;
import com.saraylg.matchmaker.matchmaker.model.enums.Languages;
import com.saraylg.matchmaker.matchmaker.model.enums.VoiceMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "jams")
public class JamEntity {

    @Id
    private String id;

    private String title;

    private String description; // Descripción del usuario

    private SteamAppEntity game;

    private String jamDate; // Día de la quedada

    private String jamTime; // Hora de la quedada

    private JamState state;

    private UsuarioEntity createdBy;

    private String createdAt;

    private Integer maxPlayers;

    private ArrayList<UsuarioEntity> players; // En esta lista se ha de incluir al creador

    private GameMode gameMode;

    private VoiceMode voiceMode;

    private Languages language;

    private String duration;

}
