package com.example.application.services;

import com.example.application.entity.Role;
import com.example.application.entity.User;
import com.example.application.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails
    loadUserByUsername(/*String username*/String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    //znalezienie każdego użytkownika poza kwatermistrzem
    public List<User> findAllNonAdmins() {
        return userRepository.findByRoleNot(Role.ADMIN);
    }

    public List<User> findAllNonAdmins(String filter) {
        if (filter == null || filter.isBlank()) {
            return userRepository.findByRoleNot(Role.ADMIN);
        }

        return userRepository.searchUsers(Role.ADMIN, filter);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
