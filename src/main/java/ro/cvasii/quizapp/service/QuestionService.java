package ro.cvasii.quizapp.service;

import com.google.appengine.api.datastore.Key;
import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.dto.QuestionDTO;
import ro.cvasii.quizapp.generic.service.GenericService;

/**
 * Defining service layer methods for Question
 * 
 * @author comy
 * 
 */
public interface QuestionService extends GenericService<Question, Key> {

	/**
	 * Saves a question from a dto
	 * 
	 * @param quizDTO
	 * @return
	 */
	Question save(QuestionDTO questionDTO);
	
}
