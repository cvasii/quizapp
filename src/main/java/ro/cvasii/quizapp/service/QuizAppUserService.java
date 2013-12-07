package ro.cvasii.quizapp.service;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.domain.QuizAppUser;
import ro.cvasii.quizapp.generic.service.GenericService;

/**
 * Defining service layer methods for QuizAppUser
 * 
 * @author comy
 * 
 */
public interface QuizAppUserService extends GenericService<QuizAppUser, Key> {

	/**
	 * Returns a user by email and nickname.This should be taken from the google
	 * account so they should be unique
	 * 
	 * @param email
	 * @param nickname
	 * @return
	 */
	QuizAppUser findByEmailAndNickname(String email, String nickname);

	/**
	 * Registers the user in the application, by getting the data from the
	 * google account and saving it in the database. Updates last date when the
	 * user enters the application
	 * 
	 * @param email
	 * @param nickname
	 * @return
	 */
	QuizAppUser register(String email, String nickname);
}
