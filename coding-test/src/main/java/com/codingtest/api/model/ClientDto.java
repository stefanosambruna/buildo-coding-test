package com.codingtest.api.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientDto {
    private String clientServiceId;
    private String name;

    public ClientDto(String clientServiceId, String name) {
        this.clientServiceId = clientServiceId;
        this.name = name;
    }
}
