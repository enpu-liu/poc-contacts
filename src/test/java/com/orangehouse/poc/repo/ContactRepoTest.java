package com.orangehouse.poc.repo;

import com.orangehouse.poc.model.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ContactRepoTest
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactRepo contactRepo;

    final private List<Contact> testContacts = new ArrayList<>(Arrays.asList(
            new Contact("john", "smith",
                    "john.s@test.com", "09086677", "tester 1"),
            new Contact("robert", "wilson",
                    "robert.w@test.com", "0988765234", "tester 2"),
            new Contact("paul", "brown",
                    "paul.b@test.com", "512-335566", "tester 3")
    ));


    @Test
    public void should_be_empty_in_fina_all()
    {
        List<Contact> contacts = contactRepo.findAll();
        assertThat(contacts).isEmpty();
    }

    @Test
    public void should_store_one_contact()
    {
        Contact stored = contactRepo.save(testContacts.get(0));

        assertThat(stored).hasFieldOrPropertyWithValue("firstName", "john");
        assertThat(stored).hasFieldOrPropertyWithValue("lastName", "smith");
        assertThat(stored)
                .hasFieldOrPropertyWithValue("email", "john.s@test.com");
        assertThat(stored).hasFieldOrPropertyWithValue("phoneNo", "09086677");
        assertThat(stored).hasFieldOrPropertyWithValue("remark", "tester 1");
    }

    @Test
    public void should_find_all_contacts()
    {
        entityManager.persistAndFlush(testContacts.get(0));
        entityManager.persistAndFlush(testContacts.get(1));
        entityManager.persistAndFlush(testContacts.get(2));

        List<Contact> contacts = contactRepo.findAll();
        assertThat(contacts).hasSize(3)
                .contains(testContacts.get(0), testContacts.get(1),
                        testContacts.get(2));
    }

    @Test
    public void should_find_contact_by_id()
    {
        entityManager.persistAndFlush(testContacts.get(0));
        entityManager.persistAndFlush(testContacts.get(1));

        assertThat(contactRepo.findById(testContacts.get(0).getId()).get())
                .isEqualTo(testContacts.get(0));
    }

    @Test
    public void should_find_contact_by_email()
    {
        entityManager.persistAndFlush(testContacts.get(0));
        entityManager.persistAndFlush(testContacts.get(1));

        assertThat(
                contactRepo.findByEmail(testContacts.get(1).getEmail()).get())
                .isEqualTo(testContacts.get(1));
    }

    @Test
    public void should_update_contact_by_id()
    {
        entityManager.persistAndFlush(testContacts.get(0));
        entityManager.persistAndFlush(testContacts.get(1));

        Contact update = new Contact("robert.update", "wilson.update",
                "r.wilson@test.com", "0988765234.update", "tester 2.update");

        Contact contact =
                contactRepo.findById(testContacts.get(1).getId()).get();

        contact.setFirstName(update.getFirstName());
        contact.setLastName(update.getLastName());
        contact.setEmail(update.getEmail());
        contact.setPhoneNo(update.getPhoneNo());
        contact.setRemark(update.getRemark());

        contactRepo.save(contact);

        Contact check = contactRepo.findById(testContacts.get(1).getId()).get();
        assertThat(check.getId()).isEqualTo(testContacts.get(1).getId());
        assertThat(check.getFirstName()).isEqualTo(update.getFirstName());
        assertThat(check.getLastName()).isEqualTo(update.getLastName());
        assertThat(check.getEmail()).isEqualTo(update.getEmail());
        assertThat(check.getPhoneNo()).isEqualTo(update.getPhoneNo());
        assertThat(check.getRemark()).isEqualTo(update.getRemark());
    }

    @Test
    public void should_delete_contact_by_id()
    {
        entityManager.persistAndFlush(testContacts.get(0));
        entityManager.persistAndFlush(testContacts.get(1));
        entityManager.persistAndFlush(testContacts.get(2));

        contactRepo.deleteById(testContacts.get(1).getId());
        List<Contact> contacts = contactRepo.findAll();
        assertThat(contacts).hasSize(2)
                .contains(testContacts.get(0), testContacts.get(2));

    }

}
