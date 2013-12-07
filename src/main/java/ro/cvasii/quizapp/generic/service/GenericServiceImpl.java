package ro.cvasii.quizapp.generic.service;

import java.util.List;

import javax.transaction.Transactional;

import ro.cvasii.quizapp.generic.dao.GenericDAO;

/**
 * Implementation for @see GenericService
 * 
 * @author comy
 * 
 * @param <T>
 * @param <I>
 */
public class GenericServiceImpl<T, I> implements GenericService<T, I> {

	protected GenericDAO<T, I> genericDAO;

	public GenericServiceImpl(GenericDAO<T, I> genericDAO) {
		super();
		this.genericDAO = genericDAO;
	}

	@Override
	@Transactional
	public T save(T entity) {
		return genericDAO.insert(entity);
	}

	@Override
	@Transactional
	public T update(T entity) {
		return genericDAO.update(entity);
	}

	@Override
	@Transactional
	public T saveOrUpdate(T entity, I id) {
		if (id == null || genericDAO.findById(id) == null) {
			return genericDAO.insert(entity);
		} else {
			return genericDAO.update(entity);
		}
	}

	@Override
	@Transactional
	public void delete(T entity) {
		genericDAO.delete(entity);
	}

	@Override
	public T findById(I id) {
		return genericDAO.findById(id);
	}

	@Override
	public List<T> findAll() {
		return genericDAO.findAll();
	}

}
