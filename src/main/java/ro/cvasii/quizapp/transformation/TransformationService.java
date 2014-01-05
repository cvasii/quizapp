package ro.cvasii.quizapp.transformation;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.domain.QuizAppUser;
import ro.cvasii.quizapp.domain.QuizCategory;
import ro.cvasii.quizapp.dto.QuizAppUserDTO;
import ro.cvasii.quizapp.dto.QuizCategoryDTO;
import ro.cvasii.quizapp.dto.QuizFullDTO;
import ro.cvasii.quizapp.service.QuizAppUserService;
import ro.cvasii.quizapp.service.QuizCategoryService;

/**
 * Class for converting from domain objects to dtos
 * 
 * @author comy
 * 
 */
@Component
public class TransformationService {

	@Autowired
	QuizCategoryService categoryService;

	@Autowired
	QuizAppUserService appUserService;

	public QuizCategoryDTO quizCategoryToDTO(QuizCategory quizCategory) {
		if (quizCategory == null) {
			return null;
		}
		QuizCategoryDTO categoryDTO = new QuizCategoryDTO();
		categoryDTO.setId(quizCategory.getId().getId());
		categoryDTO.setName(quizCategory.getName());
		return categoryDTO;
	}

	public List<QuizCategoryDTO> bulkQuizCategoryToDTO(
			List<QuizCategory> quizCategories) {
		List<QuizCategoryDTO> result = new ArrayList<QuizCategoryDTO>();
		for (QuizCategory quizCategory : quizCategories) {
			result.add(quizCategoryToDTO(quizCategory));
		}
		return result;
	}

	public QuizFullDTO quizToDTO(Quiz quiz) {
		if (quiz == null) {
			return null;
		}
		QuizFullDTO quizDTO = new QuizFullDTO();
		quizDTO.setId(quiz.getId().getId());
		quizDTO.setName(quiz.getName());
		quizDTO.setIsPrivate(quiz.getIsPrivate());
		quizDTO.setPassword(quiz.getPassword());
		quizDTO.setDateCreated(new DateTime(quiz.getDateCreated()));
		List<QuizCategoryDTO> categories = new ArrayList<QuizCategoryDTO>();
		for (Key categoryId : quiz.getCategories()) {
			QuizCategory quizCategory = categoryService.findById(categoryId);
			categories.add(quizCategoryToDTO(quizCategory));
		}
		quizDTO.setCategories(categories);

		QuizAppUser appUser = appUserService.findById(quiz.getUser());
		quizDTO.setUser(userToDTO(appUser));

		return quizDTO;
	}

	public List<QuizFullDTO> bulkQuizToDTO(List<Quiz> quizs) {
		List<QuizFullDTO> result = new ArrayList<QuizFullDTO>();
		for (Quiz quiz : quizs) {
			result.add(quizToDTO(quiz));
		}
		return result;
	}

	public QuizAppUserDTO userToDTO(QuizAppUser user) {
		QuizAppUserDTO userDTO = new QuizAppUserDTO();
		userDTO.setId(user.getId().getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setNickname(user.getNickname());
		return userDTO;
	}
}
