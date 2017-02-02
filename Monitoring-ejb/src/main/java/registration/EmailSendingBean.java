/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registration;

import javax.ejb.Stateless;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Stateless
@LocalBean
public class EmailSendingBean{
    SmtpAuthenticator authentication;
    MimeMessage message;
    public EmailSendingBean(){
      authentication=new SmtpAuthenticator();
      // Recipient's email ID needs to be mentioned.
      String to = "openstackmonitor-adm@inbox.ru";

      // Sender's email ID needs to be mentioned
      String from = "openstackmonitor@inbox.ru";

      // Assuming you are sending email from localhost
      String host = "smtp.mail.ru";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);
      properties.put("mail.smtp.port", "465");
      properties.setProperty("mail.smtp.auth","true");
      properties.put("mail.smtp.ssl.enable", "true");
      
      // Get the default Session object.
      Session session = Session.getInstance(properties,authentication);

      try {
         // Create a default MimeMessage object.
         message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

      }catch (MessagingException e)
      {
          e.printStackTrace();
      }
    }
     public void send(String login, String firstName, String lastName, String email){
        
        try {
            // Set Subject: header field
            message.setSubject("New user was registered in openstackMonitor!");
            
            // Send the actual HTML message, as big as you like
            message.setContent("<h1>Hi, admin! New user was regisered:</h1><br/>"
                    + "</h>Username: "+login+"</h3><br/>"
                    + "</h>First Name: "+firstName+"</h3><br/>"
                    + "</h>Last Name: "+lastName+"</h3><br/>"
                    + "</h>Email: "+email+"</h3><br/>"
                    + "</h>Please, follow  <a href=\"https://185.5.251.73:28443/Monitoring-web/faces/ConfirmationArea/index.xhtml\">this link</a>  to accept or decline his request.", "text/html");
            
            // Send message
            Transport.send(message);
            
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSendingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
