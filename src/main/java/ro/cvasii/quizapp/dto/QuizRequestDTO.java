package ro.cvasii.quizapp.dto;

import java.util.List;

/**
 * Quiz dto clas
 * 
 * @author comy
 * 
 */
public class QuizRequestDTO {

	private Long id;
	private String name;
	private List<Long> categories;
	private Boolean isPrivate;
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getCategories() {
		return categories;
	}

	public void setCategories(List<Long> categories) {
		this.categories = categories;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return this.name + " " + this.password + " " + this.isPrivate;
	}

}
