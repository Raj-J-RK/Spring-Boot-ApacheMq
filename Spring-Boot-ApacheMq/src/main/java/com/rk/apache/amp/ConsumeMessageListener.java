package com.rk.apache.amp;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQBytesMessage;

import com.google.gson.Gson;
import com.rk.apache.domain.Employee;

public class ConsumeMessageListener implements MessageListener {

	private String name;
	
	private static final String  BLOB_OUTPUT_DIR = "C:\\Temp\\output\\bytes\\";

	public ConsumeMessageListener(String name) {
		this.name = name;
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		try {
			Gson gson = new Gson();
			String fileName = message.getStringProperty("fineName");
			if(message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				System.out.println("Received from "+ name +" " + textMessage.getText());
				Employee e = gson.fromJson(textMessage.getText(), Employee.class);
				System.out.println(e);
			}
			else if(message instanceof ActiveMQBytesMessage) {
				processByteMessage((ActiveMQBytesMessage)message, fileName);
			}

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void processByteMessage(ActiveMQBytesMessage message, String fileName) throws IOException {
		writeFile(message.getContent().getData(), BLOB_OUTPUT_DIR+fileName);
	}
	
	private void writeFile(byte[] bytes, String fileName) throws IOException {
		File file = new File(fileName);
		RandomAccessFile rf = new RandomAccessFile(file, "rw");
		rf.write(bytes);
	}

}
