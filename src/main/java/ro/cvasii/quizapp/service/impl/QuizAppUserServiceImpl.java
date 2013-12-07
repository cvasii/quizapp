package ro.cvasii.quizapp.service.impl;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.dao.QuizAppUserDAO;
import ro.cvasii.quizapp.domain.QuizAppUser;
import ro.cvasii.quizapp.generic.service.GenericServiceImpl;
import ro.cvasii.quizapp.service.QuizAppUserService;

@Service
public class QuizAppUserServiceImpl extends
		GenericServiceImpl<QuizAppUser, Key> implements QuizAppUserService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(QuizAppUserServiceImpl.class);

	private QuizAppUserDAO appUserDAO;

	@Autowired
	public QuizAppUserServiceImpl(QuizAppUserDAO appUserDAO) {
		super(appUserDAO);
		this.appUserDAO = appUserDAO;

	}

	@Override
	@Transactional
	public QuizAppUser findByEmailAndNickname(String email, String nickname) {
		return appUserDAO.findByEmailAndNickname(email, nickname);
	}

	@Override
	@Transactional
	public QuizAppUser register(String email, String nickname) {
		QuizAppUser appUser = findByEmailAndNickname(email, nickname);
		if (appUser == null) {
			LOGGER.info("This user is not registered in the application");
			QuizAppUser newUser = new QuizAppUser();
			newUser.setEmail(email);
			newUser.setNickname(nickname);
			newUser.setLastTimeVisit(DateTime.now().toDate());
			appUser = save(newUser);
			LOGGER.info("User " + nickname + " [ " + email
					+ "] is now registered in the application. Id: "
					+ appUser.getId().getId() + ". Last visit time: "
					+ appUser.getLastTimeVisit());
		} else {
			appUser.setLastTimeVisit(DateTime.now().toDate());
			appUser = update(appUser);
			LOGGER.info("User " + nickname + " [ " + email
					+ "] is already registered in the application. Id: "
					+ appUser.getId().getId() + ". Last visit time: "
					+ appUser.getLastTimeVisit());
		}
		return appUser;
	}

}
