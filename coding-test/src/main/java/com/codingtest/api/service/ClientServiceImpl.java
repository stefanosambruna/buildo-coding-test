package com.codingtest.api.service;

import com.codingtest.api.exception.DuplicateResourceException;
import com.codingtest.api.exception.ResourceNotFoundException;
import com.codingtest.api.model.Client;
import com.codingtest.api.model.ClientDto;
import com.codingtest.api.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void registerNewClient(ClientDto newClient) {
        if(clientRepository.findByClientServiceId(newClient.getClientServiceId()).isPresent())
            throw new DuplicateResourceException("Another attribute with the same name was found for the specified client!");
        clientRepository.save(
                new Client(
                        newClient.getClientServiceId(),
                        newClient.getName()
                )
        );
    }

    @Override
    public ClientDto getDtoByClientServiceId(String clientServiceId) {
        return convertToDto(getByClientServiceId(clientServiceId));

    }

    @Override
    public List<ClientDto> getAllDtoByClientServiceId() {
        return clientRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Client getByClientServiceId(String clientServiceId) {
        return clientRepository
                .findByClientServiceId(clientServiceId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private ClientDto convertToDto(Client client) {
        return modelMapper.map(client, ClientDto.class);
    }
    
}
