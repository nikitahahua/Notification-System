package com.notyficationsystem.NotyficationSystem.service;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User create(User person);
    User update(User user);
    User readByEmail(String email);
    User readByFullName(String fullname);
    User readById(Long id);
    List<User> getAll();
    Set<Contact> getContactsByEmail(String email);
    void delete(Long id);
    void enableUser(User user);
}
