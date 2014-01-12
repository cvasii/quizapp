package ro.cvasii.quizapp.service;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.dto.QuizRequestDTO;
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
	 * 
	 * @param quizRequestDTO
	 * @return
	 */
	Quiz save(QuizRequestDTO quizRequestDTO);

	/**
	 * Updates a quiz from dto
	 * 
	 * @param quizRequestDTO
	 * @return
	 */
	Quiz update(QuizRequestDTO quizRequestDTO);

	/**
	 * Verifies password for a private quiz
	 * 
	 * @param quizId
	 * @return
	 */
	Boolean checkPassword(String quizId, String password);
}
