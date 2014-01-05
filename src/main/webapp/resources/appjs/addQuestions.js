var QuestionAdditioner = {

	init : function(quizData) {

		var template = $("#tpl_addQuestions").html();
		$("#mainContent").html(_.template(template));

		$("#quizDataHeading").append(
				'<h3 class="panel-title" id="quizDataName" data-quizId="'
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
		
		this.addQuestion();
		this.addAnswer();
		this.editQuizName();
	},
	
	editQuizName : function(){
		$("#quizDataName").editable({
			type: 'text',
			value: $("#quizDataName").html()
		});
	},
	
	addQuestion : function(){
		$("#addQuestion").on('click', function(event) {
			event.preventDefault();
			$("#questionText").editable({
				type: 'textarea',
				value: 'Question'
			});
			$('#questionCategory').editable({
				type: 'select',
		        value: 0,    
		        source: [
		              {value: 0, text: 'None selected'},  
		              {value: 1, text: 'Single choice'},
		              {value: 2, text: 'Multiple choice'}
		           ]
		    });
			$("#addQuestion").hide('slow');
			$("#questionForm").show('slow');
			
		});
	},

	addAnswer : function(){
		$("#addAnswerBtn").on('click', function(event) {
			event.preventDefault();
		});
	}


}