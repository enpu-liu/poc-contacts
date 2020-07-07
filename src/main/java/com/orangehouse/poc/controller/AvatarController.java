package com.orangehouse.poc.controller;

import com.orangehouse.poc.model.Contact;
import com.orangehouse.poc.service.AvatarService;
import com.orangehouse.poc.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Api(tags = "Avatar")
@RestController
@RequestMapping("/api/v1/avatar")
public class AvatarController
{
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private ContactService contactService;

    @ApiOperation("save avatar by id.")
    @ApiResponse(code = 417, message = "operation failed.")
    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> saveAvatar(
            @PathVariable("id") String id,
            @RequestParam("avatar") MultipartFile avatarFile)
    {
        try
        {
            if (avatarService.saveById(id, avatarFile).isPresent())
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        } catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @ApiOperation("render avatar by id.")
    @GetMapping("/{id}")
    public void renderAvatar(@PathVariable("id") String id,
                             HttpServletResponse response) throws IOException
    {
        Optional<Contact> opt = contactService.findById(id);

        if (opt.isPresent())
        {
            byte[] avatar = opt.get().getAvatar();
            if (avatar != null)
            {
                response.setContentType("image/jpeg");
                InputStream is = new ByteArrayInputStream(avatar);
                IOUtils.copy(is, response.getOutputStream());
            }
        }
    }

    @ApiOperation("delete avatar by id.")
    @ApiResponse(code = 417, message = "operation failed.")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAvatar(
            @PathVariable("id") String id)
    {
        try
        {
            avatarService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
