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
<link href='resources/bootstrap/css/navbar-fixed-top.css'
	rel='stylesheet'>

<!-- Underscore -->
<script src="resources/underscore/underscore-min.js"></script>

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
<div class="progress">
	<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 1%;">
		<span class="sr-only">0% Complete</span>
	</div>
</div>
<div class="row" id="chooseQuizCategory">
	<div class="col-sm-3">
		<div class="panel panel-success">
            <div class="panel-heading">
              <h4 class="panel-title">Choose category</h4>
            </div>
        </div>
    </div>

	<div class="col-sm-3">
		<select id="quizCategorySelected">
			<option value="a">None</option>
			<option value="">Category1</option>
			<option value="">Category2</option>
			<option value="">Category3</option>
		</select>
    </div>    
</div>

<div class="row" id="chooseQuizType">
	<div class="col-sm-3">
		<div class="panel panel-success">
            <div class="panel-heading">
              <h3 class="panel-title">Choose quiz type</h3>
            </div>
        </div>
	</div>

	<div class="col-sm-3">
		<select id="quizTypeSelected">
			<option value="a">None</option>
			<option value="">Category1</option>
			<option value="">Category2</option>
			<option value="">Category3</option>
		</select>
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
<script src="resources/appjs/handleNavigation.js"></script>
<script src="resources/appjs/createQuiz.js"></script>
<script>
	$(document).ready(function() {

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
	});
</script>