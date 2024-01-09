package com.notyficationsystem.NotyficationSystem.telegrambot.comands;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/help", "bot info"),
            new BotCommand("/template", "bot functionality"),
            new BotCommand("/notify", "bot functionality"),
            new BotCommand("/subscribe", "bot functionality"),
            new BotCommand("/login", "bot functionality"),
            new BotCommand("/register", "bot functionality")
    );

    String HELP_TEXT = """
            This bot will help to send notifications for your contacts. The following commands are available to you:

            /help - help menu
            /createPattern - create a message pattern\s""";
}