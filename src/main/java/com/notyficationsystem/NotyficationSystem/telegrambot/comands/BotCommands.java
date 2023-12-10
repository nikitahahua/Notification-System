package com.notyficationsystem.NotyficationSystem.telegrambot.comands;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/help", "bot info")
//            ,new BotCommand("/createPattern", "bot functionality")
    );

    String HELP_TEXT = """
            This bot will help to send notifications for your contacts. The following commands are available to you:

            /help - help menu
            /createPattern - create a message pattern\s""";
}