<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
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
<%
// Braugsch eig lei Parameter String schuelerName und ArrayList<String> kursListe übergebn
String schuelerName = (String) session.getAttribute("schuelerName");
%>

<div class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<div id="schuelerName" class="navbar-brand"><%= schuelerName %></div>
		<button class="navbar-right logoutButton btn btn-danger">Logout</button>
	</div>
</div>

<div class="container text-center">
	<h1 class="">Meine Kurse</h1>
	<ul class="bigFont list-unstyled ">
		<li><a href="#">Kurs1</a></li>
		<% 
		ArrayList<String> kurse = null;
		kurse = (ArrayList<String>)session.getAttribute("kursListe");
		if(kurse != null){
			for(String k : kurse){ %>
				<li><a href="KursServlet?kursName=<%= k %>"><%= k %></a></li>
		<% }} %>
	</ul>

	<div class="navbar-fixed-bottom bottomButton">
		<button class="btn btn-warning" data-toggle="modal" data-target="#kursModal">In neuen Kurs eintragen</button>
	</div>
	<!-- Modal -->
	<div id="kursModal" class="modal fade">
	  <div class="modal-dialog">
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title">In neuen Kurs eintragen</h4>
	      </div>
	      <div class="modal-body">
	        <form action="EintragenServlet" method="post">
	        	<label>Geben Sie die KursID ein:</label>
	        	<input type="text" name="kursEintragung">
	        	<input class="btn btn-primary btn-xs" type="submit" name="senden" value="Eintragen">
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">SchlieÃen</button>
	      </div>
	    </div>

	  </div>
	</div>
</div>
	
<script src="jquery/jquery-3.1.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>