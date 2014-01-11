package ro.cvasii.quizapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.cvasii.quizapp.dto.QuizAnswerDTO;
import ro.cvasii.quizapp.service.QuizAnswerService;
import ro.cvasii.quizapp.transformation.TransformationService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@Controller
public class QuizAnswerController {

	private ObjectMapper mapper;

	@Autowired
	private TransformationService transformationService;

	@Autowired
	private QuizAnswerService quizAnswerService;

	@PostConstruct
	public void postContruct() {
		mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		mapper.setDateFormat(df);
		mapper.getSerializationConfig().with(df);
	}

	@RequestMapping(value = "/quizAnswer", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String addQuizAnswer(@RequestBody QuizAnswerDTO quizAnswerDTO,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {

		return mapper.writeValueAsString(quizAnswerService
				.evaluateAndSave(transformationService
						.dtoToQuizAnswer(quizAnswerDTO)));

	}

	@RequestMapping(value = "/quizAnswer/my", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String getMyQuizAnswers() throws Exception {

		return mapper.writeValueAsString(transformationService
				.bulkQuizAnswerToDTO(quizAnswerService.findAllCurrentUser()));

	}
}
