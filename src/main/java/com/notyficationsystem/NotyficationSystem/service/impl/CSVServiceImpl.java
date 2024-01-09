package com.notyficationsystem.NotyficationSystem.service.impl;

import com.notyficationsystem.NotyficationSystem.model.Contact;
import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.*;

@Service
public class CSVServiceImpl implements CSVService {

    @Autowired
    private ContactServiceImpl contactService;
    @Autowired
    private final TelegramLongPollingBot bot;

    public CSVServiceImpl(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    public void importCSV(InputStream inputStream, User user){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        br.lines()
                .skip(1)
                .map(line -> parseContact(line, contactService, user))
                .forEach(contactService::create);
    }

    public static Contact parseContact(String CSVline, ContactServiceImpl service, User owner) {
        String[] result = CSVline.split(",\\s*");
        return new Contact(owner, result[0], result[1]);
    }

}
