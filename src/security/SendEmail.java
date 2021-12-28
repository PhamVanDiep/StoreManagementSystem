package security;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	public SendEmail(String destination, String content) {
		 String FromEmail = "huststore20194016@gmail.com";
         String FromEmailPassword = "Diep17112001";
         String Subject = "Mã xác minh quên mật khẩu - Hệ thống quản lý cửa hàng";
        
         Properties properties = new Properties();
         
	     properties.put("mail.smtp.host","smtp.gmail.com");
	     properties.put("mail.smtp.port","587");
	     properties.put("mail.smtp.auth","true");
	     properties.put("mail.smtp.starttls.enable","true");
	     
	     Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator() {
	          protected PasswordAuthentication getPasswordAuthentication(){
	               return new PasswordAuthentication(FromEmail, FromEmailPassword);
	          }
	     });
	        
	     try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(FromEmail));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
	         message.setSubject(Subject);
	         message.setText(content);
	         Transport.send(message);
	     }catch(Exception e){
	         e.printStackTrace();
	     }
	}
}
