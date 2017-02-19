var folieAktiv = true;
var folienId = 0;
var pinSet = false;
var beantwortet = false;

var interaktiv = false;
var heatplot = false;
var bereiche = false;
var bereichList = []; // obenLinksX, obenLinksY, untenRechtsX, untenRechtsY


// Websocket
var socket = new WebSocket("ws://localhost:8080/ProjectFinisher/MessageHandler");

socket.onopen = function() {
	console.log("Websocketverbindung hergestellt :)");

	var kursInfoRequest = {
		type : "kursInfoRequest",
		userId : userId,
		kursId : kursId
	};
	var kursInfoRequestJson = JSON.stringify(kursInfoRequest);
	socket.send(kursInfoRequestJson);
};

socket.onerror = function(evt) {
	console.log("Websocketverbindung konnte nicht hergestellt werden :(");
	
	var socketEnde = {
			type : "socketEnde",
			userId : userId,
			kursId : kursId
		};
	var socketEndeJson = JSON.stringify(socketEnde);
	socket.send(socketEndeJson);
};

socket.onclose = function() {
	var socketEnde = {
			type : "socketEnde",
			userId : userId,
			kursId : kursId
		};
	var socketEndeJson = JSON.stringify(socketEnde);
	socket.send(socketEndeJson);
};

// Onmessages
socket.onmessage = function(evt) {
	var msg = $.parseJSON(evt.data);

	if (msg.type == "kursInfo") {
		$("#kursName").html(msg.kursName);
		$("#lehrerName").html(msg.lehrerName);
		if(msg.folie != null){
			folienUpdate(msg);
		}
	} else if (msg.type == "folienUpdate") {
		folienUpdate(msg);
	}

};


//Wenns document geladen hat
$(document).ready(function() {

});


//Functions

function folienUpdate(msg) {
	folieAktiv = true;
	beantwortet = false;
	interaktiv = false;
	heatplot = false;
	bereiche = false;
	folienId = msg.folie.folienID;
	
	$("#folienImg").attr("src", "ImgServlet?id=" + msg.folie.folienID);
	$("#lehrerName").html(msg.lehrerName);
	$("#folienName").html(msg.folie.fSatz.name);

	if (msg.folie.folienTyp = 'H') {
		interaktiv = true;
		heatplot = true;
	}
	else if(msg.folie.folienTyp = 'C' || 'M'){
		interaktiv = true;
		bereiche = true;
		bereichList = msg.bereichList;
	}
	else{
		interaktiv = false;
	}
	
	folienReset();
}

function folienReset(){
	var clickX = 0;
	var clickY = 0;
	$('#pin').hide;
	disableButtons();
}

function enableButtons() {
	$("#submitBtn").prop("disabled", false);
	$("#clearBtn").prop("disabled", false);
}
function disableButtons() {
	$("#submitBtn").prop("disabled", true);
	$("#clearBtn").prop("disabled", true);
}


//Klicks

// Klick auf Folie
var clickX = 0;
var clickY = 0;
var bereichNr = 0;

$('#folienImg').click(function(e) {
	
	if(folieAktiv){
		var offset_x = $(this).offset().left - $(window).scrollLeft();
		var offset_y = $(this).offset().top - $(window).scrollTop();
	
		var x = (e.clientX - offset_x);
		var y = (e.clientY - offset_y);
	
		var imgW = $(this).width();
		var imgH = $(this).height();
	
		var relX = Math.round((x / imgW) * 100);
		var relY = Math.round((y / imgH) * 100);
	
		
		// mit bereichlist überprüfen...
		var inBereich = false;
		for (var i = 0; i < bereichList.length; i++) {
			if(relX >= bereichList[i].obenLinksX && relX <= bereichList[i].untenRechtsX){
				if(relY >= bereichList[i].obenLinksY && relY <= bereichList[i].untenRechtsY){
					inBereich = true;
					bereichNr = i;
					break;
				}
			}
		}
		
		if(inBereich) { 
			clickX = relX;
			clickY = relY;
			pinSet = true; 
		}
		else { pinSet = false; }
		
		if(pinSet){
			$('#pin').css('left', e.pageX).css('top', e.pageY - 25).show();
			enableButtons();
		}
		if(!pinSet){
			$('#pin').hide;
			disableButtons();
		}
	}
});

//Klick auf Bestaetigen
$('#submitBtn').click(function(e) {
	disableButtons();
	
	beantwortet = true;
	
	if(bereiche){
		var bereichAntwort = {
				type : "bereichAntwort",
				userId : userId,
				kursId : kursId,
				folienId : folienId,
				bereichNr : bereichNr,
				posX : clickX,
				posY : clickY
			};
		var bereichAntwortJson = JSON.stringify(bereichAntwort);
		socket.send(bereichAntwortJson);
	}
	else if(heatplot){
		var heatplotAntwort = {
				type : "heatplotAntwort",
				userId : userId,
				kursId : kursId,
				folienId : folienId,
				posX : clickX,
				posY : clickY
			};
		var heatplotAntwortJson = JSON.stringify(heatplotAntwort);
		socket.send(heatplotAntwortJson);
	}
});

//Klick auf Loeschen
$('#clearBtn').click(function(e) {
	disableButtons();
	$('#pin').hide;
	clickX = 0;
	clickY = 0;
});

