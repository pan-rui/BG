package com.qpp.listener;

import org.springframework.context.ApplicationContext;

/*
 * This class is used to get the bean defination from xml
 */
public class SpringContextUtil{
    private static ApplicationContext context;
    private  static  String webPath;

    public SpringContextUtil(){}

    public static String getWebPath() {
        return webPath;
    }
    public static void setWebPath(String webPath) {
        SpringContextUtil.webPath = webPath;
    }

    public static void setApplicationContext(ApplicationContext acx){
        context = acx;
    }

    public static ApplicationContext getApplicationContext(){
        return context;
    }

    public static Object getBean(String name){
        return context.getBean(name);
    }
}
