package com.codingtest.api;


import com.codingtest.api.controller.ClientController;
import com.codingtest.api.exception.ResourceNotFoundException;
import com.codingtest.api.model.ClientDto;
import com.codingtest.api.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @Test
    public void canRetrieveByIdWhenExists()
            throws Exception {
        String clientServiceId = "123";
        String clientName = "Test client";

        BDDMockito.given(clientService.getDtoByClientServiceId(clientServiceId))
                .willReturn(new ClientDto(clientServiceId, clientName));

        mvc.perform(
                MockMvcRequestBuilders.get("/api/clients/{clientServiceId}", clientServiceId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientServiceId", org.hamcrest.Matchers.is(clientServiceId)));
    }

    @Test
    public void return404WhenClientServiceIdDoesNotExist()
            throws Exception {
        String clientServiceId = "123";

        BDDMockito.given(clientService.getDtoByClientServiceId(clientServiceId))
                .willThrow(new ResourceNotFoundException());

        mvc.perform(
                MockMvcRequestBuilders.get("/api/clients/{clientServiceId}", clientServiceId))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }


}
