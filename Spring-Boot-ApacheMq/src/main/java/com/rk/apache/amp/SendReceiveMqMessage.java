package com.rk.apache.amp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;


public class SendReceiveMqMessage {

	@Value(value = "${spring.activemq.broker-url}")
	private static String connUrl;

	public static void main(String[] args) throws JMSException {
		sendMsg();
		String resp = readMsg();
		System.out.println("Data from Q:" + resp);

	}

	private static void sendMsg() throws JMSException {
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection conn = null;
		try {
			conn = cf.createConnection();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("Sample");
			session.createConsumer(destination);
			MessageProducer producer = session.createProducer(destination);
			Message msg = session.createTextMessage("Sending test message to the queue");
			producer.send(msg);
			System.out.println("Message sent to Q :" + msg.getJMSCorrelationID());
		} catch (JMSException e) {
			System.out.println("Exception :"+e);
		}
		finally {
			conn.close();
		}
	}
	
	private static String readMsg() throws JMSException {
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection conn = null;
		String qMessage = null;
		Destination destination;
		try {
			conn = cf.createConnection();
			conn.start();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("Sample");
			MessageConsumer consumer = session.createConsumer(destination);
			Message msg = consumer.receive();
			TextMessage message = (TextMessage) msg;
			System.out.println("Message from queue: "+message.getText());
			qMessage = message.getText();
		} catch (JMSException e) {
			System.out.println("Exception :"+e);
		}
		finally {
			conn.close();
		}
		return qMessage;
	}
	
	

}
