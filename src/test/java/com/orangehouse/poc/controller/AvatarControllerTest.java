package com.orangehouse.poc.controller;

import com.orangehouse.poc.model.Contact;
import com.orangehouse.poc.service.AvatarService;
import com.orangehouse.poc.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AvatarController.class)
public class AvatarControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvatarService avatarService;

    @MockBean
    private ContactService contactService;

    final private Contact testContact = new Contact("john", "smith",
            "john.s@test.com", "09086677", "tester 1");

    @Test
    public void should_save_avatar() throws Exception
    {
        given(avatarService.saveById(anyString(), any(MultipartFile.class)))
                .willReturn(Optional.of(testContact));

        MockMultipartFile mockFile =
                new MockMultipartFile("avatar", "avatar.png",
                        "multipart/form-data", "avatar_image".getBytes());
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/v1/avatar/this_is_a_contact_id").file(mockFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_render_avatar() throws Exception
    {
        given(contactService.findById(anyString()))
                .willReturn(Optional.of(testContact));

        mockMvc.perform(get("/api/v1/avatar/this_is_a_contact_id")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_delete_avatar() throws Exception
    {
        mockMvc.perform(delete("/api/v1/avatar/this_is_a_contact_id")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
