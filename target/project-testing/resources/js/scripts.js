jQuery(document).ready(function($){
    $("#comment_button").click(function() {
        var str = '<ul>';
        $.ajax({
            url : 'get_comment/'+$("#commentbox").val()+'/'+$("#get_question_id").val()+'/'+$('#get_username_from_name').val(),
        });
    });
});

const a = function() {
    var str = '<ul>';
    $.ajax({
        url : 'get_comment/'+$("#get_question_id").val(),
        success : function(data) {
            for(var i = 0; i < data.length; i++) {
                var comment_from_user = data[i].username + ': ' + data[i].value;
                str += '<li> <img width=25 src="https://img.icons8.com/external-icongeek26-linear-colour-icongeek26/50/000000/external-chat-essentials-icongeek26-linear-colour-icongeek26.png"/>' + ' <bold>' + comment_from_user + '</bold></li>';
            }
            str += '</ul>';
            document.getElementById("comment_section").innerHTML = str;
        }
    });
}

setInterval(() => {a()}, 1000);
