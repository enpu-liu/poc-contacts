package com.orangehouse.poc.service;

import com.orangehouse.poc.model.Contact;
import com.orangehouse.poc.repo.ContactRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AvatarServiceTest
{
    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    @TestConfiguration
    static class AvatarServiceTestContextConfiguration
    {
        @Bean
        public AvatarService avatarService()
        {
            return new AvatarService();
        }
    }

    @Autowired
    private AvatarService avatarService;

    @MockBean
    private ContactRepo contactRepo;

    final private Contact testContact = new Contact("john", "smith",
            "john.s@test.com", "09086677", "tester 1");

    @Mock
    MultipartFile avatarFile;

    @Test
    public void should_save_avatar_by_id()
    {
        when(contactRepo.findById(anyString()))
                .thenReturn(Optional.of(testContact));

        Optional<Contact> contact =
                avatarService.saveById(anyString(), avatarFile);
        assertThat(contact).isEqualTo(Optional.of(testContact));
    }

    @Test
    public void should_save_avatar_with_contact() throws Exception
    {
        testContact.setAvatar(avatarFile.getBytes());
        when(contactRepo.save(testContact)).thenReturn(testContact);

        Contact rsContact =
                avatarService.saveWithContact(testContact, avatarFile);
        assertThat(rsContact).isEqualTo(testContact);
    }

}
