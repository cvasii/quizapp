var QuizCreator = {
		
		init : function(){
			
			var template = $("#tpl_createQuiz").html();
			$("#mainContent").html(_.template(template));
			
			//TODO ajax request to fetch categories and add them to the select component
			//and when success call the line below
			$("#quizCategorySelected").multiselect();
			
			this.tooglePrivate();
			this.submit();
			
			
		},
		
		tooglePrivate : function(){
			$("#quizPrivateCheckbox").on('change', function(){
				var isChecked = $("#quizPrivateCheckbox").prop('checked');
				if(isChecked){
					$("#quizPasswordDiv").show('slow');
				}
				else{
					$("#quizPasswordDiv").hide('slow');
				}
			});
		},
		
		submit : function(){
			$("#createQuizBtn").on('click', function(){
				
				var isOk = true;
				
				var quizName = $("#quizName").val();
				if(quizName === ""){
					$("#alertQuizName").show('slow');
					isOk = false;
				}
				else{
					$("#alertQuizName").hide();
				}
				
				var isChecked = $("#quizPrivateCheckbox").prop('checked');
				var password = $("#quizPassword").val();
				if(isChecked){
					if(password === ""){
						$("#alertQuizPassword").show('slow');
						isOk = false;
					}
					else{
						$("#alertQuizPassword").hide();
					}
				}
				
				var categoriesOptions = $("#quizCategorySelected option:checked");
				if(categoriesOptions.length == 0){
					$("#alertQuizCategory").show('slow');
					isOk = false;
				}
				else{
					$("#alertQuizCategory").hide();
				}
				var categories = [];
				$(categoriesOptions).each(function(key, value){
					categories.push($(value).val());
				});
				
				if(isOk){
					//TODO ajax send data to server, when result success delete content of the page
					//and load the new template. The server should return the new quiz data created with
					//id, name, categories etc
					QuestionAdditioner.init();
				}
				console.log("Quiz name: " + quizName);
				console.log("Quiz private: " + isChecked);
				console.log("Quiz password: " + password);
				console.log("Categories: " + categories);
			});
		}
		
}