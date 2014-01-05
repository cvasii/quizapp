package ro.cvasii.quizapp.dao.impl;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.dao.QuizCategoryDAO;
import ro.cvasii.quizapp.domain.QuizCategory;
import ro.cvasii.quizapp.generic.dao.GenericDAOImpl;

@Repository
public class QuizCategoryDAOImpl extends GenericDAOImpl<QuizCategory, Key>
		implements QuizCategoryDAO {

	public QuizCategoryDAOImpl() {
		super(QuizCategory.class);
	}
}
