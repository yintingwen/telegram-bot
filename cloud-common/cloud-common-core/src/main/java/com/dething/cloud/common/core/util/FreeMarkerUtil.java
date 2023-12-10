package com.dething.cloud.common.core.util;

import com.dething.cloud.common.core.config.CoreConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FreeMarkerUtil {

    private static Configuration configuration;


    private static void createInstance() throws IOException {
        CoreConfig coreConfig = SpringUtil.getBean(CoreConfig.class);

        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        String dirName = coreConfig.getFreemarkerDir() != null ? coreConfig.getFreemarkerDir() : System.getProperty("user.dir") + "/freemarker";

        configuration.setDirectoryForTemplateLoading(new File(dirName));

        configuration.setDefaultEncoding("utf-8");
    }

    /**
     * 获取模板对象
     * @param path  模板路径
     * @return 模板对象
     * @throws IOException 读取模板异常
     */
    public static Template getTemplate(String path) throws IOException {
        if (configuration == null) {
            createInstance();
        }
        return configuration.getTemplate(path);
    }

    /**
     * 获取模板内字符串
     * @param path 模板路径
     * @param params 模板参数
     * @return 模板内容字符串
     * @throws IOException 读取模板异常
     * @throws TemplateException
     */
    public static String getTemplateString (String path, Map<String, Object> params) throws IOException, TemplateException {
        StringWriter out = new StringWriter();
        Template template = FreeMarkerUtil.getTemplate(path);
        template.process(params, out);
        return out.toString();
    }
}
