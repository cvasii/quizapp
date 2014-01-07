package ro.cvasii.quizapp.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import ro.cvasii.quizapp.dao.QuestionDAO;
import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.dto.QuestionDTO;
import ro.cvasii.quizapp.generic.service.GenericServiceImpl;
import ro.cvasii.quizapp.service.QuestionService;
import ro.cvasii.quizapp.transformation.TransformationService;

@Service
public class QuestionServiceImpl extends GenericServiceImpl<Question, Key>
		implements QuestionService {

	private QuestionDAO questionDAO;

	@Autowired
	TransformationService transformationService;

	@Autowired
	public QuestionServiceImpl(QuestionDAO questionDAO) {
		super(questionDAO);
		this.questionDAO = questionDAO;
	}

	@Override
	@Transactional
	public Question save(QuestionDTO questionDTO) {
		Question question = transformationService.dtoToQuestion(questionDTO);
		return save(question);
	}

}
