package com.bookshelf.spring.dto;

import com.bookshelf.spring.entity.Authority;
import com.bookshelf.spring.entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserDTO implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private byte[] photo;
    private String phoneNumber;
    private Date createDate;
    private Set<Authority> userAuthority=new HashSet<Authority>();
    private String roleName;
    private int totalPages;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id=user.getId();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.email=user.getEmail();
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.photo=user.getPhoto();
        this.phoneNumber=user.getPhoneNumber();
        this.createDate=user.getCreateDate();
        this.userAuthority=user.getUserAuthority();
    }

    public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Set<Authority> getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(Set<Authority> userAuthority) {
        this.userAuthority = userAuthority;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
