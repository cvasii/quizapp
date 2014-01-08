package ro.cvasii.quizapp.dto;

import java.util.List;

import org.joda.time.DateTime;

/**
 * Quiz dto class with more details
 *
 * @author comy
 */
public class QuizFullDTO {

    private Long id;
    private QuizAppUserDTO user;
    private String name;
    private List<QuizCategoryDTO> categories;
    private Boolean isPrivate;
    private String password;
    private DateTime dateCreated;
    private Integer noQuestions;
    private List<QuestionDTO> questions;

    public Integer getNoQuestions() {
        return noQuestions;
    }

    public void setNoQuestions(Integer noQuestions) {
        this.noQuestions = noQuestions;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuizAppUserDTO getUser() {
        return user;
    }

    public void setUser(QuizAppUserDTO user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuizCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<QuizCategoryDTO> categories) {
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

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return this.name + " " + this.password + " " + this.isPrivate;
    }

}
