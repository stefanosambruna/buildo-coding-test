package com.codingtest.api.service;

import com.codingtest.api.model.Client;
import com.codingtest.api.model.ClientDto;

import java.util.List;

public interface ClientService {
    void registerNewClient(ClientDto newClient);
    Client getByClientServiceId(String clientServiceId);
    ClientDto getDtoByClientServiceId(String clientServiceId);
    List<ClientDto> getAllDtoByClientServiceId();
}
