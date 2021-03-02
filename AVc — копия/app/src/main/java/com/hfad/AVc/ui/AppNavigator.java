package com.hfad.AVc.ui;

import com.hfad.AVc.rote.OpenMainActivity;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Command;

public class AppNavigator implements Navigator {
    @Override
    public void applyCommands(Command[] commands) {
        if (commands.length == 1) {
            Command cmd = commands[0];
            if (cmd instanceof OpenMainActivity) {
                new OpenMainActivity();
                return;
            }
        }
    }
}
