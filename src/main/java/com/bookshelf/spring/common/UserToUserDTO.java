package com.bookshelf.spring.common;

import com.bookshelf.spring.dto.UserDTO;
import com.bookshelf.spring.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserToUserDTO implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User source) {
        UserDTO dto = new UserDTO();


        dto.setFirstName(source.getFirstName());
        dto.setLastName(source.getLastName());
        dto.setEmail(source.getEmail());
        dto.setCreateDate(new Date());
        dto.setPhoneNumber(source.getPhoneNumber());
        dto.setUsername(source.getUsername());
        dto.setPhoto(source.getPhoto());
        dto.setPassword(source.getPassword());
        dto.setUserAuthority(source.getUserAuthority());

        return dto;
    }
    public List<UserDTO> convert(List<User> source){
        List<UserDTO> ret = new ArrayList<>();
        for(User u : source){
            ret.add(convert(u));
        }
        return ret;
    }
}
