<html>
<head>
<title>Quiz App</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="cosmin vasii">
<link rel="shortcut icon" href="resources/bootstrap/ico/favicon.png">
<!-- Just for debugging purposes. Don't actually copy this line! -->
<!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

<!-- jquery -->
<script src="resources/jquery/jquery-1.10.2.min.js"></script>
<!-- bootstrap -->
<script src="resources/bootstrap/js/bootstrap.js"></script>
<link href='resources/bootstrap/css/bootstrap.css' rel='stylesheet'>
<link href='resources/bootstrap3-editable/css/bootstrap-editable.css' rel='stylesheet'>
<script src="resources/bootstrap3-editable/js/bootstrap-editable.js"></script>
<link href='resources/bootstrap/css/navbar-fixed-top.css'
	rel='stylesheet'>
<link rel="stylesheet"
	href="resources/bootstrap-multiselect/css/bootstrap-multiselect.css"
	type="text/css">
<link rel="stylesheet"
	href="resources/bootstrap-multiselect/css/prettify.css" type="text/css">

<!-- Underscore -->
<script src="resources/underscore/underscore-min.js"></script>

<link href="resources/jquery-mask/jquery.loader.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="resources/jquery-mask/jquery.loader.js"></script>
<!-- Templates -->
<script type="text/html" id="tpl_jumbotron">
<div class="jumbotron">
	<h1>Welcome to Quiz App</h1>
	<p>Start designing your own quizs, resolve quizs proposed by
		others, share your ideas, test your friends or your students.</p>
	<p>Start having fun right now.</p>
</div>
</script>

<script type="text/html" id="tpl_createQuiz">
<h3>Create a new Quiz</h3>
<div class="row" id="chooseQuizCategory">
	<form class="form-horizontal" role="form">
		<div class="form-group">
			<label for="quizName" class="col-sm-2 control-label">Name</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="quizName" placeholder="Quiz Name">
			</div>
			<div class="col-sm-3" id="alertQuizName" style="display:none">
				<div class="alert alert-danger">Please enter quiz name.</div>
			</div>
		</div>
	 
		<div class="form-group">
			<label for="quizCategorySelected" class="col-sm-2 control-label">Choose categories</label>
			<div class="col-sm-5">
				<select id="quizCategorySelected" multiple="multiple">

				</select>            
			</div>
			<div class="col-sm-3" id="alertQuizCategory" style="display:none">
				<div class="alert alert-danger">Please choose at least one category.</div>
			</div>
		</div>
	  
		<div class="form-group">
			<label for="quizPrivateCheckbox" class="col-sm-2 control-label">Private</label>
			<div class="col-sm-2">
				<div class="checkbox">
					<label>
						<input type="checkbox" id="quizPrivateCheckbox">
					</label>
				</div>
			</div>
		</div>
		
		<div class="form-group" style='display:none' id='quizPasswordDiv'>
			<label for="quizPassword" class="col-sm-2 control-label">Password</label>
			<div class="col-sm-5">
				<input type="password" class="form-control" id="quizPassword" placeholder="Quiz Password">
			</div>
			<div class="col-sm-3" id="alertQuizPassword" style="display:none">
				<div class="alert alert-danger">Please enter the password, minimum 6 charaters.</div>
			</div>
		</div>
	  
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-success" id="createQuizBtn">Create Quiz</button>
			</div>
		</div>
	</form>
	  
</div>
</script>

<script type="text/html" id="tpl_addQuestions">
<h3>Add questions</h3>
<div class="row" id="addQuestions">
	<div class="col-sm-3" id="quizData">
		<div class="panel panel-success">
  			<div class="panel-heading" id="quizDataHeading">
  			</div>
  			<div class="panel-body" id="quizDataBody">
  			</div>
		</div>	
	</div>
	<div class="col-sm-9">
		<button class="btn btn-success" id="addQuestion">Add a new question</button>
		<button class="btn btn-success" id="addQuestion">Finish quiz</button>
		<form class="form-horizontal" role="form"  style='display:none' id="questionForm">
			
			<div class="form-group">
				<label for="questionCategory" class="col-sm-2 control-label">Choose category</label>
				<div class="col-sm-7">
					<p id="questionCategory">
					</p>            
				</div>
				<div class="col-sm-3" id="alertQuestionCategory" style="display:none">
					<div class="alert alert-danger">Please choose a category.</div>
				</div>
			</div>
			
			<div class="form-group">
				<label for="questionText" class="col-sm-2 control-label">Question</label>
				<div class="col-sm-7">
					<p id="questionText"></p>
				</div>
				<div class="col-sm-3" id="alertQuestionText" style="display:none">
					<div class="alert alert-danger">Please enter the question.</div>
				</div>
			</div>
		 
			<div class="form-group">
				<div class="col-sm-2">
					<button class="btn btn-success btn-sm" id="addAnswerBtn">Add answer</button>
				</div>
			</div>
		</form>

	</div>
</div>
</script>

</head>
<body>
	<!-- Fixed navbar -->
	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="home" data-name="home">Quiz App</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="create" data-name="createQuiz">Create a new
							quiz</a></li>
					<li><a href="quizs" data-name="myQuizs">My own</a></li>
					<li><a href="all" data-name="allQuizs">All quizs</a></li>
					<li><a href="results" data-name="myResults">My results</a></li>
				</ul>

				<br />
				<p class="nav navbar-nav navbar-right" id="currentUser"></p>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>

	<div class="container" id="mainContent"></div>
	<!-- /container -->
</body>
</html>
<script type="text/javascript"
	src="resources/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
<script type="text/javascript"
	src="resources/bootstrap-multiselect/js/prettify.js"></script>
<script src="resources/appjs/handleNavigation.js"></script>
<script src="resources/appjs/createQuiz.js"></script>
<script src="resources/appjs/addQuestions.js"></script>
<script>
	$(document).ready(function() {

		$.loader({
			className : "blue-with-image",
			content : ''
		});

		$.fn.editable.defaults.mode = 'popup';
		
		//get logged in user to display nickname
		$.ajax({
			url : 'getCurrentUser',
			type : 'GET',
			success : function(data) {
				$("#currentUser").html('Welcome, <b>' + data + '</b>');
			}
		});

		//Load first page template
		var template = $("#tpl_jumbotron").html();
		$("#mainContent").html(_.template(template));

		NavigationHandler.init();

		$.loader('close');
	});
</script>