package com.notyficationsystem.NotyficationSystem.repository;

import com.notyficationsystem.NotyficationSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByFullname(String fullname);
}
