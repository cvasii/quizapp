package ro.cvasii.quizapp.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import ro.cvasii.quizapp.dao.QuestionDAO;
import ro.cvasii.quizapp.dao.QuizAnswerDAO;
import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.domain.QuestionAnswer;
import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.domain.QuizAnswer;
import ro.cvasii.quizapp.domain.QuizAppUser;
import ro.cvasii.quizapp.generic.service.GenericServiceImpl;
import ro.cvasii.quizapp.service.QuizAnswerService;
import ro.cvasii.quizapp.service.QuizAppUserService;
import ro.cvasii.quizapp.service.QuizService;
import ro.cvasii.quizapp.util.MailSender;

@Service
public class QuizAnswerServiceImpl extends GenericServiceImpl<QuizAnswer, Key>
		implements QuizAnswerService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(QuizAppUserServiceImpl.class);

	private QuizAnswerDAO quizAnswerDAO;

	@Autowired
	private QuestionDAO questionDAO;

	@Autowired
	private QuizAppUserService quizAppUserService;

	@Autowired
	private MailSender mailSender;

	@Autowired
	private QuizService quizService;

	@Autowired
	public QuizAnswerServiceImpl(QuizAnswerDAO quizAnswerDAO) {
		super(quizAnswerDAO);
		this.quizAnswerDAO = quizAnswerDAO;
	}

	@Override
	@Transactional
	public QuizAnswer evaluateAndSave(QuizAnswer quizAnswer)
			throws UnsupportedEncodingException, MessagingException {
		List<QuestionAnswer> questionAnswers = quizAnswer.getQuestionAnswers();
		Integer correctQuestionsAnswered = 0;
		for (QuestionAnswer questionAnswer : questionAnswers) {
			LOGGER.info("Evaluate question " + questionAnswer.getQuestionId());
			Question question = questionDAO.findById(KeyFactory.createKey(
					Question.class.getSimpleName(),
					questionAnswer.getQuestionId()));
			List<Boolean> correctAnswers = question.getCorrectAnswers();
			List<Integer> corAnswers = new ArrayList<Integer>();
			for (int i = 0; i < correctAnswers.size(); i++) {
				if (correctAnswers.get(i)) {
					corAnswers.add(i);
				}
			}
			LOGGER.info("Correct answers are:");
			for (int i = 0; i < corAnswers.size(); i++) {
				LOGGER.info("" + corAnswers.get(i));
			}
			LOGGER.info("User's answers are:");
			for (int i = 0; i < questionAnswer.getAnswers().size(); i++) {
				LOGGER.info("" + questionAnswer.getAnswers().get(i));
			}
			if (corAnswers.size() == questionAnswer.getAnswers().size()) {
				Boolean isOk = true;
				for (int j = 0; j < corAnswers.size(); j++) {
					LOGGER.info("Comparing " + corAnswers.get(j) + " and "
							+ questionAnswer.getAnswers().get(j));
					if (corAnswers.get(j) != questionAnswer.getAnswers().get(j)) {
						LOGGER.info("This answer is not ok");
						isOk = false;
					}
				}
				if (isOk) {
					correctQuestionsAnswered++;
					LOGGER.info("This question is ok");
				}
			} else {
				LOGGER.info("This question is not ok");
			}
		}

		Double score = (double) ((1.0 * correctQuestionsAnswered) / (questionAnswers
				.size() * 1.0));
		LOGGER.info(correctQuestionsAnswered + "/" + questionAnswers.size()
				+ "=" + score);
		quizAnswer.setScore(score);
		quizAnswer = quizAnswerDAO.insert(quizAnswer);
		Quiz quiz = quizService.findById(quizAnswer.getQuiz());
		if (quiz.getNotify()) {
			QuizAppUser quizAppUser = quizAppUserService.findById(quiz
					.getUser());
			mailSender.sendEmailToOwnerAfterTakingQuiz(quizAppUser,
					quizAnswer.getDateTaken(), quiz.getName(), score);
		}
		return quizAnswer;
	}

	@Override
	@Transactional
	public List<QuizAnswer> findAllByUser(Key key) {
		return quizAnswerDAO.findAllByUser(key);
	}

	@Override
	@Transactional
	public List<QuizAnswer> findAllCurrentUser() {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		return findAllByUser(quizAppUserService.findByEmailAndNickname(
				currentUser.getEmail(), currentUser.getNickname()).getId());
	}
}
