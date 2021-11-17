<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style>
    .progress {
        display: flex;
        height: 3em;
        overflow: hidden;
        font-size: .75rem;
        background-color: #e9ecef;
        border-radius: 0.25rem;
    }

    ul {
      list-style-type: none;
    }
    </style>
</head>
<body>
    <br>
    <div class="container px-4">
        <div class="row gx-5">
          <div class="col">
            <div class="p-3 border bg-light">
                <h2 style="text-align: center;">${question.question}</h2>
                <c:forEach var="i" begin="0" end="3">
                    <form action="/project-testing/result" method="post">
                        <input hidden type="text" name="user_id" value="${user_id}" id="get_user_id">
                        <input hidden type="text" name="get_username" value=${username} id="get_username_from_name">
                        <input hidden type="text" name="question_id" value="${question.question_id}" id="get_question_id">
                        <input hidden type="text" name="option_id" value="${options[i].option_id}">
                        <div class="progress" ${set_hidden}>
                            <div class="progress-bar w-${progress[i]} ${progress_colors[i]}" role="progressbar" aria-valuenow="${progress[i]}" aria-valuemin="0" aria-valuemax="100" style="font-size: large;">${options[i].value} - ${progress[i]}%</div>
                        </div>
                        <button class="btn btn-outline-dark btn-lg" ${set_disabled}><c:out value="${options[i].value}"/></button><br>
                    </form>
                </c:forEach>
                <br>
                <div class="container px-4">
                  <div class="row gx-5">
                    <div class="col">
                     <div class="p-3 bg-light">
                       <h3><img src="https://img.icons8.com/external-parzival-1997-flat-parzival-1997/64/000000/external-vote-political-revolution-parzival-1997-flat-parzival-1997.png"/>   ${votes}</h3>
                     </div>
                    </div>
                    <div class="col">
                      <div class="p-3 bg-light">                        
                        <form action="/project-testing/dashboard" method="post">
                            <input hidden type="text" name="user_id" value="${user_id}">
                            <input hidden type="text" name="username" value="${username}"> 
                            <input hidden type="text" name="question_id" value="${question.question_id}">
                            <input type="submit" class="btn btn-secondary btn-lg" value="Dashboard">
                          </form>
                      </div>
                    </div>
                  </div>
                </div> 
            </div>
          </div>
          <div class="col">
            <div class="p-3 border bg-light">
                <div class="input-group mb-3">
                  <input type="text" class="form-control" placeholder="Comment" aria-label="Recipient's username" aria-describedby="button-addon2" name="comment" id="commentbox">
                  <div class="input-group-append">
                    <input class="btn btn-outline-secondary" type="submit" id="comment_button" value="Post"/>
                  </div>
                </div>
                <div id="comment_section">

                </div>
            </div>
          </div>
        </div>
      </div>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
      <script src="<c:url value="/resources/js/scripts.js" />"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>