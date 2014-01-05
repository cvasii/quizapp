package ro.cvasii.quizapp.generic.dao;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * Generic interface for DAO operations
 * @author comy
 *
 * @param <T> - entity class
 * @param <I> - id
 */
public interface GenericDAO<T, I> {
	
	T findById( I id);
	
	T insert(T entity);
	
	T update(T entity);

	void delete(T entity);
	
	List<T> findAll();
	
	EntityManager getEm();
	
	Class<T> getClasz();
}
