package com.rk.apache.amp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class SpringBootApacheMqApplication {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(SpringBootApacheMqApplication.class, args);
		
		JmsTemplate jmsTemplate = ac.getBean(JmsTemplate.class);
		jmsTemplate.convertAndSend("Sample", "Sending the message Again");
	}

}
