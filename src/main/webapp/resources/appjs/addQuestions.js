var QuestionAdditioner = {

	init : function(quizData) {

		var template = $("#tpl_addQuestions").html();
		$("#mainContent").html(_.template(template));

		$("#quizDataHeading").append(
				'<h3 class="panel-title" id="quizDataName" data-quizId="'
						+ quizData.id + '">' + quizData.name + '</h3>');

		$("#quizDataBody").append('<p id="editCategories">Categories: </p>');

		$("#quizDataBody").append('<div id="quizDataCategories"></div>');
		var html = '';
		$(quizData.categories).each(
				function(key, value) {
					html += '<li data-quizCategoryId="' + value.id + '">'
							+ value.name + '</li>';
				});
		var $el = $('#quizDataCategories');
		if (html)
			html = '<ul>' + html + '</ul>';
		$el.html(html);

		$("#quizDataBody").append('<br/>');
		if (quizData.isPrivate) {
			$("#quizDataBody").append(
					'<p id="quizDataIsPrivate" data-quizIsPrivate="'
							+ quizData.isPrivate + '">Private</p>');
		} else {
			$("#quizDataBody").append(
					'<p id="quizDataIsPrivate" data-quizIsPrivate="'
							+ quizData.isPrivate + '">Public</p>');
		}
		
		$("#quizDataBody").append('<br/>');
		$("#quizDataBody").append('<p>Password</p>');
		if (quizData.isPrivate) {
			$("#quizDataBody").append(
					'<p id="quizDataPassword" data-value=' + quizData.password
							+ '>********</p></div>');
			QuestionAdditioner.editPassword();
		}
		else{
			$("#quizDataBody").append(
					'<p id="quizDataPassword" data-value=' + quizData.password
							+ '>None</p></div>');
		}

		$.loader('close');

		this.addQuestion();
		this.addAnswer();
		this.editQuizName();
		this.editIsPrivate();

		var sourceData = [];
		$.ajax({
			url : '/quizcategory',
			type : 'GET',
			success : function(data) {
				$(data).each(function(key, value) {
					var entry = {};
					entry.value = value.id;
					entry.text = value.name;
					sourceData.push(entry);
				});
				QuestionAdditioner.editQuizCategories(sourceData);
			},
			error : function(xhr, ajaxOptions, thrownError) {
				console.log(xhr.status);
				console.log(thrownError);
			}
		});
	},

	editQuizName : function() {
		$("#quizDataName").editable({
			type : 'text',
			value : $("#quizDataName").html(),
			placement : 'bottom',
			validate : function(value) {
				if ($.trim(value) == '')
					return 'This field is required';
			},
			title : 'Enter quiz name',
			success : function() {
				console.log("ToDo save quiz name")
			}
		});
	},

	editIsPrivate : function() {
		var isPrivate = $("#quizDataIsPrivate").attr('data-quizIsPrivate');
		var currentValue;
		if (isPrivate == "true") {
			currentValue = 1;
		} else {
			currentValue = 0;
		}
		$("#quizDataIsPrivate").editable({
			type : 'select',
			value : currentValue,
			source : [ {
				value : 0,
				text : 'Public'
			}, {
				value : 1,
				text : 'Private'
			} ],
			placement : 'bottom',
			success : function(response, newValue) {
				if (newValue == 0) {
					$("#quizDataIsPrivate").attr('data-quizIsPrivate', false);
					$('#quizDataPassword').editable('destroy');
					$('#quizDataPassword').html('None');
					$('#quizDataPassword').attr('data-value', '');
				} else if (newValue == 1) {
					$("#quizDataIsPrivate").attr('data-quizIsPrivate', true);
					QuestionAdditioner.editPassword();
				}
				console.log("ToDo save isPrivate");
			}
		});
	},

	editQuizCategories : function(sourceData) {

		var selectedCategories = [];
		$("#quizDataCategories li").each(function(key, value) {
			selectedCategories.push($(value).attr('data-quizCategoryId'));
		});

		$("#quizDataCategories").editable(
				{
					type : 'checklist',
					title : 'Select categories',
					source : sourceData,
					value : selectedCategories,
					display : function(value, sourceData) {
						var $el = $('#quizDataCategories'), checked, html = '';
						if (!value) {
							$el.empty();
							return;
						}
						checked = $.grep(sourceData, function(o) {
							return $.grep(value, function(v) {
								return v == o.value;
							}).length;
						});

						$.each(checked, function(i, v) {
							html += '<li data-quizCategoryId="' + v.value
									+ '">' + $.fn.editableutils.escape(v.text)
									+ '</li>';
						});
						if ($("#quizDataCategories")
								.attr('data-quizCategoryId') != undefined) {
							html += $("#quizDataCategories li").html();
						}
						if (html)
							html = '<ul>' + html + '</ul>';
						$el.html(html);
					},
					validate : function(value) {
						if (value.length == 0)
							return 'Please select at least one category';
					},
					placement : 'bottom',
					success : function() {
						console.log("ToDo save quiz categories")
					}

				});
	},

	editPassword : function() {
		$("#quizDataPassword").editable({
			type : 'password',
			title : 'Change password',
			placement : 'bottom',
			value : $('#quizDataPassword').attr('data-value'),
			success : function(response, newValue){
				console.log('ToDo save password and verify at end is a password exists');
				$('#quizDataPassword').attr('data-value', newValue);
			},
			display : function(value, sourceData){
				var $el = $('#quizDataPassword'), html = '';
				if (!value) {
					$el.empty();
					return;
				}
				$("#quizDataPassword").attr('data-value', value);
				html = '********';
				$el.html(html);
			},
			validate : function(value) {
				if (value.length < 6)
					return 'Please enter the password, minimum 6 characters.';
			}
		});
	},

	addQuestion : function() {
		$("#addQuestion").on('click', function(event) {
			event.preventDefault();
			$("#questionText").editable({
				type : 'textarea',
				value : 'Question',
				placement : 'right',
				title : 'Add question',
				inputclass : 'form-control',
				placeholder : 'Question'
			});
			$('#questionCategory').editable({
				type : 'select',
				value : 0,
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
				title : 'Select question type'
			});
			$("#addQuestion").hide('slow');
			$("#questionForm").show('slow');

		});
	},

	addAnswer : function() {
		$("#addAnswerBtn").on('click', function(event) {
			event.preventDefault();
		});
	}

}