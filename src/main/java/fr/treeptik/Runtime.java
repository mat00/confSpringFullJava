package fr.treeptik;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runtime {

	private static Logger logger = Logger.getLogger(Runtime.class);
	
	public static void main(String[] args) {
		
		logger.debug("Start app"); //debug info error...
		
		ApplicationContext context = new AnnotationConfigApplicationContext(DatasourceConfiguration.class);
	}
}
