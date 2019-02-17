package ru.javaops.masterjava.util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

public class ThymeleafAppUtil {
     private static TemplateEngine templateEngine;
     public static TemplateEngine getTemplateEngine(ServletContext context) {
         ServletContextTemplateResolver templateResolver = 
                 new ServletContextTemplateResolver(context);
         templateResolver.setTemplateMode("XHTML");
         templateResolver.setPrefix("/WEB-INF/templates/");
         templateResolver.setSuffix(".html");
         templateResolver.setCacheTTLMs(3600000L);
         templateEngine = new TemplateEngine();
         templateEngine.setTemplateResolver(templateResolver);
         return templateEngine;
     }
} 