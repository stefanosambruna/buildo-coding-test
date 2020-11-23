package com.codingtest.api.controller;

import com.codingtest.api.model.ClientDto;
import com.codingtest.api.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public String registerNewClient(@RequestBody ClientDto newClientDto){
        clientService.registerNewClient(newClientDto);
        return "client registered successfully";
    }

    @GetMapping("/{clientServiceId}")
    public ClientDto getClient(@PathVariable String clientServiceId){
        return clientService.getDtoByClientServiceId(clientServiceId);
    }

    @GetMapping
    public List<ClientDto> getAllClient(){
        return clientService.getAllDtoByClientServiceId();
    }
}
