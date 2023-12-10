package com.dething.telbot.newBase.command;

public interface IBotCommandParserWithId extends IBotCommandParser {
    /**
     * 解析器唯一ID
     * @return 命令组
     */
    String getId();
}
