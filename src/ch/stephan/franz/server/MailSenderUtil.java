package ch.stephan.franz.server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import ch.stephan.franz.server.domain.User;
import ch.stephan.franz.shared.exception.UserHandlingException;

public class MailSenderUtil {
  private static final Logger LOG = Logger.getLogger(MailSenderUtil.class.getName());

  public static void sendMail(User aUser) throws UserHandlingException {
    try {
      Properties props = new Properties();
      Session session = Session.getDefaultInstance(props, null);

      String msgBody = "Das Passwort für die Franz Woertli Applikation lautet: " + aUser.getPassword();

      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("stephan.staeheli@nurfuerspam.de", "Stephan Stäheli"));
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(aUser.getEmail(), aUser.getUserName()));
      msg.setSubject("Passwort vergessen");
      msg.setText(msgBody);

      Transport.send(msg);

    } catch (Exception ex) {
      String errorMessage = "Fehler beim Versenden der EMail";
      LOG.error(errorMessage, ex);
      throw new UserHandlingException(errorMessage, ex);
    }
  }

}
