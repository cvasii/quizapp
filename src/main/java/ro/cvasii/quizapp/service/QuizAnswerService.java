package ro.cvasii.quizapp.service;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import ro.cvasii.quizapp.domain.QuizAnswer;
import ro.cvasii.quizapp.generic.service.GenericService;

/**
 * Defining service layer methods for QuizAnswer
 * 
 * @author comy
 * 
 */
public interface QuizAnswerService extends GenericService<QuizAnswer, Key> {

	/**
	 * Calculates score and saves the answer
	 * 
	 * @param quizAnswer
	 * @return
	 */
	QuizAnswer evaluateAndSave(QuizAnswer quizAnswer);

	/**
	 * Finds all answers of a user
	 * 
	 * @param key
	 * @return
	 */
	List<QuizAnswer> findAllByUser(Key key);

	/**
	 * Finds all answers of the current user
	 * 
	 * @return
	 */
	List<QuizAnswer> findAllCurrentUser();

}
