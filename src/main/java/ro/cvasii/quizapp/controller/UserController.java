package ro.cvasii.quizapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import ro.cvasii.quizapp.domain.QuizAppUser;
import ro.cvasii.quizapp.service.QuizAppUserService;

@Controller
public class UserController {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	QuizAppUserService appUserService;

	@RequestMapping(value = "/getAppUsers", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public String getAllAppUsers(HttpServletResponse response) throws Exception {
		List<QuizAppUser> allUsers = appUserService.findAll();
		for (QuizAppUser user : allUsers) {
			LOGGER.info(user.toString());
		}
		response.setStatus(HttpStatus.OK.value());
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		mapper.setDateFormat(df);
		mapper.getSerializationConfig().with(df);
		return mapper.writeValueAsString(allUsers);
	}
	
	@RequestMapping(value = "/getCurrentUser", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public String getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nickname = "";
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		if (currentUser == null) {
			LOGGER.info("The user is not authenticated with the google account. Will pass to google for this process.");
			response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
		} else {
			nickname = currentUser.getNickname();
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(nickname);
	}
}
