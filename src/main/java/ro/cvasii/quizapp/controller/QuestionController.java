package ro.cvasii.quizapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import ro.cvasii.quizapp.domain.Question;
import ro.cvasii.quizapp.dto.QuestionDTO;
import ro.cvasii.quizapp.service.QuestionService;
import ro.cvasii.quizapp.transformation.TransformationService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(QuestionController.class);

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    QuestionService questionService;

    @Autowired
    TransformationService transformationService;

    @RequestMapping(value = "/question", consumes = "application/json", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String saveQuestion(@RequestBody QuestionDTO questionDTO)
            throws Exception {
        LOGGER.info(questionDTO.getText() + " " + questionDTO.getQuizId() + " "
                + questionDTO.getType());
        Question question = questionService.save(questionDTO);
        return mapper.writeValueAsString(transformationService
                .questionToDTO(question));
    }

    @RequestMapping(value = "/question/quiz/{quizId}", consumes = "application/json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getQuestionsForQuiz(@PathVariable String quizId)
            throws Exception {
        List<Question> questions = new ArrayList<Question>();
        if (quizId != null) {
            questions = questionService.findByQuizId(quizId);
        }
        return mapper.writeValueAsString(transformationService
                .bulkQuestionToDTOs(questions));
    }
}
