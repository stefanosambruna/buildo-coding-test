package com.codingtest.api;

import com.codingtest.api.model.Client;
import com.codingtest.api.repository.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import org.assertj.core.api.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ClientRepository clientRepository;


    @Test
    public void saveClient() {
        String clientServiceId = "123";
        String name = "Test client";

        Client client = new Client(clientServiceId, name);


        clientRepository.save(client);

        Optional<Client> result = clientRepository.findByClientServiceId(clientServiceId);

        Assertions.assertThat(result.isPresent())
                .isEqualTo(true);
    }

}
