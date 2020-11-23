package com.codingtest.api.controller;

import com.codingtest.api.model.AttributeDto;
import com.codingtest.api.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/clients/{clientServiceId}/attributes")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @PostMapping
    public String addNewAttribute(
            @PathVariable String clientServiceId,
            @RequestBody AttributeDto attributeDto
    ){
        attributeService.addNewAttrByClientServiceId(clientServiceId, attributeDto);
        return "attribute added successfully";
    }

    @GetMapping
    public Map<String, String> getAllAttr(
            @PathVariable String clientServiceId
    ){
        return attributeService.getAllByClientServiceId(clientServiceId);
    }

    @GetMapping("/{attrName}")
    public AttributeDto getAttr(
            @PathVariable String clientServiceId,
            @PathVariable String attrName
    ){
        return attributeService.getDtoByClientServiceIdAndAttrName(clientServiceId, attrName);
    }

    @DeleteMapping("/{attrName}")
    public String deleteAttr(
            @PathVariable String clientServiceId,
            @PathVariable String attrName
    ){
        attributeService.deleteByClientServiceIdAndAttrName(clientServiceId, attrName);
        return "attribute deleted successfully";
    }

    @PatchMapping("/{attrName}")
    public String updateAttrValue(
            @PathVariable String clientServiceId,
            @PathVariable String attrName,
            @RequestBody AttributeDto attributeDto
    ){
        attributeService.updateAttrValueByClientServiceIdAndAttrName(clientServiceId, attrName, attributeDto.getValue());
        return "attribute updated successfully";
    }
}
