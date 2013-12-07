package ro.cvasii.quizapp.generic.service;

import java.util.List;

/**
 * Generic service interface for common service methods, usually wrapping DAO
 * operations
 * 
 * @author comy
 * 
 * @param <T>
 * @param <I>
 */
public interface GenericService<T, I> {

	T save(T entity);

	T update(T entity);

	T saveOrUpdate(T entity, I id);

	void delete(T entity);

	T findById(I id);

	List<T> findAll();
}
