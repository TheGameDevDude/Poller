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
</head>
<body>
    <div class="contianer shadow-sm p-3 mb-5 bg-body rounded">
        <form action="index.jsp">
            <input type="submit" value="Logout" class="btn btn-secondary btn-sm">
        </form>
        <h1 style="text-align: center;">Hi ${username}</h1>
    </div>
    <br>

    <div class="container px-4">
      <div class="row gx-5">
        <div class="col">
         <div class="p-3 border bg-light">
             <!--create poll-->
             <h3 style="text-align: center;">Create Poll</h3>
             <form action="/project-testing/createpoll" method="POST">
              <input hidden type="text" name="user_id" value=${user_id}>
              <input hidden type="text" name="username" value=${username}>
              
              <br>
              <div class="mb-3">
                  <label for="exampleFormControlTextarea1" class="form-label">Poll description</label>
                  <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="question"></textarea>
              </div>
              <br>
              <div class="mb-3">
                  <label for="exampleFormControlInput1" class="form-label">Option 1:</label>
                  <input type="text" class="form-control" id="exampleFormControlInput1" name="option1">
              </div><br>
              <div class="mb-3">
                  <label for="exampleFormControlInput1" class="form-label">Option 2:</label>
                  <input type="text" class="form-control" id="exampleFormControlInput1" name="option2">
              </div><br>
              <div class="mb-3">
                  <label for="exampleFormControlInput1" class="form-label">Option 3:</label>
                  <input type="text" class="form-control" id="exampleFormControlInput1" name="option3">
              </div><br>
              <div class="mb-3">
                  <label for="exampleFormControlInput1" class="form-label">Option 4:</label>
                  <input type="text" class="form-control" id="exampleFormControlInput1" name="option4">
              </div><br>
              <input type="submit" value="Create Poll" class="btn btn-outline-primary">
          </form>
         </div>
        </div>
        <div class="col">
          <div class="p-3 border bg-light">

            <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="Search Poll" aria-label="Recipient's username" aria-describedby="button-addon2" name="search_question" id="searchbox">
                <div class="input-group-append">
                    <input class="btn btn-outline-secondary" type="submit" id="search_button" value="Search"/>
                </div>
            </div>

              <!--list poll-->
              <table id="searchable">
                  <c:forEach items="${listpolls}" var="polls">
                      <tr><td>
                          <form action="/project-testing/polling" method="post">
                            <input hidden type="text" name="user_id" value=${user_id} id="get_user_id">
                            <input hidden type="text" name="get_username" value=${username} id="get_username">
                            <input hidden type="text" name="question_id" value=${polls.question_id}>
                            <button class="btn btn-outline-dark btn-lg">
                                <img src="https://img.icons8.com/office/16/00str 0000/poll-topic.png"/>
                                <c:out value="${polls.question}"/>
                            </button>
                          </form>
                      </tr></td>
                  </c:forEach>
              </table>
          </div>
        </div>
      </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/search.js" />"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>