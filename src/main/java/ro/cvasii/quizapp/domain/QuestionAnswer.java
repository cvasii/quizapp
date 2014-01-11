package ro.cvasii.quizapp.domain;

import java.util.List;

import javax.persistence.Embeddable;

@Embeddable
public class QuestionAnswer {

	private Long questionId;
	private List<Integer> answers;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long id) {
		this.questionId = id;
	}

	public List<Integer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Integer> answers) {
		this.answers = answers;
	}

}
