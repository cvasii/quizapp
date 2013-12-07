package ro.cvasii.quizapp.generic.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.appengine.api.datastore.Key;

/**
 * Generic entity to be extended by all the entities in the app
 * 
 * @author comy
 * 
 */
@MappedSuperclass
public class QuizAppEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

}
