package com.notyficationsystem.NotyficationSystem.telegrambot.service;

import com.notyficationsystem.NotyficationSystem.model.TelegramContact;
import com.notyficationsystem.NotyficationSystem.service.TelegramService;
import com.notyficationsystem.NotyficationSystem.service.UserService;
import com.notyficationsystem.NotyficationSystem.telegrambot.comands.BotCommands;
import com.notyficationsystem.NotyficationSystem.telegrambot.config.BotConfig;
import com.notyficationsystem.NotyficationSystem.telegrambot.constant.UserState;
import com.notyficationsystem.NotyficationSystem.telegrambot.model.UserSession;
import com.notyficationsystem.NotyficationSystem.telegrambot.model.UserStateCache;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MessageSender extends TelegramLongPollingBot implements BotCommands {
    protected final BotConfig botConfig;
    private final UserStateCache userStateCache;
    private final TelegramService telegramService;

    public MessageSender(BotConfig botConfig, UserStateCache userStateCache, TelegramService telegramService1) {
        this.botConfig = botConfig;
        this.userStateCache = userStateCache;
        this.telegramService = telegramService1;
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

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("/help"));
        rows.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("/login"));
        rows.add(row3);

        return rows;
    }

    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(replyKeyboardMarkup());
        message.setText("Welcome, " + userName + "! I'm here to connect you with our centralized notification system. \n\n" +
                "To receive notifications, please subscribe to our service. You can do this by sending your email address. Once subscribed, you'll need to confirm your email through a link sent to you. This is a necessary step to ensure the security and authenticity of your subscription.\n\n" +
                "Additionally, you have the option to create custom notification templates for your convenience. Remember, your subscription is key to staying informed and connected. Let's get started!");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = message.getText();

            switch (messageText) {
                case "/help" -> sendMessage(chatId, HELP_TEXT);
                case "/start" -> startBot(chatId, message.getFrom().getFirstName());
                case "/login" -> {
                    UserSession userSession = new UserSession();
                    userSession.setUserState(UserState.AWAITING_EMAIL);
                    userStateCache.setUserSession(chatId, userSession);
                    sendMessage(chatId, "Your chatId: " + chatId + "\nPlease enter your email: ");
                }
                default -> {
                    if (userStateCache.getUserSession(chatId).getUserState() == UserState.AWAITING_EMAIL) {
                        UserSession userSessionEmailUpdate = userStateCache.getUserSession(chatId);
                        userSessionEmailUpdate.setEmail(messageText);
                        processLogin(chatId, userSessionEmailUpdate);
                        userSessionEmailUpdate.setUserState(UserState.NONE);
                    } else {
                        sendDefaultMessage(chatId);
                    }
                }
            }

        }
    }

    private void processLogin(long chatId, UserSession userSession) {

        TelegramContact contact = telegramService.readByEmail(userSession.getEmail());
        if (contact != null) {
            contact.setChatId(chatId);
            telegramService.update(contact);
            sendMessage(chatId, "success. wait for your notification ...");
        } else {
            sendMessage(chatId, "cant find email\n no one wants you to be notified.. sad ass fuck..");
        }
    }

    private void sendDefaultMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("First of all You have to login or register an account then you should subscribe for receiving messages \nYou have to go: /login or /register");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}