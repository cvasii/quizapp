var QuestionAdditioner = {

	init : function(quizData) {

		var template = $("#tpl_addQuestions").html();
		$("#mainContent").html(_.template(template));

		$("#quizDataHeading").append(
				'<h3 class="panel-title" id="quizDataName" data-value="'
						+ quizData.id + '">' + quizData.name + '</h3>');

		$("#quizDataBody").append('<p>Categories: </p>');
		var list = $('<ul id="quizDataCategories"></ul>');
		$(quizData.categories).each(
				function(key, value) {
					$(list).append(
							'<li data-value="' + value.id + '">' + value.name
									+ '</li>');
				});
		$("#quizDataBody").append(list);
		
		if (quizData.isPrivate) {
			$("#quizDataBody").append(
					'<p id="quizDataIsPrivate" data-value="'
							+ quizData.isPrivate
							+ '">Private, protected with password.</p>');
		} else {
			$("#quizDataBody").append(
					'<p id="quizDataIsPrivate" data-value="'
							+ quizData.isPrivate
							+ '">Public, no password required.</p>');
		}
		
		$.loader('close');

	}

}