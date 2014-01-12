package ro.cvasii.quizapp.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import ro.cvasii.quizapp.dao.QuizDAO;
import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.domain.QuizCategory;
import ro.cvasii.quizapp.dto.QuizRequestDTO;
import ro.cvasii.quizapp.dto.QuizFullDTO;
import ro.cvasii.quizapp.generic.service.GenericServiceImpl;
import ro.cvasii.quizapp.service.QuizService;
import ro.cvasii.quizapp.transformation.TransformationService;

@Service
public class QuizServiceImpl extends GenericServiceImpl<Quiz, Key> implements
		QuizService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(QuizServiceImpl.class);

	private QuizDAO quizDAO;

	@Autowired
	private TransformationService transformationService;

	@Autowired
	public QuizServiceImpl(QuizDAO quizDAO) {
		super(quizDAO);
		this.quizDAO = quizDAO;
	}

	@Override
	@Transactional
	public Quiz findById(String id) {
		Key key = KeyFactory.createKey(Quiz.class.getSimpleName(),
				Long.parseLong(id));
		LOGGER.info(key.toString());
		return quizDAO.findById(key);
	}

	@Override
	@Transactional
	public QuizFullDTO findDTOById(String id) {
		Key key = KeyFactory.createKey(QuizCategory.class.getSimpleName(),
				Long.parseLong(id));
		LOGGER.info(key.toString());
		Quiz quiz = quizDAO.findById(key);
		return transformationService.quizToDTO(quiz);
	}

	@Override
	@Transactional
	public List<QuizFullDTO> findAllDTO() {
		List<Quiz> findAll = findAll();
		return transformationService.bulkQuizToDTO(findAll);
	}

	@Override
	@Transactional
	public Quiz save(QuizRequestDTO quizRequestDTO) {
		Quiz quiz = transformationService.dtoToQuiz(quizRequestDTO);
		quiz = this.save(quiz);
		LOGGER.info("Quiz saved " + quiz.toString());
		return quiz;
	}

	@Override
	@Transactional
	public Quiz update(QuizRequestDTO quizRequestDTO) {
		Quiz quiz = transformationService.dtoToQuiz(quizRequestDTO);
		quiz = this.saveOrUpdate(quiz, quiz.getId());
		LOGGER.info("Quiz updated " + quiz.toString());
		return quiz;
	}

	@Override
	@Transactional
	public Boolean checkPassword(String quizId, String password) {
		Quiz quiz = this.findById(quizId);
		if (quiz.getPassword().equals(password)) {
			return true;
		}
		return false;
	}
}
