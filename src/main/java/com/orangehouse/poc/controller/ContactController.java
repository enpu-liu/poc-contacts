package com.orangehouse.poc.controller;

import com.orangehouse.poc.model.Contact;
import com.orangehouse.poc.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = "Contact", value = "Operation APIs for contacts.")
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController
{
    final private Logger logger =
            LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @ApiOperation("list all contacts with pagination.")
    @ApiResponse(code = 417, message = "operation failed.")
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        try
        {
            Page<Contact> pages =
                    contactService.findAll(PageRequest.of(page, size));

            if (pages.getContent().isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            Map<String, Object> response = new HashMap<>();
            response.put("contacts", pages.getContent());
            response.put("current_page", pages.getNumber());
            response.put("total_items", pages.getTotalElements());
            response.put("total_pages", pages.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex)
        {
            logger.error(">>>>>>>> error=[" + ex.getMessage() + "] <<<<<<<<");
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @ApiOperation("get one contact by id.")
    @ApiResponse(code = 404, message = "contact not found.")
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(
            @PathVariable("id") String id)
    {
        return contactService.findById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation("get one contact by email.")
    @ApiResponse(code = 404, message = "contact not found.")
    @GetMapping("/email/{email}")
    public ResponseEntity<Contact> getContactByEmail(
            @PathVariable("email") String email)
    {
        return contactService.findByEmail(email)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation("create a new contact.")
    @ApiResponses({@ApiResponse(code = 400, message = "email exists."),
            @ApiResponse(code = 417, message = "operation failed.")})
    @PostMapping("/")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact)
    {
        if (contactService
                .contactEmailExists(contact.getId(), contact.getEmail()))
        {
            logger.error(">>>>>>>> email exists: " + contact.getEmail() +
                    " <<<<<<<<");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else
        {
            try
            {
                Contact data =
                        contactService.save(new Contact(contact.getFirstName(),
                                contact.getLastName(), contact.getEmail(),
                                contact.getPhoneNo(), contact.getRemark()));
                return new ResponseEntity<>(data, HttpStatus.OK);
            } catch (Exception ex)
            {
                logger.error(
                        ">>>>>>>> error=[" + ex.getMessage() + "] <<<<<<<<");
                return new ResponseEntity<>(null,
                        HttpStatus.EXPECTATION_FAILED);
            }
        }
    }

    @ApiOperation("modify an existing contact by id.")
    @ApiResponses({@ApiResponse(code = 404, message = "contact not found."),
            @ApiResponse(code = 400, message = "email exists.")})
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(
            @PathVariable("id") String id,
            @RequestBody Contact contact)
    {
        Optional<Contact> existing = contactService.findById(id);

        if (!existing.isPresent())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else if (contactService.contactEmailExists(id, contact.getEmail()))
        {
            logger.error(">>>>>>>> email exists: " + contact.getEmail() +
                    " <<<<<<<<");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else
        {
            Contact update = existing.get();
            update.setFirstName(contact.getFirstName());
            update.setLastName(contact.getLastName());
            update.setEmail(contact.getEmail());
            update.setPhoneNo(contact.getPhoneNo());
            // should use individual API to update avatar
            // update.setAvatar(contact.getAvatar());
            update.setRemark(contact.getRemark());
            contactService.save(update);
            return new ResponseEntity<>(update, HttpStatus.OK);
        }
    }

    @ApiOperation("delete an existing contact by id.")
    @ApiResponses({@ApiResponse(code = 204, message = "contact deleted."),
            @ApiResponse(code = 417, message = "operation failed.")})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteContact(
            @PathVariable("id") String id)
    {
        try
        {
            contactService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex)
        {
            logger.error(">>>>>>>> error=[" + ex.getMessage() + "] <<<<<<<<");
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
