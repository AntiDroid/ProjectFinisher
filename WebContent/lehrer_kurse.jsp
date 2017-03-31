<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, models.*"%>
<!DOCTYPE html>
<html>
<head>
	<%
	Client user = null;
	if(session.getAttribute("benutzer") == null){
		response.sendRedirect("login.jsp");
		return;
	}
	else{
		user = (Client) session.getAttribute("benutzer");
		if(user instanceof Student){
			response.sendRedirect("studenten_kurse.jsp");
		}
	}
	%>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Meine Kurse</title>

	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="style.css">
	<script type="text/javascript">
		
	</script>
	<style type="text/css">
	
	</style>
</head>
<body>

<div class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<a id="userName" class="navbar-brand" href="index.jsp"><%= user.getVorname()+" "+user.getNachname() %></a>
		<form action="LogoutServlet" method="post">
		<button class="navbar-right logoutButton btn btn-danger">Logout</button>
		</form>
	</div>
</div>

<div class="container text-center">
	<h1 class="">Meine Kurse</h1>
	<ul class="bigFont list-unstyled ">
		<% 
		ArrayList<Kurs> kurse = null;
		kurse = (ArrayList<Kurs>) session.getAttribute("kursListe");
		if(kurse != null){
			for(Kurs k : kurse){ %>
				<li><a href="KursServlet?kursId=<%= k.getID() %>"><%= k.getName() %></a></li>
		<% }} %>
	</ul>

	<div class="navbar-fixed-bottom bottomButton">
		<button class="btn btn-warning" data-toggle="modal" data-target="#kursModal">Neuen Kurs Anlegen</button>
	</div>
	<!-- Modal -->
	<div id="kursModal" class="modal fade">
	  <div class="modal-dialog">
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title">Neuen Kurs Anlegen</h4>
	      </div>
	      <div class="modal-body">

	        <form class="text-center" action="KursErstellenServlet" method="post">
	        	<label>Kursname:</label>
	        	<input type="text" name="newKursName"><br/>
	        	<label>Kurspasswort:</label>
	        	<input type="text" name="newKursPass"><br/>
	        	<input class="btn btn-success" type="submit" name="senden" value="Erzeugen">
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
	      </div>
	    </div>
	    
	  </div>
	</div>
</div>
	
<script src="jquery/jquery-3.1.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>