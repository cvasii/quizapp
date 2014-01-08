var QuizDetailsEditPanel = {

    init: function ($el, quizData) {
    	var templateQuizDetail = $("#tpl_quizDetailsEditPanel").html();
		$($el).html(_.template(templateQuizDetail));
		
        $("#quizDataHeading").append(
            '<h3 class="panel-title" id="quizDataName" data-quizId="'
                + quizData.id + '">' + quizData.name + '</h3>');

        $("#quizDataBody").append('<p id="editCategories">Categories: </p>');

        $("#quizDataBody").append('<div id="quizDataCategories"></div>');
        var html = '';
        $(quizData.categories).each(
            function (key, value) {
                html += '<li data-quizCategoryId="' + value.id + '">'
                    + value.name + '</li>';
            });
        
        var $elCategories = $('#quizDataCategories');
        if (html)
            html = '<ul>' + html + '</ul>';
        $elCategories.html(html);

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
            QuizDetailsEditPanel.editPassword();
        } else {
            $("#quizDataBody").append(
                '<p id="quizDataPassword" data-value=' + quizData.password
                    + '>None</p></div>');
        }

        this.editQuizName();
        this.editIsPrivate();

        var sourceData = [];
        $.ajax({
            url: '/quizcategory',
            type: 'GET',
            success: function (data) {
                $(data).each(function (key, value) {
                    var entry = {};
                    entry.value = value.id;
                    entry.text = value.name;
                    sourceData.push(entry);
                });
                QuizDetailsEditPanel.editQuizCategories(sourceData);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(xhr.status);
                console.log(thrownError);
            }
        });

        this.saveQuiz();
    },

    editQuizName: function () {
        $("#quizDataName").editable({
            type: 'text',
            value: $("#quizDataName").html(),
            placement: 'bottom',
            validate: function (value) {
                if ($.trim(value) == '')
                    return 'This field is required';
            },
            title: 'Enter quiz name',
            success: function () {
             
            }
        });
    },

    editIsPrivate: function () {
        var isPrivate = $("#quizDataIsPrivate").attr('data-quizIsPrivate');
        var currentValue;
        if (isPrivate == "true") {
            currentValue = 1;
        } else {
            currentValue = 0;
        }
        $("#quizDataIsPrivate").editable({
            type: 'select',
            value: currentValue,
            source: [
                {
                    value: 0,
                    text: 'Public'
                },
                {
                    value: 1,
                    text: 'Private'
                }
            ],
            placement: 'bottom',
            success: function (response, newValue) {
                if (newValue == 0) {
                    $("#quizDataIsPrivate").attr('data-quizIsPrivate', false);
                    $('#quizDataPassword').editable('destroy');
                    $('#quizDataPassword').html('None');
                    $('#quizDataPassword').attr('data-value', '');
                } else if (newValue == 1) {
                    $("#quizDataIsPrivate").attr('data-quizIsPrivate', true);
                    QuizDetailsEditPanel.editPassword();
                }
            }
        });
    },

    editQuizCategories: function (sourceData) {

        var selectedCategories = [];
        $("#quizDataCategories li").each(function (key, value) {
            selectedCategories.push($(value).attr('data-quizCategoryId'));
        });

        $("#quizDataCategories").editable(
            {
                type: 'checklist',
                title: 'Select categories',
                source: sourceData,
                value: selectedCategories,
                display: function (value, sourceData) {
                    var $el = $('#quizDataCategories'), checked, html = '';
                    if (!value) {
                        $el.empty();
                        return;
                    }
                    checked = $.grep(sourceData, function (o) {
                        return $.grep(value,function (v) {
                            return v == o.value;
                        }).length;
                    });

                    $.each(checked, function (i, v) {
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
                validate: function (value) {
                    if (value.length == 0)
                        return 'Please select at least one category';
                },
                placement: 'bottom',
                success: function () {
                    
                }

            });
    },

    editPassword: function () {
        $("#quizDataPassword")
            .editable(
            {
                type: 'password',
                title: 'Change password',
                placement: 'bottom',
                value: $('#quizDataPassword').attr('data-value'),
                success: function (response, newValue) {
                    $('#quizDataPassword').attr('data-value',
                        newValue);
                },
                display: function (value, sourceData) {
                    var $el = $('#quizDataPassword'), html = '';
                    if (!value) {
                        $el.empty();
                        return;
                    }
                    $("#quizDataPassword")
                        .attr('data-value', value);
                    html = '********';
                    $el.html(html);
                },
                validate: function (value) {
                    if (value.length < 6)
                        return 'Please enter the password, minimum 6 characters.';
                }
            });
    },

    saveQuiz: function () {
        $("#saveQuiz").on('click', function (event) {
            event.preventDefault();
            var isOk = true;
            var quizName = $("#quizDataName").html();
            var quizId = $("#quizDataName").attr('data-quizId');
            var isPrivate = $("#quizDataIsPrivate").attr('data-quizIsPrivate');
            var password = $("#quizDataPassword").attr('data-value');

            var categories = [];
            $.each($('#quizDataCategories li'), function (key, value) {
                categories.push($(value).attr('data-quizCategoryId'));
            });

            if (isPrivate == "true" && password == '') {
                $("#alertQuizNoPassword").show('slow');
                isOk = false;
            }
            else {
                $("#alertQuizNoPassword").hide('slow');
            }

            if (isPrivate == "true") {
                isPrivate = true;
            }
            else {
                isPrivate = false;
            }

            if (isOk) {
                $.loader({
                    className: "blue-with-image",
                    content: ''
                });
                var requestUrl = 'question/quiz/' + quizId;
                $.ajax({
                    type: "GET",
                    url: requestUrl,
                    contentType: "application/json",
                    success: function (data) {
                        console.log(data);
                        if (data.length == 0) {
                            $("#alertQuizNoQuestions").show('slow');
                            $.loader('close');
                        }
                        else {
                            $("#alertQuizNoQuestions").hide('slow');
                            var quiz = new Object();
                            quiz.name = quizName;
                            quiz.categories = categories;
                            quiz.id = quizId;
                            quiz.isPrivate = isPrivate;
                            quiz.password = password;
                            quiz = JSON.stringify(quiz);
                            var url = '/quiz/' + quizId;
                            $.ajax({
                                type: "PUT",
                                url: url,
                                data: quiz,
                                contentType: "application/json",
                                success: function (data) {
                                    console.log(data);
                                    $.loader('close');
                                },
                                error: function (xhr, ajaxOptions, thrownError) {
                                    console.log(xhr.status);
                                    console.log(thrownError);
                                    $.loader('close');
                                }
                            });
                        }

                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        console.log(xhr.status);
                        console.log(thrownError);
                        $.loader('close');
                    }
                });
            }
        });
    }

}