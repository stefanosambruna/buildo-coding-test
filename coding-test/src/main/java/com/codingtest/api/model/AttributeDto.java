package com.codingtest.api.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttributeDto {
    private String name;
    private String value;

    public AttributeDto(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
