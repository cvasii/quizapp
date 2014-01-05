package ro.cvasii.quizapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import ro.cvasii.quizapp.domain.Quiz;
import ro.cvasii.quizapp.dto.QuizDTO;
import ro.cvasii.quizapp.service.QuizService;
import ro.cvasii.quizapp.transformation.TransformationService;

@Controller
public class QuizController {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(QuizController.class);

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	QuizService quizService;

	@Autowired
	TransformationService transformationService;

	@RequestMapping(value = "/quiz", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String addQuiz(@RequestBody QuizDTO quizDTO, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		LOGGER.info(quizDTO.toString());
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		LOGGER.info(currentUser.toString());

		Quiz quiz = quizService.save(quizDTO, currentUser);

		mapper.registerModule(new JodaModule());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		mapper.setDateFormat(df);
		mapper.getSerializationConfig().with(df);

		return mapper.writeValueAsString(transformationService.quizToDTO(quiz));
	}
}
