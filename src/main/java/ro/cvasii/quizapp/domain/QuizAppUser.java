package ro.cvasii.quizapp.domain;

import java.util.Date;

import javax.persistence.Entity;

import org.joda.time.DateTime;

import ro.cvasii.quizapp.generic.domain.QuizAppEntity;

@Entity
public class QuizAppUser extends QuizAppEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2379839417283672072L;

	private String nickname;
	private String email;
	private Date lastTimeVisit;

	public DateTime getLastTimeVisit() {
		return new DateTime(lastTimeVisit);
	}

	public void setLastTimeVisit(Date lastTimeVisit) {
		this.lastTimeVisit = lastTimeVisit;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "QuizAppUser [nickname=" + nickname + ", email=" + email
				+ ", lastTimeVisit=" + lastTimeVisit + "]";
	}

}
