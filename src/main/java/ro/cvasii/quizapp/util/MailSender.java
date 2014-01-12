package ro.cvasii.quizapp.util;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import ro.cvasii.quizapp.domain.QuizAppUser;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Component
public class MailSender {

	private String senderEmailAddress = "cosminradu.vasii@gmail.com";
	private String senderPersonal = "Quiz App";
	private String mailSubjectTakeQuiz = "Result From Quiz App";
	private String mailTakeQuizTemplate = "Dear Mr./Ms. {0},\n\n On {1}, the user {2} has taken the quiz \'{3}\', which you have created and received a score of {4}%. \n\n Kind regards, \n The Quiz App team";

	public void sendEmailToOwnerAfterTakingQuiz(QuizAppUser recipient,
			Date dateTaken, String quizName, Double score)
			throws UnsupportedEncodingException, MessagingException {
		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties, null);
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(senderEmailAddress, senderPersonal));

		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				recipient.getEmail()));

		message.setSubject(mailSubjectTakeQuiz);

		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		String mailMessage = MessageFormat.format(mailTakeQuizTemplate,
				recipient.getNickname(), dateTaken, currentUser.getNickname(),
				quizName, score * 100);
		message.setText(mailMessage);

		Transport.send(message);
	}
}
