package com.codingtest.api;

import com.codingtest.api.controller.AttributeController;
import com.codingtest.api.exception.ResourceNotFoundException;
import com.codingtest.api.model.Attribute;
import com.codingtest.api.model.AttributeDto;
import com.codingtest.api.service.AttributeService;
import com.codingtest.api.service.ClientService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@WebMvcTest(AttributeController.class)
public class AttributeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AttributeService attributeService;

    @MockBean
    private ClientService clientService;

    private Gson gson = new Gson();

    @Test
    public void canRetrieveByIdWhenExists()
            throws Exception {
        String clientServiceId = "123";
        String attrName = "attr1";
        String attrValue = "value 1";

        BDDMockito.given(attributeService.getDtoByClientServiceIdAndAttrName(clientServiceId, attrName))
                .willReturn(new AttributeDto(attrName, attrValue));

        mvc.perform(
                MockMvcRequestBuilders.get("/api/clients/" + clientServiceId + "/attributes/" + attrName))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", org.hamcrest.Matchers.is(attrName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", org.hamcrest.Matchers.is(attrValue)));
    }

    @Test
    public void return404WhenAttrNameDoesNotExist()
            throws Exception {
        String clientServiceId = "123";
        String attrName = "attr1";

        BDDMockito.given(attributeService.getDtoByClientServiceIdAndAttrName(clientServiceId, attrName))
                .willThrow(new ResourceNotFoundException());

        mvc.perform(
                MockMvcRequestBuilders.get("/api/clients/" + clientServiceId + "/attributes/" + attrName))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    public void canRetrieveAllByClientServiceIdWhenClientExists()
            throws Exception {
        String clientServiceId = "123";

        Map<String, String> attributes  = new HashMap<String, String>() {{
            put("attr1", "value 1");
            put("attr2", "value 2");
            put("attr3", "value 3");
        }};

        BDDMockito.given(attributeService.getAllByClientServiceId(clientServiceId))
                .willReturn(attributes);

        mvc.perform(
                MockMvcRequestBuilders.get("/api/clients/" + clientServiceId + "/attributes/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$['attr1']", Matchers.is("value 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['attr2']", Matchers.is("value 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['attr3']", Matchers.is("value 3")));
    }

    @Test
    public void addAttributeWhenClientExists() throws Exception {
        String name = "attr1";
        String value = "value 1";
        AttributeDto attrDto = new AttributeDto(name, value);

        BDDMockito.given(attributeService.addNewAttrByClientServiceId("123", attrDto))
                .willReturn(new Attribute("attr1", "value 1", null));


        mvc.perform(MockMvcRequestBuilders.post("/api/clients/123/attributes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(attrDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void editAttributeWhenExists() throws Exception {
        AttributeDto attrDto = new AttributeDto("", "new value");

        BDDMockito.given(attributeService.updateAttrValueByClientServiceIdAndAttrName("123", "attr1", "new value"))
                .willReturn(new Attribute("attr1", "new value", null));

        mvc.perform(MockMvcRequestBuilders.patch("/api/clients/123/attributes/attr1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(attrDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void return404WhenEditAttributeWhenDoesNotExists() throws Exception {
        AttributeDto attrDto = new AttributeDto("", "new value");

        BDDMockito.given(attributeService.updateAttrValueByClientServiceIdAndAttrName("123", "attr1", "new value"))
                .willThrow(new ResourceNotFoundException());

        mvc.perform(MockMvcRequestBuilders.patch("/api/clients/123/attributes/attr1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(attrDto)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

}
