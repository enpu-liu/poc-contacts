package com.orangehouse.poc.service;

import com.orangehouse.poc.model.Contact;
import com.orangehouse.poc.repo.ContactRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ContactServiceTest
{
    @TestConfiguration
    static class ContactServiceTestContextConfiguration
    {
        @Bean
        public ContactService contactService()
        {
            return new ContactService();
        }
    }

    @Autowired
    private ContactService contactService;

    @MockBean
    private ContactRepo contactRepo;

    final private List<Contact> testContacts = new ArrayList<>(Arrays.asList(
            new Contact("john", "smith",
                    "john.s@test.com", "09086677", "tester 1"),
            new Contact("robert", "wilson",
                    "robert.w@test.com", "0988765234", "tester 2"),
            new Contact("paul", "brown",
                    "paul.b@test.com", "512-335566", "tester 3")
    ));

    @Mock
    Page<Contact> pageContacts;

    @Test
    public void should_find_all_contacts()
    {
        Pageable pageable = PageRequest.of(0, 10);
        when(contactRepo.findAll(any(PageRequest.class))).thenReturn(pageContacts);

        Page<Contact> page = contactService.findAll(pageable);
        assertThat(page).isEqualTo(pageContacts);
    }

    @Test
    public void should_find_contact_by_id()
    {
        when(contactRepo.findById(anyString()))
                .thenReturn(Optional.of(testContacts.get(0)));

        Optional<Contact> opt = contactService.findById(anyString());
        assertThat(opt.get()).isEqualTo(testContacts.get(0));
    }

    @Test
    public void should_find_contact_by_email()
    {
        when(contactRepo.findByEmail(anyString()))
                .thenReturn(Optional.of(testContacts.get(1)));

        Optional<Contact> opt = contactService.findByEmail(anyString());
        assertThat(opt.get()).isEqualTo(testContacts.get(1));
    }

    @Test
    public void should_save_contact()
    {
        when(contactRepo.save(any(Contact.class)))
                .thenReturn(testContacts.get(0));

        Contact contact = contactService.save(testContacts.get(0));
        assertThat(contact).isEqualTo(testContacts.get(0));
    }

    @Test
    public void should_delete_contact_by_id()
    {
        contactRepo.deleteById(anyString());

        verify(contactRepo, times(1)).deleteById(anyString());
    }

}
