package com.bookshelf.spring.controller;

import com.bookshelf.spring.common.UserDTOtoUser;
import com.bookshelf.spring.common.UserToUserDTO;
import com.bookshelf.spring.dto.UserDTO;
import com.bookshelf.spring.entity.User;
import com.bookshelf.spring.service.AuthorityServiceInterface;
import com.bookshelf.spring.service.Impl.NotificationServiceImpl;
import com.bookshelf.spring.service.UserServiceIterface;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping(value = "api/register")
@CrossOrigin("*")
public class RegisterController {

    @Autowired
    UserServiceIterface userServiceIterface;

    @Autowired
    AuthorityServiceInterface authorityServiceInterface;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDTOtoUser toUser;

    @Autowired
    private UserToUserDTO toDTO;

    @Autowired
    private NotificationServiceImpl notificationService;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO){
        UserDTO userDTO1= userDTO;
        userDTO1.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = toUser.convert(userDTO1);
        user=userServiceIterface.save(user);
        return new ResponseEntity<UserDTO>(toDTO.convert(user), HttpStatus.CREATED);
    }
    @RequestMapping(value = "/file",method = RequestMethod.POST)
    public ResponseEntity<UserDTO>saveUserProfile(@RequestParam("user")String user,@RequestParam("file") MultipartFile file) throws JsonParseException, JsonMappingException, IOException {

        UserDTO person = new ObjectMapper().readValue(user,UserDTO.class);

        person.setPhoto(file.getBytes());
        person.setRoleName("ROLE_USER");
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setCreateDate(new Date());
        User dbPerson = userServiceIterface.save(toUser.convert(person));
        notificationService.sendNotification(dbPerson);
        if(dbPerson!=null){
            return new ResponseEntity<>(toDTO.convert(dbPerson),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
