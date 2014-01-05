package ro.cvasii.quizapp.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.cvasii.quizapp.domain.QuizCategory;
import ro.cvasii.quizapp.service.QuizCategoryService;

/**
 * Class to persist data needed which is not yet available from ui
 * 
 * @author comy
 * 
 */
@Component
public class DataSimulator {

	@Autowired
	QuizCategoryService categoryService;

	@PostConstruct
	public void postContruct() {
		this.addQuizCategories();
	}

	/**
	 * Add some categories
	 */
	public void addQuizCategories() {
		QuizCategory category1 = new QuizCategory();
		category1.setName("Others");
		QuizCategory category2 = new QuizCategory();
		category2.setName("University");
		QuizCategory category3 = new QuizCategory();
		category3.setName("Sport");
		QuizCategory category4 = new QuizCategory();
		category4.setName("Literature");

		categoryService.save(category1);
		categoryService.save(category2);
		categoryService.save(category3);
		categoryService.save(category4);
	}
}
