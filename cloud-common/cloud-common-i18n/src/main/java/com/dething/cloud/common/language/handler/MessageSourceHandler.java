package com.dething.cloud.common.language.handler;


import org.springframework.context.i18n.LocaleContextHolder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 这个是spring国际化的方法
 * 国际化(internationalization)又称为 i18n(读法为i 18 n，据说是因为internationalization(国际化)这个单词从i到n之间有18个英文字母，i18n的名字由此而来)
 */
public class MessageSourceHandler {
    /** 将国际化信息存放在一个map中 */
    private static final Map<String, ResourceBundle> MESSAGES = new HashMap<String, ResourceBundle>();

    /** 获取国际化信息 */
    public static String getMessage(Integer statusCode, Object... params) {
        String key = "STATUS_" + statusCode;
        /** 获取上下文信息 zh-CN 默认获取的是这个 */
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle message = MESSAGES.get(locale.getLanguage());
        if (message == null) {
            synchronized (MESSAGES) {
                //在这里读取配置信息  用于加载本地化资源文件的方便类java.util.ResourceBoundle
                /**ResourceBoundle为加载及访问资源文件提供便捷的操作，下面的语句从相对于类路径的目录中加载一个名为resource的本地化资源文件**/
                    message = ResourceBundle.getBundle("i18n/message", locale);
                MESSAGES.put(locale.getLanguage(), message);
            }
        }
        /**通过以下的代码即可访问资源文件的属性值 通过key 值访问到value值**/
        String msg = message.getString(key);
        //无法获取语言资源，返回key值
        if(msg==null) return key;

//        try{
//            //转码
//            msg = new String(msg.getBytes("ISO-8859-1"), "UTF8");
//        }catch (Exception e){
//
//        }
        //拼接返回内容
        if (params != null) {
            msg = String.format(msg, params);//进行%s  占位符的替换
        }
        return msg;
    }
}