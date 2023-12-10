package com.dething.telbot.newBase.manager;

import com.dething.telbot.base.entity.Bot;
import com.dething.telbot.base.entity.Group;

import java.util.HashMap;
import java.util.Map;

public abstract class TelBotManager<K, M> {
    public final Map<K, M> managerMap = new HashMap<>();

    /**
     * 添加成员
     * @param key 唯一标识
     * @param member 成员
     */
    public abstract void add(K key, M member);

    /**
     * 移除成员
     * @param key 唯一标识
     */
    public abstract void remove(K key);

    /**
     * 获取成员
     * @param key 唯一标识
     * @return 成员
     */
    public M get(K key) {
        return this.managerMap.get(key);
    }

    /**
     * 获取成员数量
     * @return 成员数量
     */
    public int size() {
        return this.managerMap.size();
    }

    /**
     * 是否包含成员
     * @param id 唯一标识
     * @return 是否包含
     */
    public boolean containsKey(K id) {
        return this.managerMap.containsKey(id);
    }
}
