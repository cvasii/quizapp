package ro.cvasii.quizapp.dto;

import java.util.List;

public class QuestionAnswerDTO {

	private Long id;
	private List<Integer> answers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Integer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Integer> answers) {
		this.answers = answers;
	}

}
