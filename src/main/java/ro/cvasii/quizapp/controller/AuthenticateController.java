package ro.cvasii.quizapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.cvasii.quizapp.service.QuizAppUserService;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class AuthenticateController {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(AuthenticateController.class);

	@Autowired
	QuizAppUserService appUserService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public void authenticate(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		if (currentUser == null) {
			LOGGER.info("The user is not authenticated with the google account. Will pass to google for this process.");
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
		} else {
			String nickname = currentUser.getNickname();
			String email = currentUser.getEmail();
			LOGGER.info("User " + nickname + " [ " + email
					+ "] just entered the application.");
			appUserService.register(email, nickname);

			resp.sendRedirect("/my");
		}

	}

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public String welcome() {
		return "index";
	}
}
