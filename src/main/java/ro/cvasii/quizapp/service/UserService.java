package ro.cvasii.quizapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.cvasii.quizapp.dao.UserDAO;
import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.domain.User;

@Service
public class UserService {

	@Autowired
	UserDAO userDAO;
	

	@Transactional
	public User save(User u){
		return userDAO.save(u);
	}
	
	@Transactional
	public User update(User u){
		return userDAO.update(u);
	}
	
	@Transactional
	public User saveOrUpdate(User u){
		if(u.getId() == null){
			return userDAO.save(u);
		}
		else if(userDAO.findById(u) == null){
			return userDAO.save(u);
		}
		else{
			return userDAO.update(u);
		}
	}
	
	@Transactional
	public List<User> findAll(){
		return userDAO.findAll();
	}
	
	
}
