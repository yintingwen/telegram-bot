package com.dething.telbot.newBase.command;

import java.util.List;

public interface IBotCommandBeforeRunMultiple extends IBotCommandBeforeRun{
    List<String> getIds();
}
