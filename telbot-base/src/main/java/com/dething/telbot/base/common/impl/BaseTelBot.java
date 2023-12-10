package com.dething.telbot.base.common.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.ITelGroup;
import com.dething.telbot.base.common.ITelPrivate;
import com.dething.telbot.base.entity.*;
import com.dething.telbot.newBase.events.DayTimeKeeper;
import com.dething.telbot.newBase.service.IDutyService;
import com.dething.telbot.newBase.service.IGroupService;
import com.dething.telbot.newBase.service.IRechargeService;
import com.dething.telbot.newBase.service.IUserService;
import com.dething.telbot.newBase.service.impl.DutyService;
import com.dething.telbot.newBase.service.impl.GroupService;
import com.dething.telbot.newBase.service.impl.RechargeService;
import com.dething.telbot.newBase.service.impl.UserService;
import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.util.FreeMarkerUtil;
import freemarker.template.Template;
import lombok.NonNull;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

/**
 * 机器人
 */
public abstract class BaseTelBot extends TelegramLongPollingBot implements ITelBot, Observer  {
    public IUserService userService;

    public IRechargeService rechargeService;

    public IGroupService groupService;

    protected IDutyService dutyService;

    /**
     * 机器人管理的群组,key=chat_id, value=telgroup
     */
    protected Map<Long, ITelGroup> groupMap = new HashMap<>();


    /**
     * 私聊消息处理
     */
    protected ITelPrivate telPrivate;


    /**
     * 机器人用户列表
     */
    protected Map<String, User> userMap = new HashMap<>();

    /**
     * 机器人数据
     */
    protected Bot bot;

    /**
     * 构造函数
     * @param bot 机器人数据
     * @param botOptions 配置,代理信息等
     */
    public BaseTelBot(Bot bot, DefaultBotOptions botOptions){
        super(botOptions, bot.getToken());
        this.bot = bot;

        userService = SpringUtil.getBean(UserService.class);
        groupService = SpringUtil.getBean(GroupService.class);
        dutyService = SpringUtil.getBean(DutyService.class);
        rechargeService = SpringUtil.getBean(RechargeService.class);

        DayTimeKeeper timeKeeper = SpringUtil.getBean(DayTimeKeeper.class);
        timeKeeper.addObserver(this);

        this.mountPrivateImpl();

        // 加载机器人用户
        initUserFromDb();
        // 加载机器人群组
        initGroupsFromDb();
    }

    @Override
    public AbsSender getAbsSender() {
        return this;
    }

    /**
     * 尝试创建群组
     * @param update
     */
    @Override
    public void tryCreateGroup(Update update){
        Message message = update.getMessage();
        if(message!=null){
            long chatId = message.getChatId();
            String userName = message.getFrom().getUserName();
            User user = this.userMap.get(userName);
            if(chatId >=0 || user == null){
                return;
            }

            ITelGroup telGroup = this.createGroup(chatId, user);
            this.onNewGroup(telGroup);
        }
    }

    @Override
    public ITelGroup createGroup(long chatId, User user){
        Group group = new Group();
        group.setBotId(this.bot.getId());
        group.setChatId(chatId);
        // 拉机器人进群的用户设置为该群的超管
        group.setOwner(user.getAccount());

        Map<String, Object> config = new HashMap<>();
        group.setConfig(config);

        IGroupService groupService = SpringUtil.getBean(IGroupService.class);
        group = groupService.saveGroup(group);
        if(group==null){
            return null;
        }

        this.initConfig(group);

        ITelGroup telGroup = this.mountGroupImpl(group);

        return telGroup;
    }

    /**
     * 初始化配置
     * @param group
     */
    protected void initConfig(Group group){

    }

    /**
     * 新建群回调
     * @param telGroup
     */
    protected void onNewGroup(ITelGroup telGroup){
    }

    @Override
    public String getBotUsername() {
        return this.bot.getAccount();
    }


    @Override
    public String getContacts(){
        return this.bot.getContacts();
    }


    @Override
    public void onUpdateReceived(Update update) {
        try{
            long chatId = 0;
            Message msg = update.getMessage();
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if(msg != null){
                chatId = msg.getChatId();
            }
            else if(callbackQuery != null){
                chatId = callbackQuery.getMessage().getChatId();
            }

            if(chatId<0){
                ITelGroup telGroup = this.getTelGroup(chatId);

                // 判断是否机器人加进群组
                if(msg!=null){
                    boolean bBotEnterGroup = false;
                    List<org.telegram.telegrambots.meta.api.objects.User> userList = msg.getNewChatMembers();
                    if(userList!=null){
                        for(int i=0; i<userList.size(); i++){
                            org.telegram.telegrambots.meta.api.objects.User enterUser = userList.get(i);
                            if(enterUser.getUserName().equals(bot.getAccount())){
                                bBotEnterGroup = true;
                                if(telGroup==null){
                                    this.tryCreateGroup(update);
                                }
                                break;
                            }
                        }
                        if(bBotEnterGroup){
                            // 如果是机器人进群，则发送欢迎消息
                            telGroup = this.getTelGroup(chatId);
                            Duty duty = telGroup.getTelBot().getDuty();
                            telGroup.notifyMessage(String.format("%s正在为您服务",duty.getName()));
                            return;
                        }
                    }
                }
                // 群聊消息
                if(telGroup!=null){
                    if(msg.getText()==null){
                        return;
                    }
                    telGroup.onMessage(update);
                }
            } else if(chatId>0) {
                // 更新私信聊天ID
                this.updateUserChatId(update);
                this.telPrivate.onMessage(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Duty getDuty(){
        Duty duty = dutyService.getById(this.bot.getType());
        return duty;
    }

    @Override
    public User createUser(String userName) {
        User user = this.userMap.get(userName);
        if(user==null){
            user = new User();
            user.setAccount(userName);
            user.setBotId(this.getId());
            Date now = new Date();

            Duty duty = this.getDuty();
            // 30天试用
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, duty.getTrailPeriod());
//            user.setExpireTime(calendar.getTime());

            userService.save(user);

            this.userMap.put(userName, user);
        }

        return user;
    }

    /**
     * 刷新用户数据
     * @param userName
     */
    public void flushUser(String userName){
        User user = this.userMap.get(userName);
        if(user == null) return;
        user = userService.getById(user.getId());
        this.userMap.put(userName, user);
    }

    @Override
    public User getUser(String userName){
        User user = this.userMap.get(userName);

        return user;
    }

    /**
     * 获取群组
     * @param chatId 群组ID
     * @return
     */
    protected ITelGroup getTelGroup(long chatId){
        return this.groupMap.get(chatId);
    }

    /**
     * 初始化用户列表
     */
    protected void initUserFromDb(){
        IUserService userService = SpringUtil.getBean(IUserService.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("bot_id", this.bot.getId());
        List<User> userList = userService.list(queryWrapper);
        userList.forEach(item->{
            this.userMap.put(item.getAccount(), item);
        });
    }

    protected void initGroupsFromDb(){
        IGroupService groupService = SpringUtil.getBean(IGroupService.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("bot_id", this.bot.getId());
        List<Group> groupList = groupService.list(queryWrapper);

        groupList.forEach(item -> {
            this.mountGroupImpl(item);
        });


    }

    /**
     * 获取机器人ID
     * @return
     */
    public int getId(){
        return this.bot.getId();
    }

    /**
     * 挂载私信处理实现
     */
    protected abstract void mountPrivateImpl();

    /**
     * 挂载群聊处理实现
     */
    protected abstract ITelGroup mountGroupImpl(Group group);

    /**
     * 更新用户的ID
     * @param update
     */
    protected void updateUserChatId(Update update){

        Message msg = update.getMessage();
        if(msg == null && update.hasCallbackQuery()){
            msg = update.getCallbackQuery().getMessage();
        }
        if(msg == null){
            return;
        }
        String userName = msg.getFrom().getUserName();
        User user = getUser(userName);
        if(user!=null){
            long chatId = msg.getChatId();
            if(user.getChatId() == null || user.getChatId() != chatId){
                // 更新用户与机器人的聊天ID
                user.setChatId(chatId);
                userService.saveOrUpdate(user);
            }
        }
    }

    @Override
    public void update(Observable observable, Object arg) {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        final int currentYear = currentDateTime.getYear();
//        final int currentDay = currentDateTime.getDayOfYear();
//        final int currenTotalDays = Year.of(currentYear).length();
//
//        userMap.forEach((username, user) -> {
//            Date expireTime = user.getExpireTime();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(expireTime);
//            int expireYear = calendar.get(Calendar.YEAR);
//            // 相差一年以上跳过
//            if(Math.abs(expireYear - currentYear)>1){
//                return;
//            }
//            // 获取今年的总天数
//            int expirTotalDays = Year.of(expireYear).length();
//            int expireDay = calendar.get(Calendar.DAY_OF_YEAR);
//            int updataCurrentDay = currentDay;
//            // 相差一年以内的，讲两个dayofyear的day相对小的年分给出day值
//            if (expireYear > currentYear) {
//                expireDay = expireDay + currenTotalDays;
//            } else if (expireYear < currentYear) {
//                updataCurrentDay = currentDay + expirTotalDays;
//            }
//            int duibi = expireDay-updataCurrentDay;
//            int absduibi = Math.abs(duibi);
//            if(absduibi >3){
//                return;
//            }
//            Map<String, Object> paramMap = new HashMap<>();
//            paramMap.put("absduibi",absduibi);
//            if(duibi<0){
//                this.templateMessage("/user/expireOut",paramMap,user.getChatId());
//            } else if (duibi>0) {
//                this.templateMessage("/user/expireCome",paramMap,user.getChatId());
//            }else{
//                this.templateMessage("/user/expireToday",null,user.getChatId());
//            }
//        });
    }

    public void templateMessage(String templatePath, Map<String, Object> paramMap, @NonNull Long chatId){
        try{
            Template template = FreeMarkerUtil.getTemplate(templatePath);
            StringWriter out = new StringWriter();
            template.process(paramMap, out);
            String text = out.toString();
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            message.enableHtml(true);
            this.execute(message);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected User getUserById(int userId){
        for (Map.Entry<String, User> entry : this.userMap.entrySet()) {
            User user = entry.getValue();
            if (user.getId() == userId) {

                return user;
            }
        }

        return null;
    }
    @Override
    public void onRecharge(int userId, float amount){
        int price = 5;
        User user = this.getUserById(userId);
        float baseAmount = amount;
        if(user != null){
            amount = amount + user.getAmount();

            Recharge recharge = new Recharge();
            recharge.setBotId(this.getId());
            recharge.setUserId(userId);
            recharge.setAmount(baseAmount);
            rechargeService.save(recharge);
//            Date expireTime = user.getExpireTime();
//            int months = (int)amount/price;
//            if(months>0){
//                amount = amount - months*price;
//                // 设置到期时间
//                Date curTime = new Date();
//                if(expireTime.before(curTime)){
//                    expireTime = curTime;
//                }
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(expireTime);
//                calendar.add(Calendar.MONTH, months);
//
//                expireTime = calendar.getTime();
//                user.setExpireTime(expireTime);
//            }

            user.setAmount(amount);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("amount",baseAmount);
            paramMap.put("balance",user.getAmount());
            this.templateMessage("/user/rechargeSuccess.ftl",paramMap,user.getChatId());
            // 更新到数据库
            userService.saveOrUpdate(user);

        }
    }

    /**
     * 启动运行
     */
    @Override
    public void run() {
        try{
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
