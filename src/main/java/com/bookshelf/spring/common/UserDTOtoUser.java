package com.bookshelf.spring.common;

import com.bookshelf.spring.dto.UserDTO;
import com.bookshelf.spring.entity.Authority;
import com.bookshelf.spring.entity.User;
import com.bookshelf.spring.service.AuthorityServiceInterface;
import com.bookshelf.spring.service.UserServiceIterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserDTOtoUser implements Converter<UserDTO, User> {

    @Autowired
    private UserServiceIterface userServiceIterface;

    @Autowired
    AuthorityServiceInterface authorityServiceInterface;

    @Override
    public User convert(UserDTO source) {

        Authority authority=authorityServiceInterface.findByName(source.getRoleName());


        User user;
        if(source.getId()==null){
            user=new User();
        }else{
            user=userServiceIterface.findOne(source.getId());
        }
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setEmail(source.getEmail());
        user.setCreateDate(new Date());
        user.setPhoneNumber(source.getPhoneNumber());
        user.setUsername(source.getUsername());
        user.setPhoto(source.getPhoto());
        user.setPassword(source.getPassword());

        if(user.getUserAuthority().size() >0) {
            user.getUserAuthority().clear();
            user.getUserAuthority().add(authority);
        }else{
            user.getUserAuthority().add(authority);

        }

        return user;
    }
}
