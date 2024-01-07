package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.repository.ContactRepo;
import com.notyficationsystem.NotyficationSystem.service.ContactService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;
    @Override
    public Contact create(Contact contact) {
        if (contact != null){
            return contactRepo.save(contact);
        }
        throw new IllegalArgumentException("person is not valid");
    }

    @Override
    public Contact update(Contact contact) {
        Contact oldPerson = contactRepo.findByContactName(contact.getContactName());
        oldPerson.setEmail(contact.getEmail());
        oldPerson.setContactName(contact.getContactName());
        return contactRepo.save(oldPerson);
    }
    @Override
    public Contact readByEmail(String email) {
        if (email!=null){
            return contactRepo.findByEmail(email);
        }
        throw new EntityNotFoundException("cant find Person by email");
    }

    @Override
    public Contact readByFullName(String fullname) {
        if (fullname!=null){
            return contactRepo.findByContactName(fullname);
        }
        throw new EntityNotFoundException("cant find Person by full name");
    }

    @Override
    public Contact readById(Long id) {
        return contactRepo.findById(id).orElseThrow(() ->
                new NoSuchElementException("wrong id was delivered"));
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public void delete(Long id) {
        contactRepo.delete(readById(id));
    }

}
