/**
 * Created by cvasii on 1/8/14.
 */
var MyResultsDisplay = {

	init : function() {
		$.loader({
			className : "blue-with-image",
			content : ''
		});
		var template = $("#tpl_myResultsDisplayTable").html();
		$("#mainContent").html(_.template(template));

		$.ajax({
			type : "GET",
			url : "/quizAnswer/my",
			contentType : "application/json",
			success : function(data) {
				console.log(JSON.stringify(data));
				$.each(data, function (key, value) {
                    var html = '<tr>';
                    html += '<td>' + value.quiz.name + '</td>';
                    html += '<td>' + value.score*100 + '%</td>';
                    html += '<td>' + value.dateTaken + '</td>';
                    html += '</tr>';
                    $("tbody").append(html);
                });
				$.loader('close');

			},
			error : function(xhr, ajaxOptions, thrownError) {
				console.log(xhr.status);
				console.log(thrownError);
				$.loader('close');
			}
		});

	}
}