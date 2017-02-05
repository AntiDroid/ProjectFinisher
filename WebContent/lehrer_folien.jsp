<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Folien</title>

	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
	<link rel="stylesheet" type="text/css" href="slick/slick.css">
  	<link rel="stylesheet" type="text/css" href="slick/slick-theme.css">
	<link rel="stylesheet" href="style.css">
	<style type="text/css">
		.form-control{
			padding: 6px;
		}
		.folienOption{
			padding: 3px 2px;
		}

		.outerUploadBtn{
			width: 100%;
			margin: 1% 0 13px 0;
		}

		#uploadModal{
			margin: auto;
			text-align: center;
		}
		.innerUploadBtn{
			margin: 5px 0;
		}
		.abschnittsRow{
			margin: 3px 0 7px 0;
		}
		.abschnittsDiv{
			margin: 0 auto;
			width: 86%;
			border-style: solid;
			border-width: 1px;
		}

		.carouselDiv{
		}
		.folieThumbnail{
			padding: 0 3%;
			width: 100%;
		}
		.vorschauNr{
			margin: auto;
   			font-weight: bold;
		}

		.slick-arrow{
			filter: invert(70%);
		}

		.folieVorschau{
			width: 100%;
			border-style: solid;
			border-color: #ddd;
			border-width: 1px;
		}

		.folienButtons{
  			vertical-align: middle;
		}

		.interaktivSwitchDiv{
			font-size: 18px;
			margin: 18px auto;
		}

		.intModusDiv{
			margin: 14px 0;
			font-size: 16px;
			width: 100%;
		}

		.verticalMiddle{
  			vertical-align: middle;
		}
		.fWidth{
  			width: 100%;
		}
		.fHeight{
  			height: 100%;
		}
		.hWidth{
			width: 50%;
		}

		.abschnittsRow2{
			margin: 7px 0;
		}

		.intBereichButton{

			width: 100%;
			margin: 5px;

		}

		.borda{
			border-style: solid;
			border-width: 1px;
		}

		.folienNavDiv{
			margin: 0 0 7px 0;
		}

		.folienNavBtn{
			width: 100px;
			margin: 0 5px;
		}

		.intBerDiv{
			margin: 14px 0;
		}

		.auswDiv{
			margin: 12px 0;
		}


	</style>
</head>
<body>
<!--Navbar-->
<div class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<span id="lehrerName" class="navbar-brand">NAME</span>
		<span class="navbar-brand" style="font-size: 10px;">0 Studenten Online</span>
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
		<select id="folienSaetze" class="form-control" size="24">
			<option class="folienOption" value="1" selected>Foliensatz1</option>
			<option class="folienOption" value="2">Foliensatz2</option>
		</select>

		<button class="btn btn-sm btn-warning outerUploadBtn" data-toggle="modal" data-target="#uploadModal">PDF Hochladen</button>

		<!-- Upload Modal -->
		<div id="uploadModal" class="modal fade">
		  <div class="modal-dialog">
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <h4 class="modal-title">PDF Hochladen</h4>
		      </div>
		      <div class="modal-body">
		        <form class="text-center" action="" method="post">
		        	<label></label>
		        	<input type="file" accept=".pdf" name="pdfDatei" value=".pdf">
		        	<input class="btn btn-primary btn-sm innerUploadBtn" type="submit" name="senden" value="Hochladen">
		        </form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Schließen</button>
		      </div>
		    </div>

		  </div>
		</div>
		

		</div>
		<div class="col-md-10">
			<div class="row"></div>
				<div class="folienNavDiv text-center">
					<button class="folienNavBtn btn btn-xs">zurück</button>
					<span>7 Seiten</span>
					<button class="folienNavBtn btn btn-xs">vor</button>
				</div>

				<div class="carouselDiv">
				  <section class="center slider">
				    <div>
				      <img class="folieThumbnail" src="imgs/Beispiele/1.png">
				      <div class="text-center">1</div>
				    </div>
				    <div>
				      <img class="folieThumbnail" src="imgs/Beispiele/2.png">
				      <div class="text-center">2</div>
				    </div>
				    <div>
				      <img class="folieThumbnail" src="imgs/Beispiele/3.png">
				      <div class="text-center">3</div>
				    </div>
				    <div>
				      <img class="folieThumbnail" src="imgs/Beispiele/4.png">
				      <div class="text-center">4</div>
				    </div>
				    <div>
				      <img class="folieThumbnail" src="imgs/Beispiele/5.png">
				      <div class="text-center">5</div>
				    </div>
				    <div>
				      <img class="folieThumbnail" src="imgs/Beispiele/6.png">
				      <div class="text-center">6</div>
				    </div>
				    <div>
				      <img class="folieThumbnail" src="imgs/Beispiele/7.png">
				      <div class="text-center">7</div>
				    </div>
				  </section>
				</div>

			<div class="row abschnittsRow">
				<div class="abschnittsDiv"></div>
			</div>

			<div class="row">

				<div class="col-md-8">
					<!-- Hier sollte noch Canvas her -->

					<img class="folieVorschau" src="imgs/Beispiele/2.png">
				</div>

				<div class="col-md-4">

					<div class="row">
						<div class="folienButtons">
							<div class="col-md-6 text-left">
							<button id="useThisFoil" class="btn btn-success">Auf diese Folie<br/>wechseln</button>
							</div>
							<div class="col-md-6 text-right">
							<button id="delThisFoil" class="btn btn-xs btn-default verticalMiddle">Folie<br/>löschen!</button>
							</div>
						</div>
					</div>

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
				                <span class="btn btn-sm btn-default">
				                    <input type="radio" value="Bereiche" checked/> Bereiche
				                </span> 
				                <span class="btn btn-sm btn-default">
				                    <input type="radio" value="Heatplot" /> Heatplot
				                </span>
				            </span>
						</div>

						<div class="intBerDiv">
							<div class="row">
								<div class="col-md-6">
									<div class="text-center">Interaktive Bereiche:</div>
									<select class="fWidth" size="5">
										<option class="" value="1">1: 5,55;26,90</option>
										<option class="" value="2">Bereich 2</option>
									</select>
								</div>
								<div class="col-md-6" style="top: 5px;">
									<button class="btn btn-sm btn-danger intBereichButton">Neuer interaktiver<br/>Bereich</button>
									<button class="btn btn-xs btn-default intBereichButton" disabled>interaktiven Bereich<br/>löschen</button>
								</div>
							</div>
						</div>

						<div class="auswDiv">
							<div class="row">
								<div class="col-md-6">
									<div class="text-center">Auswertungen:</div>
									<select class="fWidth" size="5">
										<option class="" value="1">1: 23</option>
										<option class="" value="2">Auswertung 2</option>
									</select>
								</div>
								<div class="col-md-6" style="top: 30px;">
									<button class="btn btn-sm btn-info intBereichButton verticalMiddle">Histogramm<br/>erzeugen</button>
								</div>
							</div>
						</div>
					</div>

				</div>

			</div>


		</div>
	</div>

</div>


<!-- <script src="jquery/jquery-3.1.1.js"></script> -->
<!-- jquery2 wegn slick -->
<script src="https://code.jquery.com/jquery-2.2.0.min.js" type="text/javascript"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="slick/slick.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".center").slick({
	  infinite: false,
	  slidesToShow: 4,
	  slidesToScroll: 3
	});

	if($("#interaktivSwitch").attr("checked")){
		$("#allIntDiv").show();
	}
	else{
		$("#allIntDiv").hide();
	}

});

$("#interaktivSwitch").change(function() {
    if(this.checked) {
        $("#allIntDiv").fadeIn(350);
    }
    else{
		$("#allIntDiv").fadeOut(450);
	}
});



</script>

</body>
</html>