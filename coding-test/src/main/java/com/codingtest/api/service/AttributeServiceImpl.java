package com.codingtest.api.service;

import com.codingtest.api.exception.DuplicateResourceException;
import com.codingtest.api.exception.ResourceNotFoundException;
import com.codingtest.api.model.Attribute;
import com.codingtest.api.model.AttributeDto;
import com.codingtest.api.model.Client;
import com.codingtest.api.repository.AttributeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Attribute addNewAttrByClientServiceId(String clientServiceId, AttributeDto newAttribute) {
        if(attributeRepository.findByClientClientServiceIdAndName(clientServiceId, newAttribute.getName()).isPresent())
            throw new DuplicateResourceException("Another attribute with the same name was found for the specified client!");
        Attribute attribute = attributeRepository.save(
                new Attribute(
                        newAttribute.getName(),
                        newAttribute.getValue(),
                        clientService.getByClientServiceId(clientServiceId)
                )
        );
        return attribute;
    }

    @Override
    public AttributeDto getDtoByClientServiceIdAndAttrName(String clientServiceId, String attrName) {
        return convertToDto(getByClientServiceIdAndAttrName(clientServiceId, attrName));
    }

    @Override
    public Attribute getByClientServiceIdAndAttrName(String clientServiceId, String attrName) {
        return attributeRepository
                .findByClientClientServiceIdAndName(clientServiceId, attrName)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Map<String, String> getAllByClientServiceId(String clientServiceId) {
        Client client = clientService.getByClientServiceId(clientServiceId);
        return client
                .getAttributes()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().getValue()));
    }


    @Override
    public Attribute updateAttrValueByClientServiceIdAndAttrName(String clientServiceId, String attrName, String newValue) {
        Attribute attr = getByClientServiceIdAndAttrName(clientServiceId, attrName);
        attr.setValue(newValue);
        return attributeRepository.save(attr);
    }

    @Override
    public void deleteByClientServiceIdAndAttrName(String clientServiceId, String attrName) {
        if(attributeRepository.deleteByClientClientServiceIdAndName(clientServiceId, attrName) < 1)
            throw new ResourceNotFoundException();
    }


    private AttributeDto convertToDto(Attribute attribute) {
        return modelMapper.map(attribute, AttributeDto.class);
    }
}
