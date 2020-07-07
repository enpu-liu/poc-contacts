package com.orangehouse.poc.service;

import com.orangehouse.poc.exception.ImageException;
import com.orangehouse.poc.model.Contact;
import com.orangehouse.poc.repo.ContactRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class AvatarService
{
    final private Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Autowired
    private ContactRepo contactRepo;

    @Transactional
    public Optional<Contact> saveById(String id, MultipartFile avatarFile)
    {
        Optional<Contact> opt = contactRepo.findById(id);

        if (!opt.isPresent())
            return Optional.empty();
        else
        {
            Contact contact = opt.get();
            return Optional.of(saveWithContact(contact, avatarFile));
        }
    }

    @Transactional
    public Contact saveWithContact(Contact contact, MultipartFile avatarFile)
    {
        try
        {
            contact.setAvatar(avatarFile.getBytes());
            contactRepo.save(contact);
            return contact;
        } catch (Exception ex)
        {
            logger.error(">>>>>>>> error=[" + ex.getMessage() + "] <<<<<<<<");
            throw new ImageException("save avatar failed.", ex);
        }
    }

    @Transactional
    public void deleteById(String id)
    {
        Optional<Contact> opt = contactRepo.findById(id);

        if (opt.isPresent())
        {
            Contact contact = opt.get();
            contact.setAvatar(null);
            contactRepo.save(contact);
        }
    }

}
