<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <img style="display: block; margin-left: auto; margin-right: auto; margin-top: 10em;" width="50%" src = "resources/images/logo.PNG">

    <div class="d-flex justify-content-center">
        <div class="p-2">
            <form action="/project-testing/loginpage.jsp">
                <input type="submit" value="Login" class="btn btn-primary btn-lg">
            </form>
        </div>
        <div class="p-2">            
            <form action="/project-testing/registerpage.jsp">
                <input type="submit" value="Register" class="btn btn-primary btn-lg">
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>