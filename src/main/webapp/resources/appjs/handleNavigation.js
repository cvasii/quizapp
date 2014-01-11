var NavigationHandler = {

	init : function() {
		this.bindUIActions();
	},

	bindUIActions : function() {
		$('a').on(
				'click',
				function(event) {
					event.preventDefault();
					if ($(event.target).attr('href') !== 'javascript:void(0)'
							&& $(event.target).attr('href') !== null
							&& $(event.target).attr('href') !== undefined
							&& $(event.target).attr('href') !== '') {
						$('#mainContent').empty();

						window.location.hash = $(event.target).attr('href');

						var dataName = $(event.target).attr('data-name');
						if (dataName === "home") {
							var template = $("#tpl_jumbotron").html();
							$("#mainContent").html(_.template(template));
						}
						if (dataName === "createQuiz") {
							QuizCreator.init();
						}
                        if(dataName === "allQuizs"){
                            QuizsDisplay.init();
                        }
                        if(dataName === "myResults"){
                            MyResultsDisplay.init();
                        }
					}

				});
	}
};