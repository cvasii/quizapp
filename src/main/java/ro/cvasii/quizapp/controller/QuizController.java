package ro.cvasii.quizapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.UserServiceFactory;

import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.domain.User;
import ro.cvasii.quizapp.service.QuizService;
import ro.cvasii.quizapp.service.UserService;

@Controller
public class QuizController {

	@Autowired
	private QuizService quizService;

	@Autowired
	private UserService userService;

	private User bigUser;

	@RequestMapping(value = "/check")
	@ResponseBody
	public String checkUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		com.google.appengine.api.users.UserService userService = UserServiceFactory
				.getUserService();
		com.google.appengine.api.users.User currentUser = userService
				.getCurrentUser();

		if (currentUser == null) {
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		}

		return "ok";
	}

	@RequestMapping(value = "/findAll", produces = "application/json")
	@ResponseBody
	public List<Quiz> findAll() {
		User user = new User();
		user.setUsername("cvasii");
		user = userService.saveOrUpdate(user);
		bigUser = user;
		Quiz q1 = new Quiz();
		q1.setName("quiz1");
		// q1 = quizService.saveOrUpdate(q1);
		q1.setUserKey(user.getId());
		q1 = quizService.saveOrUpdate(q1);

		Quiz q2 = new Quiz();
		q2.setName("quiz2");
		// q2 = quizService.saveOrUpdate(q2);
		q2.setUserKey(user.getId());
		q2 = quizService.saveOrUpdate(q2);

		List<Key> quizsKeys = new ArrayList<Key>();
		quizsKeys.add(q1.getId());
		quizsKeys.add(q2.getId());
		user.setQuizsKeys(quizsKeys);
		user = userService.saveOrUpdate(user);
		bigUser = user;

		return quizService.findAll();
	}

	@RequestMapping(value = "/findByUser", produces = "application/json")
	@ResponseBody
	public List<Quiz> findGuizsOfAUser() {
		return quizService.findByUser(bigUser.getId());

	}

	@RequestMapping(value = "/findAllUsers", produces = "application/json")
	@ResponseBody
	public List<User> findAllUsers() {
		return userService.findAll();

	}

}
