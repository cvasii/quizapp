package ro.cvasii.quizapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.domain.Quiz;

@Repository
public class QuizDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public Quiz save(Quiz q) {
		entityManager.persist(q);
		return q;
	}
	
	public Quiz update(Quiz q) {
		return entityManager.merge(q);
	}

	public Quiz findById(Quiz q){
		return entityManager.find(Quiz.class, q.getId());
	}
	 
	@SuppressWarnings("unchecked")
	public List<Quiz> findByUser(Key userId){
		Query query = entityManager.createQuery("select q from ro.cvasii.quizapp.domain.Quiz q where q.userKey = :userId");
		query.setParameter("userId", userId);
		return query
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Quiz> findAll() {
		return entityManager.createQuery("select q from ro.cvasii.quizapp.domain.Quiz q")
				.getResultList();
	}
}
