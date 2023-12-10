package com.dething.telbot.newBase.events;

import com.dething.telbot.newBase.enums.TimeKeeperTypeEnum;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Observable;

@Component
@EnableScheduling
public class DayTimeKeeper extends Observable {
    public void emit() {
        // 设置状态已改变，通知观察者
        setChanged();
        notifyObservers(TimeKeeperTypeEnum.DAY);
    }

    @Scheduled(cron = "0 0 0 * * *") // 每小时执行一次，第一个0表示秒，第二个0表示分钟，*表示任意值
    public void timekeeper() {
        System.out.println("DayTimeKeeper emit.......");
        this.emit();

    }
}
