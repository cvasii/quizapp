package ro.cvasii.quizapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.cvasii.quizapp.dto.QuizCategoryDTO;
import ro.cvasii.quizapp.service.QuizCategoryService;

@Controller
public class QuizCategoryController {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(QuizCategoryController.class);

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	QuizCategoryService categoryService;

	@RequestMapping(value = "/quizcategory", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getQuizCategories() throws Exception {
		List<QuizCategoryDTO> categories = categoryService.findAllDTO();
		return mapper.writeValueAsString(categories);
	}

	@RequestMapping(value = "/quizcategory/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getQuizCategory(@PathVariable String id) throws Exception {
		if (id == null) {
			return mapper.writeValueAsString(null);
		} else {
			QuizCategoryDTO category = categoryService.findDTOById(id);
			LOGGER.info(category.toString());
			return mapper.writeValueAsString(category);
		}
	}
}
