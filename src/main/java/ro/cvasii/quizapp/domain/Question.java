package ro.cvasii.quizapp.domain;

import java.util.List;

import javax.persistence.Entity;

import ro.cvasii.quizapp.generic.domain.QuizAppEntity;

@Entity
public class Question extends QuizAppEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer category;
	private String text;
	private List<String> answers;
	private List<Boolean> correctAnswers;
	private Long quizId;

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public List<Boolean> getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(List<Boolean> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

}
