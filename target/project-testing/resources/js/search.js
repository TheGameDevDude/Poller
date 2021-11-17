jQuery(document).ready(function($){
    $("#search_button").click(function() {
        var str = "";
        $.ajax({
            url : 'get_search/'+$("#searchbox").val()+'/'+$("#get_user_id").val()+'/'+$("#get_username").val(),
            success : function(data) {
                console.log(data);
                for(var i = 0; i < data.length; i++) {
                    str += "<tr><td>";
                    str += "<form action='/project-testing/polling' method='post'>";
                    str += "<input hidden type='text' name='user_id' value=" + data[i].userId + " id='get_user_id'>";
                    str += "<input hidden type='text' name='get_username' value=" + data[i].username + " id='get_username'>";
                    str += "<input hidden type='text' name='question_id' value=" + data[i].questionId + ">";
                    str += "<button class='btn btn-outline-dark btn-lg'>";
                    str += "<img src='https://img.icons8.com/office/16/00str 0000/poll-topic.png'/>";
                    str += data[i].question;
                    str += "</button>"
                    str += "</form>"
                    str += "</tr></td>";
                }
                document.getElementById("searchable").innerHTML = str;
            }
        });
    });
});