package com.saraylg.matchmaker.matchmaker.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseDTO<T> {

    private String message;

    private String code;

    private T data;

}