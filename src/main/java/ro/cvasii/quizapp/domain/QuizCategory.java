package ro.cvasii.quizapp.domain;

import javax.persistence.Entity;

import ro.cvasii.quizapp.generic.domain.QuizAppEntity;

@Entity
public class QuizCategory extends QuizAppEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3448872475168500166L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
