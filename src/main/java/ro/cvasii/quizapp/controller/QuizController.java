package ro.cvasii.quizapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import ro.cvasii.quizapp.dto.QuizRequestDTO;
import ro.cvasii.quizapp.service.QuizService;
import ro.cvasii.quizapp.transformation.TransformationService;

@Controller
public class QuizController {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(QuizController.class);

	private ObjectMapper mapper;

	@Autowired
	private QuizService quizService;

	@Autowired
	private TransformationService transformationService;

	@PostConstruct
	public void postContruct() {
		mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		mapper.setDateFormat(df);
		mapper.getSerializationConfig().with(df);
	}

	@RequestMapping(value = "/quiz", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String addQuiz(@RequestBody QuizRequestDTO quizRequestDTO,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		LOGGER.info(quizRequestDTO.toString());
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		LOGGER.info(currentUser.toString());

		Quiz quiz = quizService.save(quizRequestDTO, currentUser);

		return mapper.writeValueAsString(transformationService.quizToDTO(quiz));
	}

	@RequestMapping(value = "/quiz/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String saveQuiz(@RequestBody QuizRequestDTO quizRequestDTO,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {
		LOGGER.info(quizRequestDTO.toString());
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		LOGGER.info(currentUser.toString());

		Quiz quiz = quizService.update(quizRequestDTO, currentUser);

		return mapper.writeValueAsString(transformationService.quizToDTO(quiz));
	}

	@RequestMapping(value = "/quizs", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String getAllQuizs() throws JsonProcessingException {

		return mapper.writeValueAsString(quizService.findAllDTO());
	}
	
	@RequestMapping(value = "/quiz/{id}/password", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String checkQuizPassword(@PathVariable String id, @RequestBody String data) throws JsonProcessingException {

		return mapper.writeValueAsString(quizService.checkPassword(id, data));
	}
	
	@RequestMapping(value = "/quiz/{id}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String getQuiz(@PathVariable String id) throws Exception {
		return mapper.writeValueAsString(transformationService.quizToDTO(quizService.findById(id)));
	}
}
