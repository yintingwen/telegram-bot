package com.dething.telbot.base.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;

public class FreeMarkerUtil {
    private static Configuration configuration = null;

    public static FreeMarkerUtil INSTANCE = null;

    public static void init () throws IOException {
        String pathName = System.getProperty("user.dir") + "/telbot-freemarker";
        configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File(pathName));
        configuration.setDefaultEncoding("utf-8");
    }

    public static Template getTemplate(String path) throws IOException {
        if (configuration == null) {
            init();
        }
        return configuration.getTemplate(path);
    }

//    public static String fillTemplate (String path, Ma) {

//    }
}
