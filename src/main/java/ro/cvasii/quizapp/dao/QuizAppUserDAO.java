package ro.cvasii.quizapp.dao;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.domain.QuizAppUser;
import ro.cvasii.quizapp.generic.dao.GenericDAO;

/**
 * Interface defining DAO operation for QuizAppUser entity
 * 
 * @author comy
 * 
 */
public interface QuizAppUserDAO extends GenericDAO<QuizAppUser, Key> {

	/**
	 * Finding by email and nickname.They should be a unique combination
	 * 
	 * @param email
	 * @param nickname
	 * @return
	 */
	QuizAppUser findByEmailAndNickname(String email, String nickname);
}
