package com.rk.apache.amp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.command.ActiveMQQueue;

public class SendFile {
	
	static ConnectionFactory factory = null;
	static Connection connection = null;
	static Session session = null;
	static Destination destination = null;
	static MessageProducer messageProducer = null;
	static final String BLOB_FILESERVER = "?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8761/fileserver/";
	static final String FILES_PATH = "C:\\Users\\HP\\Documents\\Spring-Boot-ApacheMq\\Spring-Boot-ApacheMq\\src\\main\\resources\\FilesToSend";
	
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
	
	private static void sendFilesAsByte() {
		try {
			File[] files = new File(FILES_PATH).listFiles();
			for(File f : files) {
				sendByteFile(f);
			}
		}
		catch (JMSException e) {
			System.out.println("Exception : "+e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void sendByteFile(File file) throws JMSException, IOException{
		BytesMessage bytesMessage = session.createBytesMessage();
		bytesMessage.setStringProperty("fineName", file.getName());
		bytesMessage.writeBytes(readFile(file));
		messageProducer.send(bytesMessage);
	}
	
	private static byte[] readFile(File file) throws IOException {
		RandomAccessFile rf = new RandomAccessFile(file, "r");
		byte[] bytes = new byte[(int) rf.length()];
		rf.readFully(bytes);
		return bytes;
	}
	
	public static void main(String[] args) throws JMSException {
		// TODO Auto-generated method stub
		try {
		setUpProducer();
		sendFilesAsByte();
		}
		finally {
			connection.close();
		}
	}

}
