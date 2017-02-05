<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Folienname</title>

	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="style.css">
	<script type="text/javascript">
		var userId = <%=session.getAttribute("userId")%>;
		var vorname = <%=session.getAttribute("vorname")%>;
		var nachname = <%=session.getAttribute("nachname")%>;
	</script>
	<style type="text/css">
		.allesContainer{
			max-width: 72em;
		}
		.imgDiv img {
		   width: 100%;
		   margin: 4px 0;
		   border-style: solid;
		   border-color: #ccc;
		   border-width: thin;
		}

		.submitButton{
			padding: 12px 7px;
			float: right;
		}
	</style>
</head>
<body>
<div class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<div id="userName" class="navbar-brand">NAME</div>
		<form action="LogoutServlet" method="post">
		<button class="navbar-right logoutButton btn btn-danger">Logout</button>
		</form>
	</div>
</div>

<div class="container allesContainer">

	<div class="row">
		<div id="lehrerName" class="text-left col-sm-4">Lehrer</div>
		<div id="kursName" class="text-center col-sm-4"><b>Kursname</b></div>
		<div id="folienName" class="text-right col-sm-4">Folienname</div>
	</div>

	<div class="imgDiv">
			<img id="folieImg" src="imgs/Beispiele/2.png"/>
	</div>

	<div class="row">
		<div class="col-xs-6">
			<button class="btn text-left" disabled>Marker<br/>LÃ¶schen</button>
		</div>
		<div class="col-xs-6">
			<button class="btn btn-success text-right submitButton" disabled>BestÃ¤tigen</button>
		</div>
	</div>
</div>


<script src="jquery/jquery-3.1.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">

var clickX = 0;
var clickY = 0;

$('#folieImg').click(function(e)
		{   
    		var offset_x = $(this).offset().left - $(window).scrollLeft();
		    var offset_y = $(this).offset().top - $(window).scrollTop();

		    var x = (e.clientX - offset_x);
		    var y = (e.clientY - offset_y);
		    
		    var imgW = $(this).width();
		    var imgH = $(this).height();
		    
		    var relX = Math.round((x/imgW)*100);
		    var relY = Math.round((y/imgH)*100);
		    
		    clickX = relX;
		    clickY = relY;
		    alert("X: " + clickX + " Y: " + clickY);

});
</script>
</body>
</html>