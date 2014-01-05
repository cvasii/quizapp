package ro.cvasii.quizapp.service;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import ro.cvasii.quizapp.domain.QuizCategory;
import ro.cvasii.quizapp.dto.QuizCategoryDTO;
import ro.cvasii.quizapp.generic.service.GenericService;

/**
 * Defining service layer methods for QuizCategory
 * 
 * @author comy
 * 
 */
public interface QuizCategoryService extends GenericService<QuizCategory, Key> {

	/**
	 * Find by Key's id
	 * 
	 * @param id
	 * @return
	 */
	QuizCategory findById(String id);

	/**
	 * Find DTO by Key's id
	 * 
	 * @param id
	 * @return
	 */
	QuizCategoryDTO findDTOById(String id);

	/**
	 * Find all DTOs
	 * @return
	 */
	List<QuizCategoryDTO> findAllDTO();

}
