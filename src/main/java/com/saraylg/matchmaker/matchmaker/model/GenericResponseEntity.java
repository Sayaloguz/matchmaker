package com.saraylg.matchmaker.matchmaker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseEntity<T> {

    private String message;

    private String code;

    private T data;

}