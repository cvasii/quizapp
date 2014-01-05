package ro.cvasii.quizapp.dao.impl;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.dao.QuizDAO;
import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.generic.dao.GenericDAOImpl;

@Repository
public class QuizDAOImpl extends GenericDAOImpl<Quiz, Key> implements QuizDAO {

	public QuizDAOImpl() {
		super(Quiz.class);
	}
}
