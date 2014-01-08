package ro.cvasii.quizapp.dao;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.generic.dao.GenericDAO;

import java.util.List;

/**
 * Interface defining DAO operation for Question entity
 * 
 * @author comy
 * 
 */
public interface QuestionDAO extends GenericDAO<Question, Key> {

    /**
     * Gets the questions of a quiz
     * @param quizId
     * @return
     */
    List<Question> findByQuizId(Long quizId);

}
