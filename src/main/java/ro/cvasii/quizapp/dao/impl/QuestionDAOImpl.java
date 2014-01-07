package ro.cvasii.quizapp.dao.impl;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.dao.QuestionDAO;
import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.generic.dao.GenericDAOImpl;

@Repository
public class QuestionDAOImpl extends GenericDAOImpl<Question, Key> implements
		QuestionDAO {

	public QuestionDAOImpl() {
		super(Question.class);
	}
}
