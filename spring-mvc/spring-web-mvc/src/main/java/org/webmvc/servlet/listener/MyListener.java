package org.webmvc.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("MyListener.contextInitialized");
		sce.getServletContext().setAttribute("name", "Panda");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("MyListener.contextDestroyed");
	}
}
