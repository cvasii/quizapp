package ro.cvasii.quizapp.dao;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.generic.dao.GenericDAO;

/**
 * Interface defining DAO operation for Quiz entity
 * 
 * @author comy
 * 
 */
public interface QuizDAO extends GenericDAO<Quiz, Key> {

}
