package com.example.application.entity;

import com.example.application.data.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    private String username;
    private String email;
    private String password; // hash BCrypt
    private Role role;

    public User(String username, String rawPassword, Role role, org.springframework.security.crypto.password.PasswordEncoder encoder) {
        this.username = username;
        //this.email = email;
        this.role = role;
        this.password = encoder.encode(rawPassword);
    }

    public User() {

    }


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

   /* public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }*/

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
