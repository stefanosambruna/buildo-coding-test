package com.codingtest.api;


import com.codingtest.api.model.Attribute;
import com.codingtest.api.model.Client;
import com.codingtest.api.repository.AttributeRepository;
import com.codingtest.api.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AttributeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void editAttributeWhenExists() {
        String attrName = "attr1";
        String attrValue = "value 1";

        String clientName = "Test client";
        String clientServiceId = "123";

        Client client = new Client(clientServiceId, clientName);

        clientRepository.save(client);

        Attribute attr = new Attribute(attrName, attrValue, client);

        attributeRepository.save(attr);

        Assertions.assertThat(attributeRepository.findByClientClientServiceIdAndName(clientServiceId, attrName).get())
                .isEqualTo(attr);

        attr.setValue("value 2");

        attributeRepository.save(attr);

        Assertions.assertThat(attributeRepository.findByClientClientServiceIdAndName(clientServiceId, attrName).get().getValue())
                .isEqualTo("value 2");
    }

    @Test
    public void deleteAttributeWhenExists() {

        String clientName = "Test client";
        String clientServiceId = "123";

        Client client = new Client(clientServiceId, clientName);

        clientRepository.save(client);

        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("attr1", "value 1", client));
        attributes.add(new Attribute("attr2", "value 2", client));
        attributes.add(new Attribute("attr3", "value 3", client));

        attributeRepository.saveAll(attributes);

        Assertions.assertThat(attributeRepository.findByClientClientServiceIdAndName(clientServiceId, "attr1").get())
                .isEqualTo(attributes.get(0));

        Assertions.assertThat(attributeRepository.findAllByClientClientServiceId(clientServiceId).size())
                .isEqualTo(3);

        attributeRepository.deleteByClientClientServiceIdAndName(clientServiceId, "attr2");

        Assertions.assertThat(!attributeRepository.findByClientClientServiceIdAndName(clientServiceId, "attr2").isPresent());

        Assertions.assertThat(attributeRepository.findAllByClientClientServiceId(clientServiceId).size())
                .isEqualTo(2);
    }
}
