package ro.cvasii.quizapp.transformation;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;

import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.domain.QuizAppUser;
import ro.cvasii.quizapp.domain.QuizCategory;
import ro.cvasii.quizapp.dto.AnswerDTO;
import ro.cvasii.quizapp.dto.QuestionDTO;
import ro.cvasii.quizapp.dto.QuizAppUserDTO;
import ro.cvasii.quizapp.dto.QuizCategoryDTO;
import ro.cvasii.quizapp.dto.QuizDTO;
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

	public List<AnswerDTO> bulkAnswerToDTOs(List<String> answers,
			List<Boolean> correctAnswers) {
		List<AnswerDTO> answerDTOs = new ArrayList<AnswerDTO>();
		for (int i = 0; i < answers.size(); i++) {
			AnswerDTO answerDTO = new AnswerDTO();
			answerDTO.setIsCorrect(correctAnswers.get(i));
			answerDTO.setText(answers.get(i));
			answerDTOs.add(answerDTO);
		}
		return answerDTOs;
	}

	private List<String> convertAnswersFromDTO(List<AnswerDTO> answerDTOs) {
		List<String> answers = new ArrayList<String>();
		for (AnswerDTO answerDTO : answerDTOs) {
			answers.add(answerDTO.getText());
		}
		return answers;
	}

	private List<Boolean> convertCorrectAnswersFromDTO(
			List<AnswerDTO> answerDTOs) {
		List<Boolean> correctAnswers = new ArrayList<Boolean>();
		for (AnswerDTO answerDTO : answerDTOs) {
			correctAnswers.add(answerDTO.getIsCorrect());
		}
		return correctAnswers;
	}

	public Question dtoToQuestion(QuestionDTO questionDTO) {
		Question question = new Question();
		question.setCategory(questionDTO.getType());
		question.setQuizId(questionDTO.getQuizId());
		question.setText(questionDTO.getText());
		question.setAnswers(convertAnswersFromDTO(questionDTO.getAnswers()));
		question.setCorrectAnswers(convertCorrectAnswersFromDTO(questionDTO
				.getAnswers()));
		if (questionDTO.getId() != null) {
			Key key = KeyFactory.createKey(Question.class.getSimpleName(),
					questionDTO.getId());
			question.setId(key);
		}
		return question;
	}

	public QuestionDTO questionToDTO(Question question) {
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setType(question.getCategory());
		questionDTO.setQuizId(question.getQuizId());
		questionDTO.setText(question.getText());
		questionDTO.setId(question.getId().getId());
		questionDTO.setAnswers(bulkAnswerToDTOs(question.getAnswers(),
				question.getCorrectAnswers()));
		return questionDTO;
	}

	public Quiz dtoToQuiz(QuizDTO quizDTO, User currentUser) {
		Quiz quiz = new Quiz();
		quiz.setName(quizDTO.getName());
		quiz.setPassword(quizDTO.getPassword());
		quiz.setIsPrivate(quizDTO.getIsPrivate());
		if (quizDTO.getId() != null) {
			Key quizId = KeyFactory.createKey(Quiz.class.getSimpleName(),
					quizDTO.getId());
			quiz.setId(quizId);
		}
		List<Key> categories = new ArrayList<Key>();
		for (Long keyId : quizDTO.getCategories()) {
			Key key = KeyFactory.createKey(QuizCategory.class.getSimpleName(),
					keyId);
			categories.add(key);
		}
		quiz.setCategories(categories);

		QuizAppUser user = appUserService.findByEmailAndNickname(
				currentUser.getEmail(), currentUser.getNickname());
		quiz.setUser(user.getId());

		return quiz;
	}
}
