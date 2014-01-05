package ro.cvasii.quizapp.dao;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.domain.QuizCategory;
import ro.cvasii.quizapp.generic.dao.GenericDAO;


/**
 * Interface defining DAO operation for QuizCategory entity
 * 
 * @author comy
 * 
 */public interface QuizCategoryDAO extends GenericDAO<QuizCategory, Key> {

}
