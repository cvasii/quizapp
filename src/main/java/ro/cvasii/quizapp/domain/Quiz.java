package ro.cvasii.quizapp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

import ro.cvasii.quizapp.generic.domain.QuizAppEntity;

@Entity
public class Quiz extends QuizAppEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Key> categories;
    private List<Key> questions;
    private Boolean isPrivate;
    private String password;
    private Key user;
    private Date dateCreated;
    private Integer noQuestions;

    public Integer getNoQuestions() {
        return noQuestions;
    }

    public void setNoQuestions(Integer noQuestions) {
        this.noQuestions = noQuestions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Key> getCategories() {
        return categories;
    }

    public void setCategories(List<Key> categories) {
        this.categories = categories;
    }

    public List<Key> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Key> questions) {
        this.questions = questions;
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

    public Key getUser() {
        return user;
    }

    public void setUser(Key user) {
        this.user = user;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
