package ro.cvasii.quizapp.service.impl;

import javax.transaction.Transactional;

import com.google.appengine.api.datastore.KeyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import ro.cvasii.quizapp.dao.QuestionDAO;
import ro.cvasii.quizapp.dao.QuizDAO;
import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.dto.QuestionDTO;
import ro.cvasii.quizapp.generic.service.GenericServiceImpl;
import ro.cvasii.quizapp.service.QuestionService;
import ro.cvasii.quizapp.service.QuizService;
import ro.cvasii.quizapp.transformation.TransformationService;

import java.util.List;

@Service
public class QuestionServiceImpl extends GenericServiceImpl<Question, Key>
        implements QuestionService {

    private QuestionDAO questionDAO;

    @Autowired
    TransformationService transformationService;

    @Autowired
    QuizService quizService;

    @Autowired
    QuizDAO quizDAO;

    @Autowired
    public QuestionServiceImpl(QuestionDAO questionDAO) {
        super(questionDAO);
        this.questionDAO = questionDAO;
    }

    @Override
    @Transactional
    public Question save(QuestionDTO questionDTO) {
        Question question = transformationService.dtoToQuestion(questionDTO);
        Quiz quiz = quizDAO.findById(KeyFactory.createKey(Quiz.class.getSimpleName(), questionDTO.getQuizId()));
        if (quiz.getNoQuestions() == null) {
            quiz.setNoQuestions(1);
        } else {
            quiz.setNoQuestions(quiz.getNoQuestions() + 1);
        }
        quizDAO.update(quiz);
        return save(question);
    }

    @Override
    @Transactional
    public List<Question> findByQuizId(String quizId) {
        return questionDAO.findByQuizId(Long.parseLong(quizId));
    }

}
