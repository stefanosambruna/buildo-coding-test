package com.codingtest.api;

import com.codingtest.api.model.Attribute;
import com.codingtest.api.model.AttributeDto;
import com.codingtest.api.model.Client;
import com.codingtest.api.repository.AttributeRepository;
import com.codingtest.api.repository.ClientRepository;
import com.codingtest.api.service.AttributeService;
import com.codingtest.api.service.AttributeServiceImpl;
import com.codingtest.api.service.ClientService;
import com.codingtest.api.service.ClientServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class AttributeServiceTest {

    @TestConfiguration
    static class AttributeRepositoryTestContextConfiguration {

        @Bean
        public AttributeService attributeService() {
            return new AttributeServiceImpl();
        }

        @Bean
        public ClientService clientService() {
            return new ClientServiceImpl();
        }

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }

    @Autowired
    AttributeService attributeService;

    @MockBean
    AttributeRepository attributeRepository;

    @MockBean
    ClientRepository clientRepository;

    @Test
    public void saveAttributeWhenClientExists() {
        String attrName = "attr1";
        String attrValue = "value 1";

        String clientServiceId = "123";
        String clientName = "Test client";

        AttributeDto attrDto = new AttributeDto(attrName, attrValue);

        Client client = new Client(clientServiceId, clientName);

        BDDMockito.given(clientRepository.findByClientServiceId(clientServiceId)).willReturn(Optional.of(client));
        BDDMockito.given(attributeRepository.save(Mockito.any(Attribute.class))).will(i -> i.getArguments()[0]);

        Attribute attribute = attributeService.addNewAttrByClientServiceId(clientServiceId, attrDto);

        Assertions.assertThat(attribute.getValue()).isEqualTo(attrValue);

        BDDMockito.then(attributeRepository).should().save(BDDMockito.any(Attribute.class));
    }


    @Test
    public void editAttributeWhenExists() {
        String attrName = "attr1";
        String attrValue = "value 1";
        String newAttrValue = "new value";

        String clientServiceId = "123";
        String clientName = "Test client";

        Client client = new Client(clientServiceId, clientName);
        Attribute attribute = new Attribute(attrName, attrValue, client);

        BDDMockito.given(clientRepository.findByClientServiceId(clientServiceId)).willReturn(Optional.of(client));
        BDDMockito.given(attributeRepository.findByClientClientServiceIdAndName(clientServiceId, attrName)).willReturn(Optional.of(attribute));
        BDDMockito.given(attributeRepository.save(Mockito.any(Attribute.class))).will(i -> i.getArguments()[0]);

        Attribute newAttribute = attributeService.updateAttrValueByClientServiceIdAndAttrName(clientServiceId, attrName, newAttrValue);

        Assertions.assertThat(newAttribute.getValue()).isEqualTo(newAttrValue);

        BDDMockito.then(attributeRepository).should().save(BDDMockito.any(Attribute.class));
    }



    @Test
    public void deleteAttributeWhenExists2() {
        String clientName = "Test client";
        String clientServiceId = "123";
        Client client = new Client(clientServiceId, clientName);


        Attribute attr = new Attribute("attr1", "value 1", client);
        client.addAttribute(attr);

        BDDMockito.given(attributeRepository.findByClientClientServiceIdAndName("123", "attr1")).willReturn(Optional.of(attr));

        attributeRepository.deleteByClientClientServiceIdAndName("123", "attr1");

        BDDMockito.then(attributeRepository).should(BDDMockito.times(1)).deleteByClientClientServiceIdAndName("123", "attr1");
    }



}
