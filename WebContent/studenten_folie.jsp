<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="models.*"%>
<!DOCTYPE html>
<html>
<head>
	<%
	Client user = null;
	int kursId = 0;
	
	if(session.getAttribute("benutzer") == null){
		response.sendRedirect("login.jsp");
		return;
	}
	else{
		user = (Client) session.getAttribute("benutzer");
		if(user instanceof Lehrer){
			response.sendRedirect("lehrer_kurse.jsp");
			return;
		}
		if(session.getAttribute("kursId") != null){
			kursId = (int) session.getAttribute("kursId");
		}
		else{
			response.sendRedirect("studenten_kurse.jsp");
			return;
		}
	}
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Folie</title>

	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="student_style.css">
</head>
<body>

	<div class="navbar navbar-inverse navbar-static-top">
		<div class="container">
			<a id="userName" href="index.jsp" class="navbar-brand"></a>
			<form action="LogoutServlet" method="post">
			<button class="navbar-right logoutButton btn btn-danger">Logout</button>
			</form>
		</div>
	</div>
	
	<div id="noFoil">Zurzeit ist keine Folie aktiv...</div>
	<div id="allesContainer" class="container" hidden>
	
		<div class="row">
			<div id="lehrerName" class="text-left col-sm-4">Lehrer</div>
			<div id="kursName" class="text-center col-sm-4">Kursname</div>
			<div id="folienTyp" class="text-right col-sm-4">Folientyp</div>
		</div>
	
		<div class="imgDiv">
			<img id="folienImg" src="ImgServlet?id=0"/>
		</div>
		
		<img id="pin" src="imgs/pin.png" style="display: none; position: absolute;" />
	
		<div class="row">
			<div class="col-xs-6">
				<button id="clearBtn" class="btn text-left" disabled>Marker<br/>Löschen</button>
			</div>
			<div class="col-xs-6">
				<button id="submitBtn" class="btn btn-success text-right submitButton" disabled>Bestätigen</button>
			</div>
		</div>
	</div>


<script src="jquery/jquery-3.1.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">

	var userId = "<%=user.getID()%>";
	var vorname = "<%=user.getVorname()%>";
	var nachname = "<%=user.getNachname()%>";
	
	var kursId = <%=kursId%>;
	var sessionId = "<%=session.getId()%>";
	
	if(vorname != null && nachname != null){
		$("#userName").html(vorname+" "+nachname);
	}
	else{
		$("#userName").html("kein Name");
	}
</script>
<script src="studenten_folie.js"></script>
</body>
</html>