var QuizTaker = {
		
	id: undefined,	

	init : function(quizId) {
		$.loader({
			className : "blue-with-image",
			content : ''
		});
		QuizTaker.id = quizId;
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
		html += '<h3 class="text-center">' + quizData.name + '</h3>';
		var questions = quizData.questions;
		var questionNo = 0;
		$
				.each(
						questions,
						function(key, value) {
							html += '<div class="row">';
							html += '<div class="col-sm-9" data-name="question" data-value="'+ value.id + '"' +
								'><div class="panel panel-success"><div class="panel-heading">';
							questionNo++;
							html += 'Question ' + questionNo;
							html += '<p class="text-center">' + value.text + '</p>' + '</div>';
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
									'<input type="' + answerType + '" name="group' + value.id + 
									'"></input></label>';
								html += '<span>' + v.text + '</span></p>'
							});
							html += '</div></div>';
							html +='</div>';
							html += '<div class="col-sm-3" data-name="alertNoAnswer" data-value="'+ value.id +'" style="display:none">'+
							'<div class="alert alert-danger">Please select an aswer for this question.</div></div>';
							html += '</div>';
						});
		html += '<div class="row" >';
		html += '<div class="col-sm-9" id="buttons">';
		html += '<button class="btn btn-success" id="submitQuiz">Submit quiz</button> ';
		html += '<button class="btn btn-default" id="cancelQuiz">Cancel</button>';
		html +='</div></div>';
		$el.html(html);
		QuizTaker.submitQuiz();
		QuizTaker.cancelQuiz();
	},
	
	submitQuiz: function(){
		$("#submitQuiz").on('click', function(){
			var isOk = true;
			var quizResponse = new Object();
			var quiz = new Object();
			quiz.id = QuizTaker.id;
			quizResponse.quiz = quiz;
			var questionAnswers = [];
			$.each($('[data-name="question"]'), function(key, value){
				var questionId= $(this).attr('data-value');
				var checked = $(this).find('input:checked');
				var allAnswers = $(this).find('input');
				var selectedAnswers = QuizTaker.getAnswerNo(checked, allAnswers);
				if(selectedAnswers.length == 0){
					$(this).parent().find($('[data-name="alertNoAnswer"]')).show('slow');
					isOk = false;
				}
				else{
					$(this).parent().find($('[data-name="alertNoAnswer"]')).hide('slow');
				}
				var questionAnswer = new Object();
				questionAnswer.id = questionId;
				questionAnswer.answers = selectedAnswers;
				questionAnswers.push(questionAnswer);
			});
			quizResponse.questionAnswers = questionAnswers;
			console.log(JSON.stringify(quizResponse));
			if(isOk){
				$.loader({
					className : "blue-with-image",
					content : 'Checking your answers'
				});
				$.ajax({
                    type: "POST",
                    url: 'quizAnswer',
                    data: JSON.stringify(quizResponse),
                    contentType: "application/json",
                    success: function (data) {
                        console.log(data);
                        $("#mainContent").prepend('<div class="row" id="quizResult">Your result is ' + data.score*100 + '%. Congratulations!</div>')
                        $("#mainContent").find($("#buttons")).empty();
                        $("#mainContent").find($("#buttons")).append('<button class="btn btn-success" id="retakeQuiz">Retake quiz</button> ');
                        QuizTaker.retakeQuiz();
                        $.loader('close');
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        console.log(xhr.status);
                        console.log(thrownError);
                        $.loader('close');
                    }
                });
			}
		});
	},
	
	cancelQuiz: function(){
		$("#cancelQuiz").on('click', function(){
			QuizsDisplay.init();
		});
	},
	
	getAnswerNo: function(checked, allAnswers){
		var selectedAnswers = [];
		$.each(checked, function(key, value){
			var answerNo = -1;
			$.each(allAnswers, function(k, v){
				answerNo++;
				if(value == v){
					selectedAnswers.push(answerNo);
				}
			});	
		});
		return selectedAnswers;
		
	},
	
	retakeQuiz: function(){
		$("#retakeQuiz").on('click', function(){
			QuizTaker.init(QuizTaker.id);
		});
	},
	
	
}
