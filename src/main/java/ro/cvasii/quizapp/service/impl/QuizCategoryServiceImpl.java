package ro.cvasii.quizapp.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import ro.cvasii.quizapp.dao.QuizCategoryDAO;
import ro.cvasii.quizapp.domain.QuizCategory;
import ro.cvasii.quizapp.dto.QuizCategoryDTO;
import ro.cvasii.quizapp.generic.service.GenericServiceImpl;
import ro.cvasii.quizapp.service.QuizCategoryService;
import ro.cvasii.quizapp.transformation.TransformationService;

@Service
public class QuizCategoryServiceImpl extends
		GenericServiceImpl<QuizCategory, Key> implements QuizCategoryService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(QuizCategoryServiceImpl.class);

	private QuizCategoryDAO categoryDAO;
	
	@Autowired
	TransformationService transformationService;

	@Autowired
	public QuizCategoryServiceImpl(QuizCategoryDAO categoryDAO) {
		super(categoryDAO);
		this.categoryDAO = categoryDAO;

	}

	@Override
	@Transactional
	public QuizCategory findById(String id) {
		Key key = KeyFactory.createKey(QuizCategory.class.getSimpleName(),
				Long.parseLong(id));
		LOGGER.info(key.toString());
		return categoryDAO.findById(key);
	}

	@Override
	@Transactional
	public QuizCategoryDTO findDTOById(String id) {
		Key key = KeyFactory.createKey(QuizCategory.class.getSimpleName(),
				Long.parseLong(id));
		LOGGER.info(key.toString());
		QuizCategory category = categoryDAO.findById(key);
		return transformationService.quizCategoryToDTO(category);
	}

	@Override
	@Transactional
	public List<QuizCategoryDTO> findAllDTO() {
		List<QuizCategory> findAll = findAll();
		return transformationService.bulkQuizCategoryToDTO(findAll);
	}
}
