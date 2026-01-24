package com.example.application.repository;

import com.example.application.entity.Role;
import com.example.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByRoleNot(Role role);

    @Query("""
    select u from User u
    where u.role <> :role
      and (
           lower(u.email) like lower(concat('%', :filter, '%'))
        or lower(u.username) like lower(concat('%', :filter, '%'))
        or lower(u.lastname) like lower(concat('%', :filter, '%'))
      )
""")
    List<User> searchUsers(@Param("role") Role role,
                           @Param("filter") String filter);
}
