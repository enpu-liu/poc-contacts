package com.orangehouse.poc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orangehouse.poc.model.Contact;
import com.orangehouse.poc.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContactService contactService;

    final private List<Contact> testContacts = new ArrayList<>(Arrays.asList(
            new Contact("john", "smith",
                    "john.s@test.com", "09086677", "tester 1"),
            new Contact("robert", "wilson",
                    "robert.w@test.com", "0988765234", "tester 2"),
            new Contact("paul", "brown",
                    "paul.b@test.com", "512-335566", "tester 3"),
            new Contact("mary", "miller",
                    "mary.m@test.com", "532-678234", "tester 4"),
            new Contact("linda", "king",
                    "linda.k@test.com", "234-876123", "tester 5"),
            new Contact("betty", "lee",
                    "betty.l@test.com", "912-526876", "tester 6"),
            new Contact("jeff", "collins",
                    "jeff.c@test.com", "312-776623", "tester 7"),
            new Contact("belle", "walker",
                    "belle.w@test.com", "987-663321", "tester 8"),
            new Contact("kevin", "hill",
                    "kevin.h@test.com", "(512)987-123245", "tester 9"),
            new Contact("steven", "baker",
                    "steven.b@test.com", "(306)922-789034", "tester 10"),
            new Contact("jason", "young",
                    "jason.y@test.com", "2233445", "tester 11"),
            new Contact("nancy", "green",
                    "nancy.g@test.com", "(998)876-4167889", "tester 12")
    ));


    @Test
    public void should_get_all_contacts() throws Exception
    {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Contact> pageContacts =
                new PageImpl<>(testContacts, pageRequest, testContacts.size());

        given(contactService.findAll(any(PageRequest.class)))
                .willReturn(pageContacts);

        mockMvc.perform(get("/api/v1/contacts/?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_contact_by_id() throws Exception
    {
        given(contactService.findById(anyString()))
                .willReturn(Optional.of(testContacts.get(0)));

        mockMvc.perform(get("/api/v1/contacts/this_is_a_contact_id")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void should_get_contact_by_email() throws Exception
    {
        given(contactService.findByEmail(anyString()))
                .willReturn(Optional.of(testContacts.get(0)));

        mockMvc.perform(get("/api/v1/contacts/email/this_is_an_email")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_create_contact() throws Exception
    {
        given(contactService.save(any(Contact.class)))
                .willReturn(testContacts.get(0));

        mockMvc.perform(post("/api/v1/contacts/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testContacts.get(0))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_update_contact() throws Exception
    {
        given(contactService.findById(anyString()))
                .willReturn(Optional.of(testContacts.get(0)));
        given(contactService.save(any(Contact.class)))
                .willReturn(testContacts.get(0));

        mockMvc.perform(put("/api/v1/contacts/this_is_a_contact_id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testContacts.get(1))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_delete_contact() throws Exception
    {
        mockMvc.perform(delete("/api/v1/contacts/this_is_a_contact_id")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
