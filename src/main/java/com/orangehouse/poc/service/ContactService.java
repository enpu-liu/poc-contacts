package com.orangehouse.poc.service;

import com.orangehouse.poc.model.Contact;
import com.orangehouse.poc.repo.ContactRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ContactService
{
    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContactRepo contactRepo;

    @Transactional(readOnly = true)
    public Page<Contact> findAll(Pageable pageable)
    {
        return contactRepo.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Contact> findById(String id)
    {
        return contactRepo.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Contact> findByEmail(String email)
    {
        return contactRepo.findByEmail(email);
    }

    @Transactional
    public Contact save(Contact contact)
    {
        return contactRepo.save(contact);
    }

    @Transactional
    public void deleteById(String id)
    {
        contactRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean contactEmailExists(String id, String email)
    {
        return contactRepo.findByEmail(email)
                .filter(contact -> !contact.getId().equals(id)).isPresent();
    }

}
