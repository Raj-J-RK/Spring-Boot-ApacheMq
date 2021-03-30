package com.rk.apache.amp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import com.google.gson.Gson;
import com.rk.apache.domain.Employee;

public class BulkMessageSender {

	static ConnectionFactory factory = null;
	static Connection connection = null;
	static Session session = null;
	static Destination destination = null;
	static MessageProducer messageProducer = null;
	
	private static void setUpProducer() {
		try {
			factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			destination = new ActiveMQQueue("Sample");
			messageProducer = session.createProducer(destination);
		} catch (JMSException e) {
			System.out.println("Exception : "+e);
		}
	}
	
	private static void sendTextMessages() {
		Message msg;
			try {
				for(int i=0;i<50;i++) {
				msg = session.createTextMessage("Text Message : "+i);
				msg.setJMSType("TEXT");
				msg.setIntProperty("Text Message property",i);
				messageProducer.send(msg);
				}
				System.out.println("All Text messages sent to Q");
			} catch (JMSException e) {
				System.out.println("Exception : "+e);
			}
	}
	
	private static void sendObjectMessages() {
		Message msg;
		Gson gson = new Gson();
		try {
			for(int i=0;i<50;i++) {
				Employee emp = new Employee("name "+i, 30+i, 1000+i, "location"+i);
				//msg = session.createObjectMessage(emp);
				String jsonMsg = gson.toJson(emp);
				msg = session.createTextMessage(jsonMsg);
				msg.setJMSType("TEXT");
				messageProducer.send(msg);
			}
		}
		catch (JMSException e) {
			System.out.println("Exception : "+e);
		}
	}
	
	public static void main(String[] args) throws JMSException {
		// TODO Auto-generated method stub
		try {
		setUpProducer();
		//sendTextMessages();
		sendObjectMessages();
		}
		finally {
			connection.close();
		}
	}
}
