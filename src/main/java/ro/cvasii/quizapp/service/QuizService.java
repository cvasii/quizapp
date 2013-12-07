package ro.cvasii.quizapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.dao.QuizDAO;
import ro.cvasii.quizapp.domain.Quiz;

@Service
public class QuizService {

	@Autowired
	private QuizDAO quizDAO;
	
	@Transactional
	public Quiz save(Quiz q){
		return quizDAO.save(q);
	}

	@Transactional
	public Quiz update(Quiz q){
		return quizDAO.update(q);
	}
	
	@Transactional
	public Quiz saveOrUpdate(Quiz q){
		if(q.getId() == null){
			return quizDAO.save(q);
		}
		else if(quizDAO.findById(q) == null){
			return quizDAO.save(q);
		}
		else{
			return quizDAO.update(q);
		}
	}

	@Transactional
	public List<Quiz> findAll(){
		return quizDAO.findAll();
	}
	
	@Transactional
	public List<Quiz> findByUser(Key userId){
		return quizDAO.findByUser(userId);
	}
}
