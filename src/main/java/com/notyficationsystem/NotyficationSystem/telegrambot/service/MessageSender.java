package com.notyficationsystem.NotyficationSystem.telegrambot.service;

import com.notyficationsystem.NotyficationSystem.service.impl.CSVServiceImpl;
import com.notyficationsystem.NotyficationSystem.telegrambot.comands.BotCommands;
import com.notyficationsystem.NotyficationSystem.telegrambot.config.BotConfig;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MessageSender extends TelegramLongPollingBot implements BotCommands {
    protected final BotConfig botConfig;

    public MessageSender(BotConfig botConfig) {
        this.botConfig = botConfig;

        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    public ReplyKeyboardMarkup replyKeyboardMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboardRows());

        return replyKeyboardMarkup;
    }

    public List<KeyboardRow> keyboardRows() {
        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(new KeyboardRow(keyboardButtons()));
        return rows;
    }

    public List<KeyboardButton> keyboardButtons() {
        List<KeyboardButton> buttons = new ArrayList<>();
        KeyboardButton keyboardButton = new KeyboardButton("Create Pattern");
        keyboardButton.setRequestLocation(true);
        buttons.add(keyboardButton);
        return buttons;
    }

    private void sendHelpText(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setReplyMarkup(replyKeyboardMarkup());
        message.setText("Welcome , " + userName + "! I'm ready to serve you. \n\n" +
                "If you want to get weather in some city, then you can send me name of the City.\n\n" +
                "If you want to get weather by your location, then you can send me your location and " +
                "i'll return current weather by your coordinates.");

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }
    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {

        Message message = update.getMessage();
        long chatId = message.getChatId();

        if(update.hasMessage() && update.getMessage().hasText()){

            String messageText = message.getText();


            String memberName = message.getFrom().getFirstName();

            switch (messageText) {
                case "/help" -> sendHelpText(chatId, HELP_TEXT);
//                case "/createPattern" -> createPattern(chatId, messageText);
                default -> {
                    SendMessage text = new SendMessage();
                    text.setChatId(chatId);
                    text.setText(" FUCK PINGUINS ");

                    try {
                        execute(text);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        else if (update.getMessage().hasDocument()) {
            if (message.getDocument().getFileName().endsWith(".csv")) {
                String senderId = message.getFrom().getId().toString();

                // Get information about the document
                String fileId = message.getDocument().getFileId();
                String caption = message.getCaption(); // Additional text with the document, if any

                InputStream fileInputStream = null;
                try {
                    fileInputStream = new FileInputStream(String.valueOf(message.getDocument()));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
//                CSVservice.importCSV(fileInputStream);
                log.info("CSV file received");

            } else {
                // If it's not a CSV file, you can send the user an error message
                sendMessage(chatId, "Please send a file in CSV format.");
            }
        }
    }

    private void sendMessage(long chatId, String s) {
    }

    private void createPattern(long chatId, String messageText) {



    }
}