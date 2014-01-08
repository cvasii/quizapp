package ro.cvasii.quizapp.dao.impl;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.dao.QuestionDAO;
import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.generic.dao.GenericDAOImpl;

import javax.persistence.Query;
import java.util.List;

@Repository
public class QuestionDAOImpl extends GenericDAOImpl<Question, Key> implements
		QuestionDAO {

	public QuestionDAOImpl() {
		super(Question.class);
	}

    @SuppressWarnings("unchecked")
	@Override
    public List<Question> findByQuizId(Long quizId) {
        Query query = this.em.createQuery("select q from " + this.clasz.getName() + " q where q.quizId = :quizId" );
        query.setParameter("quizId", quizId);
		return query.getResultList();
    }
}
