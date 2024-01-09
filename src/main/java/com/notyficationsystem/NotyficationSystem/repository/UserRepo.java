package com.notyficationsystem.NotyficationSystem.repository;

import com.notyficationsystem.NotyficationSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByFullname(String fullname);
}
