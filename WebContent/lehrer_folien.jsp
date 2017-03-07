<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="models.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		if(user instanceof Student){
			response.sendRedirect("studenten_kurse.jsp");
			return;
		}
		if(session.getAttribute("kursId") != null){
			kursId = (int) session.getAttribute("kursId");
		}
		else{
			response.sendRedirect("lehrer_kurse.jsp");
			return;
		}
	}
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Folien</title>

	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
	<link rel="stylesheet" type="text/css" href="slick/slick.css">
  	<link rel="stylesheet" type="text/css" href="slick/slick-theme.css">
	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="lehrer_style.css">
</head>
<body>
<!--Navbar-->
<div class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<a id="userName" href="index.jsp" class="navbar-brand"></a>
		<span id="studentsOnline" class="navbar-brand" style="font-size: 10px;">0 Studenten Online</span>
		<form action="LogoutServlet" method="post">
		<button class="navbar-right logoutButton btn btn-danger">Logout</button>
		</form>
	</div>
</div>

<!--Eigentlicher Aufbau-->
<div class="container">
	<div class="row">

		<div class="col-md-2">

		<div style="font-size: 20px">Foliensätze:</div>
		<select id="folienSatzListe" class="form-control" size="24">
			<!-- Hier werden die Foliensätze dynamisch geladen -->
		</select>

		<button class="btn btn-sm btn-warning outerUploadBtn" data-toggle="modal" data-target="#uploadModal">PDF Hochladen</button>
		<button id="fSatzLoeschenModalBtn" class="btn btn-sm" data-toggle="modal" data-target="#fSatzLoeschenModal">Foliensatz löschen</button>

		
		

		</div>
		<div id="allFoliensatzAnsicht" class="col-md-10">
			<div class="row"></div>
				<div class="folienNavDiv text-center">
					<button id="backButton" class="folienNavBtn btn btn-xs">zurück</button>
					<span id="folienNavAnzahl">0 Seiten</span>
					<button id="forwardButton" class="folienNavBtn btn btn-xs">vor</button>
				</div>

				<div class="carouselDiv">
				  <section id="folienNavThumbsSlick" class="center slider">
				  	<!-- Hier werden die Folienthumbnails dynamisch geladen -->
				  </section>
				</div>

			<div class="row abschnittsRow">
				<div class="abschnittsDiv"></div>
			</div>

			<div class="row">

				<div class="col-md-8">
					<div id="canvasDiv">
						<canvas id="heatplotCanvas" class="folieVorschau"></canvas>
						<canvas id="folieCanvas" class="folieVorschau"></canvas>
					</div>
				</div>

				<div class="col-md-4">

					<div class="row">
						<div class="folienButtons">
							<div class="col-md-6 text-left">
							<button id="useThisFoil" class="btn btn-success">Auf diese Folie<br/>wechseln</button>
							</div>
							<div class="col-md-6 text-right">
							<button id="delThisFoil" class="btn"><b>Folie<br/>löschen</b></button>
							</div>
						</div>
					</div>
					
					<button id="notUseThisFoil" class="btn btn-lg" hidden>Folie<br/>Inaktivieren</button>
					
					<div id="interaktivControlsDiv">
						<div class="interaktivSwitchDiv">
							<div id="intSwitchBtn" class="material-switch">
								<span class="">Interaktiv: </span>
	                            <input id="interaktivSwitch" type="checkbox" hidden/>
	                            <label for="interaktivSwitch" class="label-primary"></label>
	                        </div>
						</div>
	
						<div id="allIntDiv">
							<div class="intModusDiv">
								 <span class="verticalMiddle">Interaktionsmodus: </span>
								 <span class="btn-group" data-toggle="buttons">
					                <label id="bereichRadioBtn" class="btn btn-sm btn-default active" for="bereichRadio">
					                    <input id="bereichRadio" type="radio" name="intModus" value="Bereiche" checked/> Bereiche
					                </label> 
					                <label id="heatplotRadioBtn" class="btn btn-sm btn-default" for="heatplotRadio">
					                    <input id="heatplotRadio" type="radio" name="intModus" value="Heatplot" /> Heatplot
					                </label>
					            </span>
							</div>
	
							<div id="intBerDiv" class="intBerDiv">
								<div class="row">
									<div class="col-md-6">
										<div class="text-center">Interaktive Bereiche:</div>
										<select id="intBereichList" class="fWidth" size="5">
											<!-- Dynamisches Reinladen von Bereichen -->
										</select>
									</div>
									<div class="col-md-6" style="top: 5px;">
										<button id="newIntBereich" class="btn btn-sm btn-danger intBereichButton">Neuer interaktiver<br/>Bereich</button>
										<button id="delIntBereich" class="btn btn-xs btn-default intBereichButton" disabled>interaktiven Bereich<br/>löschen</button>
									</div>
								</div>
							</div>
	
							<div class="auswDiv">
								<div class="row" id="befragungRow">
									<div class="col-md-4 vertical-middle">
										<apan>Befragungen:</span>
									</div>
									<div class="col-md-8">
										<select id="befList" class="form-control">
											<option></option>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="text-center">Auswertungen:</div>
										<select id="auswerteList" class="fWidth" size="5">
											<!-- Dynamisches Reinladen von Auswertungen -->
										</select>
									</div>
									<div class="col-md-6" style="top: 30px;">
										<button id="histoBtn" class="btn btn-sm btn-info intBereichButton verticalMiddle" data-toggle="modal" data-target="#histoModal">Histogramm<br/>erzeugen</button>
									</div>
								</div>
							</div>
						</div>
					</div>

				</div>

			</div>


		</div>
	</div>

</div>

<!-- Upload Modal -->
		<div id="uploadModal" class="modal fade">
		  <div class="modal-dialog">
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <h4 class="modal-title">PDF Hochladen</h4>
		      </div>
		      <div class="modal-body">
		        <form enctype="multipart/form-data" class="text-center" action="GetPdfServlet" method="post" onsubmit="endSocket()">
		        	<label></label>
		        	<input type="file" accept=".pdf" name="pdfDatei" value=".pdf">
		        	<label>Foliensatzname:</label>
		        	<input type="text" name="name" required>
		        	<input type="text" name="kursId" id="getPdfKursId" hidden>
		        	<input class="btn btn-primary btn-sm innerUploadBtn" type="submit" name="senden" value="Hochladen">
		        </form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
		      </div>
		    </div>

		  </div>
		</div>
		
<!-- Histogramm Modal -->
		<div id="histoModal" class="modal fade">
		  <div class="modal-dialog">
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <h4 class="modal-title">Histogramm</h4>
		      </div>
		      <div id="histoModalBody" class="modal-body">
		      
		      	<div id="chartContainer" height="400px"></div>
		      	
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
		      </div>
		    </div>

		  </div>
		</div>
		
<!-- Foliensatz löschen Modal -->
		<div id="fSatzLoeschenModal" class="modal fade">
		  <div class="modal-dialog">
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <h4 class="modal-title">Foliensatz löschen?</h4>
		      </div>
		      <div class="modal-body text-center">
		      	
		      	<div>Sind Sie sich sicher, dass den aktuellen Foliensatz löschen möchten?</div>
		      	<br/>
		      	<button id="delFolienSatzBtn" class="btn btn-danger">Löschen</button>
		      	
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
		      </div>
		    </div>

		  </div>
		</div>


<script src="jquery/jquery-3.1.1.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="slick/slick.js" type="text/javascript" charset="utf-8"></script>
<script src="canvasjs/canvasjs.min.js"></script>
<script src="heatmap/webgl-heatmap.js"></script>
<script type="text/javascript">
	var userId = <%=user.getID()%>;
	var vorname = "<%=user.getVorname()%>";
	var nachname = "<%=user.getNachname()%>";
	
	var kursId = <%=kursId%>;
	var sessionId = "<%=session.getId()%>";
	
	$("#getPdfKursId").val(kursId);
	
	if(vorname != null && nachname != null){
		$("#userName").html(vorname+" "+nachname);
	}
	else{
		$("#userName").html("kein Name");
	}
</script>
<script src="lehrer_folien.js"></script>
<script src="drawBereich.js"></script>
</body>
</html>