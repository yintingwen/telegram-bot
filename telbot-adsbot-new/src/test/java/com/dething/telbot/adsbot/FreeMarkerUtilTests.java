package com.dething.telbot.adsbot;

import com.dething.cloud.common.core.util.FreeMarkerUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class FreeMarkerUtilTests {
    @Test
    void contextLoads() throws IOException, TemplateException {
        Template template = FreeMarkerUtil.getTemplate("/user/expireOut.ftl");
        StringWriter out = new StringWriter();
        Map<String, Object> params = new HashMap<>();
        params.put("absduibi", 123);
        template.process(params, out);
        String text = out.toString();
        System.out.println(text);
    }
}
