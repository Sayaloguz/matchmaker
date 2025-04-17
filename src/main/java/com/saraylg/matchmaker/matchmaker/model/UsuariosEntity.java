package com.saraylg.matchmaker.matchmaker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class UsuariosEntity {

    @Id
    private String steamId;

    private String name;
    private String profileUrl;
    private String avatar;
    private String timeCreated;
}
