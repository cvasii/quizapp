package ro.cvasii.quizapp.dto;

import java.util.List;

import org.joda.time.DateTime;

import com.google.appengine.api.datastore.Key;

public class QuizAnswerDTO {

	private Long id;
	private DateTime dateTaken;
	private Key userId;
	private Long quizId;
	private List<QuestionAnswerDTO> questionAnswers;

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

	public Key getUserId() {
		return userId;
	}

	public void setUserId(Key userId) {
		this.userId = userId;
	}

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public List<QuestionAnswerDTO> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<QuestionAnswerDTO> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

}
