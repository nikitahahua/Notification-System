package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.service.CSVService;
import com.notyficationsystem.NotyficationSystem.service.ContactService;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class CSVServiceImpl implements CSVService {

    private final ContactService contactService;

    public CSVServiceImpl(ContactService contactService) {
        this.contactService = contactService;
    }

    public void importCSV(InputStream inputStream, User user){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        br.lines()
                .skip(1)
                .map(line -> parseContact(line, user))
                .forEach(contactService::create);
    }

    public static Contact parseContact(String CSVline, User owner) {
        String[] result = CSVline.split(",\\s*");
        return new Contact(owner, result[0], result[1]);
    }

}
