var QuizCreator = {
		
		init : function(){
			var template = $("#tpl_createQuiz").html();
			$("#mainContent").html(_.template(template));
			this.chooseCategory();
		},
		
		chooseCategory : function(){
			$("#quizCategorySelected").on('change', function(){
				console.log("aleg categoria");
			});
		}
		
		
}