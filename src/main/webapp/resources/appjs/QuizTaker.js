var QuizTaker = {

	init : function(quizId) {
		$.loader({
			className : "blue-with-image",
			content : ''
		});
		var template = $("#tpl_quizTaker").html();
		$("#mainContent").html(_.template(template));

		var url = '/quiz/' + quizId;
		$.ajax({
			type : "GET",
			url : url,
			contentType : "application/json",
			success : function(data) {
				console.log(JSON.stringify(data));
				QuizTaker.displayQuestions($("#mainContent"), data);
				$.loader('close');
			},
			error : function(xhr, ajaxOptions, thrownError) {
				console.log(xhr.status);
				console.log(thrownError);
				$.loader('close');
			}
		});
	},

	displayQuestions : function($el, quizData) {
		var html = '';
		html += '<h3>' + quizData.name + '</h3>';
		var questions = quizData.questions;
		var questionNo = 0;
		$
				.each(
						questions,
						function(key, value) {
							html += '<div class="col-sm-9"><div class="panel panel-success"><div class="panel-heading">';
							questionNo++;
							html += 'Question ' + questionNo;
							html += '<p>' + value.text + '</p>' + '</div>';
							html += '<div class="panel-body">';
							var type = value.type;
							var answerType = '';
							if(type == 1){
								answerType = 'radio';
							}
							if(type == 2){
								answerType = 'checkbox';
							}
							$.each(value.answers, function(k,v){
								html += '<p>';
								html += '<label class="col-sm-1 control-label">'+
									'<input type="' + answerType + '" name="group' + questionNo + 
									'"></input></label>';
								html += v.text + '</p>'
							});
							html += '</div></div></div>';
						});

		$el.html(html);
	}
}
