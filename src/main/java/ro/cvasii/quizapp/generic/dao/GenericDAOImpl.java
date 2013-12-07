package ro.cvasii.quizapp.generic.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implementation for @see GenericDAO
 * @author comy
 *
 * @param <T>
 * @param <I>
 */
public class GenericDAOImpl<T, I> implements GenericDAO<T, I> {

	@PersistenceContext
	protected EntityManager em;

	protected Class<T> clasz;

	public GenericDAOImpl(Class<T> clasz) {
		super();
		this.clasz = clasz;
	}

	@Override
	public T findById(I id) {
		return em.find(clasz, id);
	}

	@Override
	public T insert(T entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		return em.merge(entity);
	}

	@Override
	public void delete(T entity) {
		em.remove(entity);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return em.createQuery(
				"select entity from " + clasz.getName() + " entity")
				.getResultList();
	}

}
