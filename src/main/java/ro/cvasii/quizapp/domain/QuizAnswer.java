package ro.cvasii.quizapp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.generic.domain.QuizAppEntity;

@Entity
public class QuizAnswer extends QuizAppEntity {

	/**
     *
     */
	private static final long serialVersionUID = 1L;

	private Key quiz;
	private Key user;
	private Date dateTaken;
	@Embedded
	private List<QuestionAnswer> questionAnswers;
	private Double score;

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Key getQuiz() {
		return quiz;
	}

	public void setQuiz(Key quiz) {
		this.quiz = quiz;
	}

	public Key getUser() {
		return user;
	}

	public void setUser(Key user) {
		this.user = user;
	}

	public Date getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}

	public List<QuestionAnswer> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

}
