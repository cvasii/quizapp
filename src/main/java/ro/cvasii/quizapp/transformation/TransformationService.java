package ro.cvasii.quizapp.transformation;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.domain.QuestionAnswer;
import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.domain.QuizAnswer;
import ro.cvasii.quizapp.domain.QuizAppUser;
import ro.cvasii.quizapp.domain.QuizCategory;
import ro.cvasii.quizapp.dto.*;
import ro.cvasii.quizapp.service.QuestionService;
import ro.cvasii.quizapp.service.QuizAppUserService;
import ro.cvasii.quizapp.service.QuizCategoryService;
import ro.cvasii.quizapp.service.QuizService;

/**
 * Class for converting from domain objects to dtos and viceversa
 * 
 * @author comy
 */
@Service
@Transactional
public class TransformationService {

	@Autowired
	QuizCategoryService categoryService;

	@Autowired
	QuizAppUserService appUserService;

	@Autowired
	QuizService quizService;

	@Autowired
	QuestionService questionService;

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
		quizDTO.setDateCreated(new DateTime(quiz.getDateCreated()));
		quizDTO.setNoQuestions(quiz.getNoQuestions());
		quizDTO.setNotify(quiz.getNotify());
		List<QuizCategoryDTO> categories = new ArrayList<QuizCategoryDTO>();
		for (Key categoryId : quiz.getCategories()) {
			QuizCategory quizCategory = categoryService.findById(categoryId);
			categories.add(quizCategoryToDTO(quizCategory));
		}
		quizDTO.setCategories(categories);

		QuizAppUser appUser = appUserService.findById(quiz.getUser());
		quizDTO.setUser(userToDTO(appUser));

		List<Question> questions = questionService.findByQuizId(String
				.valueOf(quiz.getId().getId()));
		quizDTO.setQuestions(this.bulkQuestionToDTOs(questions));
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

	public List<QuestionDTO> bulkQuestionToDTOs(List<Question> questions) {
		List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();
		for (Question question : questions) {
			questionDTOs.add(questionToDTO(question));
		}
		return questionDTOs;
	}

	public Quiz dtoToQuiz(QuizRequestDTO quizRequestDTO) {
		Quiz quiz = new Quiz();
		quiz.setName(quizRequestDTO.getName());
		quiz.setIsPrivate(quizRequestDTO.getIsPrivate());
		if (quiz.getIsPrivate()) {
			quiz.setPassword(quizRequestDTO.getPassword());
			if (quizRequestDTO.getPasswordChanged() != null) {
				if (!quizRequestDTO.getPasswordChanged()) {
					quiz.setPassword(quizService.findById(
							String.valueOf(quizRequestDTO.getId()))
							.getPassword());
				}
			}
		} else {
			quiz.setPassword(null);
		}
		quiz.setNotify(quizRequestDTO.getNotify());
		quiz.setDateCreated(DateTime.now().toDate());

		if (quizRequestDTO.getId() != null) {
			Key quizId = KeyFactory.createKey(Quiz.class.getSimpleName(),
					quizRequestDTO.getId());
			quiz.setId(quizId);
		}

		List<Key> categories = new ArrayList<Key>();
		for (Long keyId : quizRequestDTO.getCategories()) {
			Key key = KeyFactory.createKey(QuizCategory.class.getSimpleName(),
					keyId);
			categories.add(key);
		}
		quiz.setCategories(categories);

		List<Key> questions = new ArrayList<Key>();
		if (quizRequestDTO.getId() != null) {
			List<Question> quizQuestions = questionService.findByQuizId(String
					.valueOf(quizRequestDTO.getId()));
			for (Question q : quizQuestions) {
				questions.add(q.getId());
			}
		}
		quiz.setQuestions(questions);

		quiz.setNoQuestions(questions.size());

		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		QuizAppUser user = appUserService.findByEmailAndNickname(
				currentUser.getEmail(), currentUser.getNickname());
		quiz.setUser(user.getId());

		return quiz;
	}

	public QuestionAnswer dtoToQuestionAnswer(
			QuestionAnswerDTO questionAnswerDTO) {
		QuestionAnswer questionAnswer = new QuestionAnswer();
		questionAnswer.setAnswers(questionAnswerDTO.getAnswers());
		questionAnswer.setQuestionId(questionAnswerDTO.getId());
		return questionAnswer;
	}

	public List<QuestionAnswer> bulkDTOToQuestionAnswer(
			List<QuestionAnswerDTO> questionAnswerDTOs) {
		List<QuestionAnswer> questionAnswers = new ArrayList<QuestionAnswer>();
		for (QuestionAnswerDTO questionAnswerDTO : questionAnswerDTOs) {
			questionAnswers.add(dtoToQuestionAnswer(questionAnswerDTO));
		}
		return questionAnswers;
	}

	public QuizAnswer dtoToQuizAnswer(QuizAnswerDTO answerDTO) {
		QuizAnswer answer = new QuizAnswer();
		Quiz quiz = quizService.findById(String.valueOf(answerDTO.getQuiz()
				.getId()));
		answer.setQuiz(quiz.getId());
		answer.setDateTaken(DateTime.now().toDate());
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		QuizAppUser user = appUserService.findByEmailAndNickname(
				currentUser.getEmail(), currentUser.getNickname());
		answer.setUser(user.getId());
		answer.setQuestionAnswers(bulkDTOToQuestionAnswer(answerDTO
				.getQuestionAnswers()));
		return answer;
	}

	public QuizAnswerDTO quizAnswerToDTO(QuizAnswer answer) {
		QuizAnswerDTO dto = new QuizAnswerDTO();
		dto.setId(answer.getId().getId());
		dto.setDateTaken(new DateTime(answer.getDateTaken()));
		dto.setScore(answer.getScore());
		dto.setUser(this.userToDTO(appUserService.findById(answer.getUser())));
		dto.setQuiz(this.quizToDTO(quizService.findById(answer.getQuiz())));
		return dto;
	}

	public List<QuizAnswerDTO> bulkQuizAnswerToDTO(List<QuizAnswer> quizAnswers) {
		List<QuizAnswerDTO> dtos = new ArrayList<QuizAnswerDTO>();
		for (QuizAnswer answer : quizAnswers) {
			dtos.add(this.quizAnswerToDTO(answer));
		}
		return dtos;
	}
}
