package ro.cvasii.quizapp.dto;

import java.util.List;

import org.joda.time.DateTime;

public class QuizAnswerDTO {

	private Long id;
	private QuizFullDTO quiz;
	private DateTime dateTaken;
	private QuizAppUserDTO user;
	private List<QuestionAnswerDTO> questionAnswers;
	private Double score;

	
	public QuizFullDTO getQuiz() {
		return quiz;
	}

	public void setQuiz(QuizFullDTO quiz) {
		this.quiz = quiz;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(DateTime dateTaken) {
		this.dateTaken = dateTaken;
	}

	public QuizAppUserDTO getUser() {
		return user;
	}

	public void setUser(QuizAppUserDTO user) {
		this.user = user;
	}


	public List<QuestionAnswerDTO> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<QuestionAnswerDTO> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

}
