package com.rk.apache.amp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.rk.apache.domain.Employee;

public class ConsumeMessageListener implements MessageListener {

	private String name;

	public ConsumeMessageListener(String name) {
		this.name = name;
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			if(message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				System.out.println("Received from "+ name +" " + textMessage.getText());
			}

			else {
				System.out.println("Message :" + message);
			}

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
