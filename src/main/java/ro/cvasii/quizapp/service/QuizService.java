package ro.cvasii.quizapp.service;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.dto.QuizDTO;
import ro.cvasii.quizapp.dto.QuizFullDTO;
import ro.cvasii.quizapp.generic.service.GenericService;

/**
 * Defining service layer methods for Quiz
 * 
 * @author comy
 * 
 */
public interface QuizService extends GenericService<Quiz, Key> {

	/**
	 * Find by Key's id
	 * 
	 * @param id
	 * @return
	 */
	Quiz findById(String id);

	/**
	 * Find DTO by Key's id
	 * 
	 * @param id
	 * @return
	 */
	QuizFullDTO findDTOById(String id);

	/**
	 * Find all DTOs
	 * 
	 * @return
	 */
	List<QuizFullDTO> findAllDTO();

	/**
	 * Saves a quiz from a dto
	 * @param quizDTO
	 * @return
	 */
	Quiz save(QuizDTO quizDTO, User currentUser);
}
