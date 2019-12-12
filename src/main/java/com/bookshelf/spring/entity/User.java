package com.bookshelf.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable,UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",unique = true,nullable = false)
    private Integer id;
    @Column(name="first_name",nullable = false)
    private String firstName;
    @Column(name="last_name",nullable = false)
    private String lastName;
    @Column(name="email",nullable = false,unique = true)
    private String email;
    @Column(name = "username",nullable = false,unique = true)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name="phone_number",nullable = false)
    private String phoneNumber;
    @Column(name="create_date")
    private Date createDate;
    @Lob
    @Column(name = "photo")
    private byte[] photo;


    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "authority_id"))
    private Set<Authority>userAuthority=new HashSet<Authority>();

    public User() {
    }
    public User(String firstName,String lastName,String email,String username,String password,String phoneNumber,Date createDate,byte[] photo){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.username=username;
        this.password=password;
        this.photo=photo;
        this.phoneNumber=phoneNumber;
        this.createDate=createDate;

    }
    public User(String firstName,String lastName,String email,String username,String password,String phoneNumber,Date createDate,byte[] photo,Set<Authority>userAuthority){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.username=username;
        this.password=password;
        this.photo=photo;
        this.phoneNumber=phoneNumber;
        this.createDate=createDate;
        this.userAuthority=userAuthority;
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

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Set<Authority> getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(Set<Authority> userAuthority) {
        this.userAuthority = userAuthority;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userAuthority;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
