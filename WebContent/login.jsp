<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, models.*"%>
<!DOCTYPE html>
<html>
<head>

	<%
	Client user = null;
	if(session.getAttribute("benutzer") != null){
	  
	  if(session.getAttribute("benutzer") instanceof Lehrer)
	    response.sendRedirect("lehrer_kurse.jsp");
	  else if(session.getAttribute("benutzer") instanceof Student)
	    response.sendRedirect("studenten_kurse.jsp");
	  
	  return;
	}
	%>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Login</title>

	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
	<script type="text/javascript">
		
	</script>
	<style type="text/css">
		body{
			background-color: #f9f9f9
		}

		.loginForm {
			margin: 0 auto;
			max-width: 325px;
			margin-top: 16%;
		}

		.form-control {
			width: 100%;
			padding: 5px 10px;
			border-radius: 6px;
			font-size: 140%;
			margin-bottom: 5px; 
		}

		.loginForm h3{
			font-size: 300%;
			margin-bottom: 15px;
		}

		.btn-lg{
			width: 33%
		}

		#infoBox{
			border: solid;
			border-width: 2px;
			border-color: #dddd88;
		}
	</style>
</head>
<body>

<div class="container">
	<form class="loginForm" action="LoginServlet" method="post">
		<h3>Login</h3>
		<input type="text" name="user" class="form-control" id="usernameInput" placeholder="Username" required autofocus>
		<input type="password" name="pw" class="form-control" id="passwordInput" placeholder="Passwort" required>
		<input class="btn btn-info btn-lg" type="submit" name="submit" value="Login">
		<div id="infoBox" hidden></div>
	</form>
</div>

<script src="jquery/jquery-3.1.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>