var QuestionAdditioner = {

	init : function(quizData) {

		var template = $("#tpl_addQuestions").html();
		$("#mainContent").html(_.template(template));
		var templateQuizDetail = $("#tpl_quizDetailsEditPanel").html();
		$("#addQuestions").html(_.template(templateQuizDetail));
		$("#addQuestions").append('<div class="col-sm-9" id="addQuestionDiv"></div>');
		QuizDetailsEditPanel.init(quizData);

		$("#addQuestionDiv")
				.html(
						'<div><button class="btn btn-success" id="addQuestion">Add a new question</button></div>');
		
		this.preventForm();
		this.addQuestion();
		
		$.loader('close');
	},

	
	addQuestion : function() {
		$("#addQuestion").on('click', function(event) {
			event.preventDefault();

			var template = $("#tpl_addQuestion").html();
			$("#addQuestionDiv").html(_.template(template));

			QuestionAdditioner.saveQuestion();
			QuestionAdditioner.editAnswer();
			QuestionAdditioner.addAnswer();

			$('#questionCategory').editable({
				type : 'select',
				value : 0,
				defaultValue : '',
				source : [ {
					value : 0,
					text : 'None selected'
				}, {
					value : 1,
					text : 'Single choice'
				}, {
					value : 2,
					text : 'Multiple choice'
				} ],
				placement : 'right',
				title : 'Select question type',
				display : function(value, sourceData) {
					var $el = $('#questionCategory'), html = '';
					if (!value) {
						$el.empty();
						return;
					}
					$el.attr('data-value', value);

					$.each(sourceData, function(i, v) {
						if (v.value == $el.attr('data-value')) {
							html += $.fn.editableutils.escape(v.text);
						}
					});
					$el.html(html);
				},
				validate : function(value) {
					if (value == 0)
						return 'Please select a category.';
				}
			});

			$("#questionText").editable({
				defaultValue : '',
				type : 'textarea',
				placement : 'right',
				title : 'Add question',
				placeholder : 'Question',
				value : '',
				validate : function(value) {
					if (value.length == 0)
						return 'Please enter the question.';
				}
			});

		});
	},

	addAnswer : function() {
		$("#addAnswer")
				.on(
						'click',
						function(event) {
							event.preventDefault();
							var answerNo = $(
									$('.form-group label')[$('.form-group label').length - 2])
									.html()[$(
									$('.form-group label')[$('.form-group label').length - 2])
									.html().length - 1]++ + 1;
							$("#questionForm")
									.append(
											'<div class="form-group" data-name="answerDiv"><label class="col-sm-2 control-label" > Answer '
													+ answerNo
													+ '</label ><label class="col-sm-2 control-label"><input type="checkbox" data-name="questionCorrectAnswer"> Is correct</input></label><div class="col-sm-4" ><p data-name="questionAnswer"></p></div ><span class="glyphicon glyphicon-remove"></div>')
							$('[data-name="questionAnswer"]').editable(
									'destroy');
							QuestionAdditioner.editAnswer();
							QuestionAdditioner.removeAnswer();
						});
	},

	editAnswer : function() {
		$('[data-name="questionAnswer"]').editable({
			type : 'text',
			placement : 'right',
			defaultValue : '',
			title : 'Add answer',
			placeholder : 'Question',
			value : '',
			validate : function(value) {
				if (value.length == 0)
					return 'Please enter the answer.';
			}
		});
	},

	removeAnswer : function() {
		$(".glyphicon").on('click', function(event) {
			event.preventDefault();
			$($(this).parent().parent()).find($($(this).parent())).remove();
			console.log("remove answer");
			QuestionAdditioner.recalculateAnswerNos();
		});
	},

	recalculateAnswerNos : function() {
		var noAnswer = 0;
		$.each($('.form-group[data-name="answerDiv"]'), function(key, value) {
			noAnswer++;
			$($(value).find('label').first()).html('Answer ' + noAnswer);
		});
	},

	saveQuestion : function() {
		$("#saveQuestion")
				.on(
						'click',
						function(event) {
							event.preventDefault();
							var isOk = true;

							if ($("#questionCategory").attr('data-value') == undefined) {
								$("#alertQuestionCategory").show();
								$("#alertQuestionCategory").css('display',
										'inline');
								isOk = false;
							} else {
								$("#alertQuestionCategory").hide();
								$("#alertQuestionCategory").css('display',
										'none');
							}

							var questionText = '';
							if ($("#questionText").html() == 'Empty') {
								$("#alertQuestionText").show();
								$("#alertQuestionText")
										.css('display', 'inline');
								isOk = false;
							} else {
								$("#alertQuestionText").hide();
								$("#alertQuestionText").css('display', 'none');
								questionText = $.fn.editableutils.escape($(
										"#questionText").html());
							}

							var answersOk = true;
							$.each($('[data-name="questionAnswer"]'), function(
									key, value) {
								if ($(value).html() == 'Empty') {
									answersOk = false;
									$("#alertQuestionAnswers").show();
									$("#alertQuestionAnswers").css('display',
											'inline');
								}
							});
							if (answersOk) {
								$("#alertQuestionAnswers").hide();
								$("#alertQuestionAnswers").css('display',
										'none');
							} else {
								isOk = false;
							}

							var noCorrectAnswers = 0;
							$.each($('[data-name="questionCorrectAnswer"]'),
									function(key, value) {
										if ($(value).prop('checked')) {
											noCorrectAnswers++;
										}
									});

							$("#alertQuestionNoAnswersSingle").css('display',
									'none');
							$("#alertQuestionNoAnswersMultiple").css('display',
									'none');
							var questionType = $("#questionCategory").attr(
									'data-value');
							if (questionType != undefined) {
								if (questionType == "1") {
									if (noCorrectAnswers != 1) {
										$("alertQuestionNoAnswersSingle")
												.show();
										$("#alertQuestionNoAnswersSingle").css(
												'display', 'inline');
										isOk = false;
									} else {
										$("alertQuestionNoAnswersSingle")
												.hide();
										$("#alertQuestionNoAnswersSingle").css(
												'display', 'none');
									}
								}
								if (questionType == "2") {
									if (noCorrectAnswers < 1) {
										$("alertQuestionNoAnswersMultiple")
												.show();
										$("#alertQuestionNoAnswersMultiple")
												.css('display', 'inline');
										isOk = false;
									} else {
										$("alertQuestionNoAnswersMultiple")
												.hide();
										$("#alertQuestionNoAnswersMultiple")
												.css('display', 'none');
									}
								}
							}
							if (isOk) {
								$.loader({
									className : "blue-with-image",
									content : ''
								});
								var answers = [];
								$
										.each(
												$('[data-name="answerDiv"]'),
												function(key, value) {
													var answer = {};
													answer.isCorrect = $(
															$(value)
																	.find(
																			'[data-name="questionCorrectAnswer"]'))
															.prop('checked');
													answer.text = $(
															$(value)
																	.find(
																			'[data-name="questionAnswer"]'))
															.html();
													answers.push(answer);
												});
								var quizId = $("#quizDataName").attr(
										'data-quizId');
								var question = {
									type : questionType,
									text : questionText,
									answers : answers,
									quizId : quizId
								};
								$.ajax({
									type : "POST",
									url : "/question",
									data : JSON.stringify(question),
									contentType : "application/json",
									success : function(data) {
										console.log(data);
										$("#addQuestionDiv")
										.html(
												'<div><button class="btn btn-success" id="addQuestion">Add a new question</button></div>');
										QuestionAdditioner.addQuestion();
										$.loader('close');
										
									},
									error : function(xhr, ajaxOptions,
											thrownError) {
										console.log(xhr.status);
										console.log(thrownError);
										$.loader('close');
									}
								});
							}
						});
	},
	
	preventForm : function() {
		$("button").on('click', function(event) {
			event.preventDefault();
		});
	}
}