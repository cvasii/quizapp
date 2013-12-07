package ro.cvasii.quizapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.domain.User;

@Repository
public class UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public User save(User u) {
		entityManager.persist(u);
		return u;
	}
	
	public User update(User u){
		return entityManager.merge(u);
	}
	
	public User findById(User u){
		return entityManager.find(User.class, u.getId());
	}
	

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return entityManager.createQuery("select u from ro.cvasii.quizapp.domain.User u")
				.getResultList();
	}
}
