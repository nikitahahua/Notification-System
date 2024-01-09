package com.notyficationsystem.NotyficationSystem.telegrambot.service;

import com.notyficationsystem.NotyficationSystem.model.User;
import com.notyficationsystem.NotyficationSystem.payload.LoginRequest;
import com.notyficationsystem.NotyficationSystem.service.UserService;
import com.notyficationsystem.NotyficationSystem.telegrambot.comands.BotCommands;
import com.notyficationsystem.NotyficationSystem.telegrambot.config.BotConfig;
import com.notyficationsystem.NotyficationSystem.telegrambot.constant.UserState;
import com.notyficationsystem.NotyficationSystem.telegrambot.model.UserSession;
import com.notyficationsystem.NotyficationSystem.telegrambot.model.UserStateCache;
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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MessageSender extends TelegramLongPollingBot implements BotCommands {
    protected final BotConfig botConfig;
    private final UserService userService;
    @Autowired
    private UserStateCache userStateCache;
    public MessageSender(BotConfig botConfig, UserService userService) {
        this.botConfig = botConfig;
        this.userService = userService;

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

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("/notify"));
        row1.add(new KeyboardButton("/addTemplate"));
        rows.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("/subscribe"));
        row2.add(new KeyboardButton("/help"));
        rows.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("/login"));
        row3.add(new KeyboardButton("/register"));
        rows.add(row3);

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
        message.setChatId(String.valueOf(chatId));
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
        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(replyKeyboardMarkup());
        message.setText("Welcome, " + userName + "! I'm here to connect you with our centralized notification system. \n\n" +
                "To receive notifications, please subscribe to our service. You can do this by sending your email address. Once subscribed, you'll need to confirm your email through a link sent to you. This is a necessary step to ensure the security and authenticity of your subscription.\n\n" +
                "Additionally, you have the option to create custom notification templates for your convenience. Remember, your subscription is key to staying informed and connected. Let's get started!");
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = message.getText();

            switch (messageText) {
                case "/start":
                    startBot(chatId, message.getFrom().getFirstName());
                    break;
                case "/help":
                    sendHelpText(chatId, HELP_TEXT);
                    break;
                case "/notify":
                    notifyUsers(chatId);
                    break;
                case "/addTemplate":
                    addTemplate(chatId);
                    break;
                case "/subscribe":
                    subscribeUser(chatId);
                    break;
                case "/login":
                    UserSession userSession = new UserSession();
                    userSession.setUserState(UserState.AWAITING_EMAIL);
                    userStateCache.setUserSession(chatId,userSession);
                    sendMessage(chatId, "Your chatId: "+chatId+"\nPlease enter your email: ");
                    break;

                default:
                    if (userStateCache.getUserSession(chatId).getUserState() == UserState.AWAITING_EMAIL) {
                        UserSession userSessionEmailUpdate = userStateCache.getUserSession(chatId);
                        userSessionEmailUpdate.setEmail(messageText);
                        userSessionEmailUpdate.setUserState(UserState.AWAITING_PASSWORD);
                        sendMessage(chatId, "Please enter your password:");
                    } else if (userStateCache.getUserSession(chatId).getUserState() == UserState.AWAITING_PASSWORD) {
                        UserSession userSessionEmailUpdate = userStateCache.getUserSession(chatId);
                        userSessionEmailUpdate.setPassword(messageText);
                        processLogin(chatId, userSessionEmailUpdate);
                        userSessionEmailUpdate.setUserState(UserState.NONE);
                    } else {
                        sendDefaultMessage(chatId);
                    }
                    break;

            }

        }
    }

    private void processLogin(long chatId, UserSession userSession) {
        LoginRequest loginRequest = new LoginRequest(userSession.getEmail(), userSession.getPassword());
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(loginRequest);

        String url = "http://localhost:8080/auth/login";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                User user =  userService.readByEmail(userSession.getEmail());
                user.setChatId(chatId);
                userService.update(user);
                sendMessage(chatId, "Success. You are subscribed");
                return;
            } else {
                sendMessage(chatId, "Login failed. Please try again.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            sendMessage(chatId, "Error occurred during login.");
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
    public void notifyUsers(long chatId) {

    }

    public void addTemplate(long chatId) {

    }

    public void subscribeUser(long chatId) {

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