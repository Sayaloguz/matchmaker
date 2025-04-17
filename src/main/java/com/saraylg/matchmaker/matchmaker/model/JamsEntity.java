package com.saraylg.matchmaker.matchmaker.model;

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
public class JamsEntity {
    @Id
    private String id;

    private String title;
    private String description;
    private String game;
    private String jamDateTime;
    private JamStatus status;
    private String createdBy;
    private String createdAt;
    private Integer maxPlayers;
    private ArrayList<UsuarioEntity> players;
    private GameMode gameMode;
    private VoiceMode voiceMode;
    private String language;
}
