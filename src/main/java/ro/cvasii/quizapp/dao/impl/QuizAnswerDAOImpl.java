package ro.cvasii.quizapp.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.dao.QuizAnswerDAO;
import ro.cvasii.quizapp.domain.QuizAnswer;
import ro.cvasii.quizapp.generic.dao.GenericDAOImpl;

@Repository
public class QuizAnswerDAOImpl extends GenericDAOImpl<QuizAnswer, Key>
		implements QuizAnswerDAO {

	public QuizAnswerDAOImpl() {
		super(QuizAnswer.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuizAnswer> findAllByUser(Key key) {
		Query query = em.createQuery("select entity from "
				+ this.clasz.getName() + " entity where entity.user = :user");
		query.setParameter("user", key);
		return query.getResultList();
	}
}
