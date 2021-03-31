package com.rk.apache.amp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class ReceiveFile {
	
	static ConnectionFactory factory = null;
	static Connection connection = null;
	static Session session = null;
	static Destination destination = null;
	static MessageConsumer messageConsumer = null;
	static Queue queue = null;

	private static void setUpConsumer() {
		try {
			factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = factory.createConnection();
			//connection.start();
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			destination = new ActiveMQQueue("Sample");
		} catch (JMSException e) {
			System.out.println("Exception : "+e);
		}
	}
	
	private static void readTextMessages() {
		try {
			messageConsumer = session.createConsumer(destination);
			messageConsumer.setMessageListener(new ConsumeMessageListener("Consume"));
			connection.start();

		}
		catch(JMSException e) {
			System.out.println("Exception:"+e);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		setUpConsumer();
		readTextMessages();
	}

}
