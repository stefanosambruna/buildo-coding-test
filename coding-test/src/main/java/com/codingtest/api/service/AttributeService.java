package com.codingtest.api.service;

import com.codingtest.api.model.Attribute;
import com.codingtest.api.model.AttributeDto;

import java.util.Map;

public interface AttributeService {
    Attribute addNewAttrByClientServiceId(String clientServiceId, AttributeDto newAttribute);
    AttributeDto getDtoByClientServiceIdAndAttrName(String clientServiceId, String attrName);
    Attribute getByClientServiceIdAndAttrName(String clientServiceId, String attrName);
    Map<String, String> getAllByClientServiceId(String clientServiceId);
    Attribute updateAttrValueByClientServiceIdAndAttrName(String clientServiceId, String attrName, String newValue);
    void deleteByClientServiceIdAndAttrName(String clientServiceId, String attrName);
}
