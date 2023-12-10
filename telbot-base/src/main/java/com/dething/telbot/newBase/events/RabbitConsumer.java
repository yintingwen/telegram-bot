package com.dething.telbot.newBase.events;

import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.base.entity.BotUser;
import com.dething.telbot.base.entity.RechargeNotify;
import com.dething.telbot.base.manager.IBotManager;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Service
public class RabbitConsumer {

//    @Autowired
//    IBotManager botManager;
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "", durable = "true"), // 创建一个匿名队列
//            exchange = @Exchange(value = "dething", type = "topic"),
//            key = "recharge.recharge")
//    )
//    public void handleMessage1(RechargeNotify rechargeNotif) {
//        System.out.println("get recharge");
//        System.out.println(rechargeNotif);
//        botManager.onRecharge(rechargeNotif.getUid(), Float.parseFloat(rechargeNotif.getAmount()));
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "", durable = "true"), // 创建一个匿名队列
//            exchange = @Exchange(value = "dething", type = "topic"),
//            key = "admin.user.change")
//    )
//    public void handleMessage2(BotUser botUser) {
//        System.out.println("get user.change");
//        System.out.println(botUser);
//        botManager.flushBotUser(botUser.getId(),botUser.getAccount());
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "", durable = "true"), // 创建一个匿名队列
//            exchange = @Exchange(value = "dething", type = "topic"),
//            key = "admin.bot.add")
//    )
//    public void handleMessage3(Bot bot) {
//        System.out.println("get bot add");
//        System.out.println(bot);
//        botManager.addBot(bot.getId());
//    }
}