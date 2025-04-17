package com.saraylg.matchmaker.matchmaker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInputDTO {

    @NotBlank (message = "La ID de Steam no puede estar vacía")
    private String steamId;

    @NotBlank (message = "No se encontró información del jugador...")
    private String name;

    @NotBlank (message = "La URL del perfil no puede estar vacía")
    private String profileUrl;

    @NotBlank (message = "La URL del avatar no puede estar vacía")
    private String avatar;

    @NotBlank (message = "El tiempo de creación no puede estar vacío")
    private String timeCreated;

}
