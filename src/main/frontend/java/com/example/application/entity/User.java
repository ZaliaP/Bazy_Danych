package com.example.application.entity;

import com.example.application.data.AbstractEntity;
import jakarta.persistence.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "kadra")
public class User /*extends AbstractEntity*/ {
/*
    private String username;
    private String lastname;
    private String email;
    private String password; // hash BCrypt

    @Enumerated(EnumType.STRING)
    private Role role;
*/

    @Id
    @Column(name = "numer_ewidencji")
    private String id;

    @Column(name = "email_harcerski")
    private String email;

    @Column(name = "imie")
    private String username;

    @Column(name = "nazwisko")
    private String lastname;

    @Column(name = "haslo")
    private String password;

    //@Enumerated(EnumType.STRING)
    @Column(name = "rola")
    private Role role;

    public User(String number, String username, String lastname, String email, String rawPassword, Role role, org.springframework.security.crypto.password.PasswordEncoder encoder) {
        this.id = number;
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.password = encoder.encode(rawPassword);
    }

    public User() {

    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
