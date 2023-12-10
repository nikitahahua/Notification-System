package com.notyficationsystem.NotyficationSystem.service;

import com.notyficationsystem.NotyficationSystem.model.User;

import java.util.List;

public interface UserService {
    User create(User person);
    User update(User user);
    User readByEmail(String email);
    User readByPhone(String phoneNumber);
    User readByFullName(String fullname);
    User readById(Long id);
    List<User> getAll();
    void delete(Long id);
}
