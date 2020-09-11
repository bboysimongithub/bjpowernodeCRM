package com.tankangya.crm.web.listener;


import com.tankangya.crm.settings.entity.DicValue;
import com.tankangya.crm.settings.service.DicService;
import com.tankangya.crm.settings.service.impl.DicServiceImpl;
import com.tankangya.crm.utils.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

@WebListener
public class ContextListener implements ServletContextListener {

    private DicService dicService;

    /*
    * cvsPosService = WebApplicationContextUtils
                .getWebApplicationContext(sce.getServletContext())
                .getBean(PosServiceImpl.class);

    * */

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("创建上下文作用域监听器");

        ServletContext application = sce.getServletContext();

        dicService = WebApplicationContextUtils
                .getWebApplicationContext(sce.getServletContext())
                .getBean(DicService.class);

        Map<String, List<DicValue>> map = dicService.getAll();

        Set<String> set = map.keySet();

        for (String key: set) {
            application.setAttribute(key,map.get(key));
        }


        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> keys = rb.getKeys();
        Map<String, String> pMap = new HashMap<>();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = rb.getString(key);
            pMap.put(key,value);
        }

        application.setAttribute("pMap",pMap);
    }
}
