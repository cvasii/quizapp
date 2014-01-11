package ro.cvasii.quizapp.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.domain.QuizAnswer;
import ro.cvasii.quizapp.generic.dao.GenericDAO;

/**
 * Interface defining DAO operation for QuizAnswer entity
 * 
 * @author comy
 * 
 */
public interface QuizAnswerDAO extends GenericDAO<QuizAnswer, Key> {

	List<QuizAnswer> findAllByUser(Key key);
}
