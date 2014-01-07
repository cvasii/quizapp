package ro.cvasii.quizapp.dao;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.generic.dao.GenericDAO;

/**
 * Interface defining DAO operation for Question entity
 * 
 * @author comy
 * 
 */
public interface QuestionDAO extends GenericDAO<Question, Key> {

}
