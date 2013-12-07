package ro.cvasii.quizapp.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.dao.QuizAppUserDAO;
import ro.cvasii.quizapp.domain.QuizAppUser;
import ro.cvasii.quizapp.generic.dao.GenericDAOImpl;

@Repository
public class QuizAppUserDAOImpl extends GenericDAOImpl<QuizAppUser, Key>
		implements QuizAppUserDAO {

	public QuizAppUserDAOImpl() {
		super(QuizAppUser.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QuizAppUser findByEmailAndNickname(String email, String nickname) {
		Query query = this.em.createQuery("select u from "
				+ this.clasz.getName()
				+ " u where u.nickname = :nickname and u.email = :email");
		query.setParameter("nickname", nickname);
		query.setParameter("email", email);
		List<QuizAppUser> resultList = query.getResultList();
		if (resultList.size() == 0) {
			return null;
		} else {
			return resultList.get(0);
		}
	}
}
