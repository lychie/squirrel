package org.squirrel.sample.util;

import org.squirrel.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Context {

	private static final ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
	
	private Context() {}
	
	public static Session getSession() {
		return ac.getBean(Session.class);
	}

	public static ApplicationContext getApplicationContext() {
		return ac;
	}
	
}