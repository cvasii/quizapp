/**
 * Created by cvasii on 1/8/14.
 */
var QuizsDisplay = {

    init: function () {
        $.loader({
            className: "blue-with-image",
            content: ''
        });
        var template = $("#tpl_quizsDisplayTable").html();
        $("#mainContent").html(_.template(template));

        $.ajax({
            type: "GET",
            url: "/quizs",
            contentType: "application/json",
            success: function (data) {
                console.log(JSON.stringify(data));
                $.each(data, function (key, value) {
                    var html = '<tr>';
                    html += '<td>' + value.name + '</td><td><ul>';
                    $.each(value.categories, function (k, v) {
                        html += '<li>' + v.name + '</li>';
                    });
                    html += '</ul></td>';
                    if (value.isPrivate) {
                        html += '<td>Yes</td>';
                    } else {
                        html += '<td>No</td>';
                    }
                    html += '<td>' + value.user.nickname + '</td>';
                    html += '<td>' + value.dateCreated + '</td>';
                    html += '<td>' + value.noQuestions + '</td>';
                    html += '<td><button class="btn btn-success btn-sm" data-name="takeQuizBtn" data-value="' + value.id + '">Take quiz</button></td>';
                    html += '</tr>';
                    $("tbody").append(html);
                });
                QuizsDisplay.takeQuiz();
                $.loader('close');

            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(xhr.status);
                console.log(thrownError);
                $.loader('close');
            }
        });


    },

    takeQuiz: function () {
        $('[data-name="takeQuizBtn"]').on('click', function () {
            var $td;
            var $el = $($(this).parent().parent().children());
            for (var i = $el.length; i >= 0; --i) {
                if (i == 2) {
                    $td = $($el[i]);
                }
            }
            var quizId = $(this).attr('data-value');
            if ($($td).html() == "Yes") {
                $("#passwordModal").modal('show');
                $("#quizPassword").attr('data-value', quizId);
                $("#submitPassword").on('click', function(e){
                    var url = '/quiz/' + quizId + '/password';
                    
                    $.ajax({
                        type: "POST",
                        url: url,
                        data: $("#quizPassword").val(),
                        contentType: "application/json",
                        success: function (data) {
                            console.log(data);
                            if(data == false){
                            	$("#alertWrongPassword").show('slow');
                            }
                            else{
                            	$("#alertWrongPassword").hide('slow');
                            	$("#passwordModal").modal('hide');
                            	$('#passwordModal').on('hidden.bs.modal', function (e) {
                                	QuizTaker.init(quizId);
                            	});
                            }
                            $.loader('close');
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                            console.log(xhr.status);
                            console.log(thrownError);
                            $.loader('close');
                        }
                    });
                });
            }
            else{
                $("#passwordModal").modal('hide');
            	QuizTaker.init(quizId);
            }

        });
    }
}