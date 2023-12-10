package com.notyficationsystem.NotyficationSystem.service;

import com.notyficationsystem.NotyficationSystem.model.Contact;

import java.util.List;

public interface ContactService {

    Contact create(Contact person);
    Contact update(Contact person);
    Contact readByEmail(String email);
    Contact readByPhone(String phoneNumber);
    Contact readByFullName(String fullname);
    Contact readById(Long id);
    List<Contact> getAll();
    void delete(Long id);

}
