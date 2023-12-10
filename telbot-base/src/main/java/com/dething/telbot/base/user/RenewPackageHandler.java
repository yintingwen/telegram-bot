package com.dething.telbot.base.user;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.impl.BasePrivateHandler;
import com.dething.telbot.base.entity.BuyPlan;
import com.dething.telbot.base.entity.DutyPlan;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.service.IBuyPlanService;
import com.dething.telbot.newBase.service.IDutyPlanService;
import com.dething.telbot.newBase.service.IUserService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RenewPackageHandler extends BasePrivateHandler implements IMessageHandler {

    public IDutyPlanService dutyPlanService;

    public IUserService userService;

    public IBuyPlanService buyPlanService;
    private Long chatId;
    private String userName;
    private String msg;

    public RenewPackageHandler(ITelBot telBot) {
        super(telBot);
        userService = SpringUtil.getBean(IUserService.class);
        dutyPlanService = SpringUtil.getBean(IDutyPlanService.class);
    }

    @Override
    public boolean parse(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if(callbackQuery!=null){
            String msg =callbackQuery.getData();
            if(msg != null){
                if(msg.startsWith("renewal")){
                    this.chatId = callbackQuery.getMessage().getChatId();
                    this.userName = callbackQuery.getFrom().getUserName();
                    this.msg = msg;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void execute() {
        // 获取剩下的字符串（除去 "renewal"）
        String numericPart = this.msg.substring("renewal".length());
        // 检查剩下的字符串是否为数字
        if (numericPart.matches("\\d+")) {
            // 将数字转换为 int
            int number = 0;
            try {
                number = Integer.parseInt(numericPart);
                DutyPlan dutyPlan = dutyPlanService.getById(number);
                if(dutyPlan != null){
                    User user = telBot.getUser(userName);
                    if(user.getAmount() < dutyPlan.getPrice()){
                        this.notifyMessage(user.getChatId(), "您的余额不足，请先充值。");
                    }else{
                        user.setAmount(user.getAmount()-dutyPlan.getPrice());
                        // 设置到期时间
                        Date curTime = new Date();
//                        Date expireTime = user.getExpireTime();
//                        if(expireTime.before(curTime)){
//                            expireTime = curTime;
//                        }
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(expireTime);
//                        calendar.add(Calendar.MONTH, dutyPlan.getMonths());
//
//                        expireTime = calendar.getTime();
//                        user.setExpireTime(expireTime);
//                        userService.saveOrUpdate(user);
//
//                        BuyPlan buyPlan = new BuyPlan();
//                        buyPlan.setBotId(telBot.getId());
//                        buyPlan.setMonths(dutyPlan.getMonths());
//                        buyPlan.setPrice(dutyPlan.getPrice());
//                        buyPlan.setDutyId(dutyPlan.getDutyId());
//                        buyPlan.setUserId(user.getId());
//                        buyPlanService.save(buyPlan);
//
//                        // 格式化时间
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String formattedExpireTime = dateFormat.format(expireTime);
//                        this.notifyMessage(user.getChatId(), "充值成功，您的到期时间为"+formattedExpireTime+"。");
                    }
                }else{
                    System.out.println("Invalid dutyPlan: not a valid dutyPlan id");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid format: not a valid number");
            }
        } else {
            System.out.println("Invalid format: not a number");
        }

    }
}
