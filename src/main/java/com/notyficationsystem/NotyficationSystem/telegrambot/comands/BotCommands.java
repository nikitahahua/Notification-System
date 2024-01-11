package com.notyficationsystem.NotyficationSystem.telegrambot.comands;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/help", "bot info"),
            new BotCommand("/login", "login by already existing account")
    );

    String HELP_TEXT = """
            This bot will help to send notifications for your contacts. The following commands are available to you:
    
            Here you can only receive messages from decentralized system            

            /help - help menu, which you already see
            /login - login with user that been added as contact\s""";


}