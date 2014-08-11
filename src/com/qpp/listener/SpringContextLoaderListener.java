package com.qpp.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringContextLoaderListener extends ContextLoaderListener{
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		SpringContextUtil.setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()));
        SpringContextUtil.setWebPath(event.getServletContext().getRealPath("/"));
	}
}
