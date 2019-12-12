package com.bookshelf.spring.controller;


import com.bookshelf.spring.common.UserToUserDTO;
import com.bookshelf.spring.dto.UserDTO;
import com.bookshelf.spring.dto.UserDTOLog;
import com.bookshelf.spring.entity.Authority;
import com.bookshelf.spring.entity.User;
import com.bookshelf.spring.security.JwtAuthenticationRequest;
import com.bookshelf.spring.security.TokenHelper;
import com.bookshelf.spring.service.AuthorityServiceInterface;
import com.bookshelf.spring.service.CustomUserDetailService;
import com.bookshelf.spring.service.UserServiceIterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService userDetailsService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserDTOLog> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,
            HttpServletResponse response
    ) throws AuthenticationException, IOException {

        // Izvrsavanje security dela
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        // Ubaci username + password u kontext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token
        User user = (User)authentication.getPrincipal();
        String jws = tokenHelper.generateToken( user.getUsername());

        // Vrati token kao odgovor na uspesno autentifikaciju
        return new ResponseEntity<>(new UserDTOLog(user,jws), HttpStatus.OK);
//        return ResponseEntity.ok(new UserTokenState(jws));
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }
    @Autowired
    UserServiceIterface userServiceIterface;

    @Autowired
    AuthorityServiceInterface authorityServiceInterface;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserToUserDTO toDTO;

//    @GetMapping(value = "/users")
//    @PreAuthorize("hasRole('ADMIN')")
//    1
//    public ResponseEntity<List<UserDTO>>getAllUsers(){
//        List<User> users = userServiceIterface.findAll();
//        return new ResponseEntity<List<UserDTO>>(toDTO.convert(users), HttpStatus.OK);
//    }
//    2
    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>>getAllUsers(@RequestParam(defaultValue = "0") int page){
        Page<User> users = userServiceIterface.findAll(page);
        List<UserDTO> retVal = new ArrayList<>();
        for (User u : users) {
            UserDTO dto=new UserDTO(u);
            dto.setTotalPages(users.getTotalPages());
            retVal.add(dto);
        }
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }


    @GetMapping(value = "/users/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>>getUsersByUsername(@RequestParam Optional<String> username,
                                                           @RequestParam(defaultValue = "0") int page){
        Page<User> users = userServiceIterface.pretraga(username.orElse("_"),page);
        List<UserDTO> retVal = new ArrayList<>();
        for (User u : users) {
            UserDTO dto=new UserDTO(u);
            dto.setTotalPages(users.getTotalPages());
            retVal.add(dto);
        }
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
    
    @GetMapping(value = "/getuser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO>getAllUsers(Principal principal){
        User user = userServiceIterface.getUserByName(principal.getName());
        return new ResponseEntity<>(toDTO.convert(user), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Integer id){
        User user = userServiceIterface.findOne(id);
        if(user!=null){
            userServiceIterface.remove(id);
            return new ResponseEntity<>(toDTO.convert(user),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/editrole/{id}",method = RequestMethod.GET)
    public ResponseEntity<UserDTO> editRole(@PathVariable Integer id){
        Authority authority;
        User user = userServiceIterface.findOne(id);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String role=user.getUserAuthority().iterator().next().getName();
        if(role.equals("ROLE_USER")){
            authority=authorityServiceInterface.findByName("ROLE_ADMIN");
            if(user.getUserAuthority().size() >0) {
                user.getUserAuthority().clear();
                user.getUserAuthority().add(authority);
            }else {
                user.getUserAuthority().add(authority);
            }
        }
        if(role.equals("ROLE_ADMIN")) {
            authority = authorityServiceInterface.findByName("ROLE_USER");
            if (user.getUserAuthority().size() > 0) {
                user.getUserAuthority().clear();
                user.getUserAuthority().add(authority);
            } else {
                user.getUserAuthority().add(authority);
            }
        }
        userServiceIterface.save(user);
        return new ResponseEntity<>(toDTO.convert(user),HttpStatus.OK);
    }

}
