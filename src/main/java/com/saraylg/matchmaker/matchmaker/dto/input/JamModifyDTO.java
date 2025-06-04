package com.saraylg.matchmaker.matchmaker.dto.input;

import com.saraylg.matchmaker.matchmaker.model.enums.GameMode;
import com.saraylg.matchmaker.matchmaker.model.enums.Languages;
import com.saraylg.matchmaker.matchmaker.model.enums.VoiceMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JamModifyDTO {

    private String id;

    private String title;

    private String description;

    private String jamDate;

    private String jamTime;

    private Integer maxPlayers;

    private GameMode gameMode;

    private VoiceMode voiceMode;

    private Languages language;

    private String duration;

}
