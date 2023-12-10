package com.dething.telbot.base.user;

import com.dething.cloud.common.core.util.SpringUtil;
import com.dething.telbot.base.common.IMessageHandler;
import com.dething.telbot.base.common.ITelBot;
import com.dething.telbot.base.common.impl.BasePrivateHandler;
import com.dething.telbot.base.entity.DutyPlan;
import com.dething.telbot.base.entity.User;
import com.dething.telbot.newBase.service.IDutyPlanService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class RenewalHandler extends BasePrivateHandler implements IMessageHandler {

    public RenewalHandler(ITelBot telBot) {
        super(telBot);
        dutyPlanService = SpringUtil.getBean(IDutyPlanService.class);
    }

    public IDutyPlanService dutyPlanService;

    private String userName;

    @Override
    public boolean parse(Update update) {
        String text = update.getMessage().getText().trim();

        if(text.equals("续费")){
            this.userName = update.getMessage().getFrom().getUserName();
            return true;
        }

        return false;
    }

    @Override
    public void execute() {
        User user = telBot.getUser(userName);
        Long chatId = user.getChatId();
        this.notifyMessage(chatId,"请选择续费套餐",createInlineKeyboard());
    }
    // 创建一个包含单个按钮的键盘
    private InlineKeyboardMarkup createInlineKeyboard() {
        List<DutyPlan> dutyPlans = dutyPlanService.findByDutyId(1L);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (DutyPlan dutyPlan : dutyPlans) {
            InlineKeyboardButton button0 = InlineKeyboardButton.builder().text(dutyPlan.getPrice()+"U/"+dutyPlan.getMonths()+"月").callbackData("renewal"+dutyPlan.getId()).build();
            List<InlineKeyboardButton> row0 = new ArrayList<>();
            row0.add(button0);
            rows.add(row0);
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }
}
