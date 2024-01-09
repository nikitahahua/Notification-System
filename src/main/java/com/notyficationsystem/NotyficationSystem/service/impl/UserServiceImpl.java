package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.TelegramContact;
import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.repository.UserRepo;
import com.notyficationsystem.NotyficationSystem.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User create(User person) {
        if (person != null){
            return userRepo.save(person);
        }
        throw new IllegalArgumentException("person is not valid");
    }

    @Override
    public User update(User person) {
        User oldPerson = userRepo.findByFullname(person.getFullname());
        oldPerson.setEmail(person.getEmail());
        oldPerson.setChatId(person.getChatId());
        return userRepo.save(oldPerson);
    }

    @Override
    public User readByEmail(String email) {
        if (email!=null){
            return userRepo.findByEmail(email);
        }
        throw new EntityNotFoundException("cant find Person by email");
    }

    @Override
    public User readByFullName(String fullname) {
        if (fullname!=null){
            return userRepo.findByFullname(fullname);
        }
        throw new EntityNotFoundException("cant find Person by full name");
    }

    @Override
    public User readById(Long id) {
           return userRepo.findById(id).orElseThrow(() ->
                   new NoSuchElementException("worng id was delivered"));
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public Set<TelegramContact> getTelegramContactsByEmail(String email) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        return user.getTelegramContacts();
    }

    @Override
    public void delete(Long id) {
        userRepo.delete(readById(id));
    }

    @Override
    public void enableUser(User user) {
        user.setEnabled(true);
        userRepo.save(user);
    }


}
