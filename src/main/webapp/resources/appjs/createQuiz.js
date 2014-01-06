var QuizCreator = {

	init : function() {

		var template = $("#tpl_createQuiz").html();
		$("#mainContent").html(_.template(template));

		$.loader({
			className : "blue-with-image",
			content : ''
		});
		$.ajax({
			url : '/quizcategory',
			type : 'GET',
			success : function(data) {
				$(data).each(
						function(key, value) {
							$("#quizCategorySelected").append(
									'<option value="' + value.id + '">'
											+ value.name + '</option>');
						});
				$("#quizCategorySelected").multiselect();
				$.loader('close');
			},
			error : function(xhr, ajaxOptions, thrownError) {
				console.log(xhr.status);
				console.log(thrownError);
			}
		});

		this.tooglePrivate();
		this.submit();

	},

	tooglePrivate : function() {
		$("#quizPrivateCheckbox").on('change', function() {
			var isChecked = $("#quizPrivateCheckbox").prop('checked');
			if (isChecked) {
				$("#quizPasswordDiv").show('slow');
			} else {
				$("#quizPasswordDiv").hide('slow');
			}
		});
	},

	submit : function() {
		$("#createQuizBtn").on('click', function(event) {
			event.preventDefault();
			var isOk = true;

			var quizName = $("#quizName").val();
			if (quizName === "") {
				$("#alertQuizName").show('slow');
				isOk = false;
			} else {
				$("#alertQuizName").hide();
			}

			var isChecked = $("#quizPrivateCheckbox").prop('checked');
			var password = $("#quizPassword").val();
			if (isChecked) {
				if (password.length < 6) {
					$("#alertQuizPassword").show('slow');
					isOk = false;
				} else {
					$("#alertQuizPassword").hide();
				}
			}

			var categoriesOptions = $("#quizCategorySelected option:checked");
			if (categoriesOptions.length == 0) {
				$("#alertQuizCategory").show('slow');
				isOk = false;
			} else {
				$("#alertQuizCategory").hide();
			}
			var categories = [];
			$(categoriesOptions).each(function(key, value) {
				categories.push($(value).val());
			});

			if (isOk) {
				$.loader({
					className : "blue-with-image",
					content : ''
				});
				var quiz = new Object();
				quiz.name = quizName;
				quiz.categories = categories;
				quiz.isPrivate = isChecked;
				quiz.password = password;
				quiz = JSON.stringify(quiz);
				$.ajax({
					type : "POST",
					url : "/quiz",
					data : quiz,
					contentType : "application/json",
					success : function(data) {
						console.log(data);
						QuestionAdditioner.init(data);
					},
					error : function(xhr, ajaxOptions, thrownError) {
						console.log(xhr.status);
						console.log(thrownError);
						$.loader('close');
					}
				});
			}
		});
	}

}