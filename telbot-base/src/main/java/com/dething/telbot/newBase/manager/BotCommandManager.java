package com.dething.telbot.newBase.manager;

import com.dething.telbot.newBase.command.*;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 消息处理器管理器
 */
public class BotCommandManager {
    private final Map<String, IBotCommandParser> parserMap = new HashMap<>();

    private final Map<String, Set<IBotCommandBeforeRun>> beforeRunMap = new HashMap<>();

    /**
     * 解析Update
     * @param group 分组
     * @param update 更新
     */
    public void parseUpdate (String group, Update update, Integer botId) {
        BotCommandContext context = new BotCommandContext(this, update, botId);
        this.parseContext(group, context);
    };

    /**
     * 解析上下文
     * @param groupOrId 分组或id
     * @param context 上下文
     */
    public void parseContext (String groupOrId, BotCommandContext context) {
        try {
            if (groupOrId.contains("*")) {
                this.runParsers(groupOrId, context);
            } else {
                this.runParser(groupOrId, context);
            }
        } catch (BotCommandRedirectException e) {
            this.parseContext(e.getTarget(), context);
        }
    }

    /**
     * 运行解析器
     * @param id 解析器ID
     * @param context 上下文
     */
    public boolean runParser (String id, BotCommandContext context) {
        IBotCommandParser parser = this.parserMap.get(id);
        if (parser == null) return false;
        // beforeParse
        boolean pass = parser.parse(context);
        if (!pass) return false;
        // beforeRun
        boolean beforeRunResult = this.runInterceptorBeforeRun(id, context);
        if (!beforeRunResult) return false;
        // run
        parser.run(context);
        return true;
    };

    /**
     * 运行多个解析器，通配符
     * @param group 分组，携带通配符
     * @param context 上下文
     */
    public void runParsers (String group, BotCommandContext context) {
        String groupReg = group.replaceAll("\\*", ".*");
        List<String> groupList = this.parserMap.keySet().stream().filter(groupName -> groupName.matches(groupReg)).collect(Collectors.toList());
        for (String groupItem : groupList) {
            boolean isRun = this.runParser(groupItem, context);
            if (isRun) return;
        }
    };

    public boolean runInterceptorBeforeRun (String id, BotCommandContext context) {
        Set<IBotCommandBeforeRun> beforeRuns = this.beforeRunMap.get(id);
        if (beforeRuns == null || beforeRuns.size() == 0) return true;
        for (IBotCommandBeforeRun interceptor: beforeRuns) {
            boolean result = interceptor.run(context);
            if (!result) return false;
        }
        return true;
    }

    public void addParser (String id, IBotCommandParser parser) {
        this.parserMap.put(id, parser);
    };

    public void addParser (IBotCommandParserWithId parser) {
        this.addParser(parser.getId(), parser);
    };

    public void removeParser (String group, IBotCommandParser parser) {
        IBotCommandParser parserList = this.parserMap.get(group);
        if (parserList == null) return;
        this.parserMap.remove(group);
    };

    public void removeParser (IBotCommandParserWithId parser) {
        this.removeParser(parser.getId(), parser);
    };

    public void addInterceptor (IBotCommandBeforeRunMultiple interceptor) {
        List<String> ids = interceptor.getIds();
        for (String id: ids) {
            Set<IBotCommandBeforeRun> interceptorSet = this.beforeRunMap.computeIfAbsent(id, (item) -> new HashSet<>());
            interceptorSet.add(interceptor);
        }
//        IBotCommandParser parser = this.parserMap.get(group);
//        if (parser == null) return;
//        parser.addInterceptor(handler);
    };
}
