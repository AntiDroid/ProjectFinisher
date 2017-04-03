var folieAktiv = true;
var folienId = 0;
var pinSet = false;
var beantwortet = false;

var folie = null;
var folienTyp = 'A';
var bereichList = []; // obenLinksX, obenLinksY, untenRechtsX, untenRechtsY


// Websocket
var wsip = "localhost"
var socket = new WebSocket("ws://"+wsip+":8080/ProjectFinisher/MessageHandler");

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
	console.log("Websocket Error :(");
	console.log(evt.data);
	
	endSocket();
};

socket.onclose = function() {
	console.log("Websocket Closed :(")
	
	endSocket();
};

// Onmessages
socket.onmessage = function(evt) {
	var msg = $.parseJSON(evt.data);
	console.log(evt.data);

	if (msg.type == "kursInfo") {
		$("#kursName").html(msg.kursName);
		$("#lehrerName").html(msg.lehrerName);
		if(msg.folie != null){
			folie = msg.folie;
			bereichList = msg.bereichList;
			folienUpdate(msg);
		}
		else deactivateAnsicht();
	} 
	else if (msg.type == "folienUpdate") {
		if(msg.folie != null){
			folie = msg.folie;
			bereichList = msg.bereichList;
			folienUpdate(msg);
		}
		else deactivateAnsicht();
	}

};


//Functions
function folienUpdate(msg) {
	folieAktiv = true;
	beantwortet = false;
	folienTyp = 'A';
	folienId = folie.folienID;
	
	if(msg.beantwortet != null){
		beantwortet = msg.beantwortet;
	}
	
	$("#folienImg").prop("src", "ImgServlet?id=" + folie.folienID);
	
	$("#lehrerName").html(msg.lehrerName);
	
	folienTyp = folie.folienTyp; 
	if(folienTyp == 'A'){
		$("#folienTyp").html("Ansicht");
		$("#folienTyp").css("color", "DarkBlue");
	}
	else if(folienTyp == 'C'){
		$("#folienTyp").html("Auswahl");
		$("#folienTyp").css("color", "DarkOrange");
	}
	else if(folienTyp == 'H'){
		$("#folienTyp").html("freie Auswahl");
		$("#folienTyp").css("color", "DarkOrange");
	}
	
	folienReset();
	activateAnsicht();
}

function folienReset(){
	var clickX = 0;
	var clickY = 0;
	$('#pin').hide();
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

function activateAnsicht() {
	$("#noFoil").hide();
	$("#allesContainer").show();
}
function deactivateAnsicht() {
	$("#allesContainer").hide();
	$("#noFoil").show();
}

function endSocket() {
	var socketEnde = {
			type : "socketEnde",
			userId : userId,
			kursId : kursId
		};
	var socketEndeJson = JSON.stringify(socketEnde);
	socket.send(socketEndeJson);
}


//Klicks

// Klick auf Folie
var clickX = 0;
var clickY = 0;
var bereichNr = 0;

$('#folienImg').mousedown(function(e) {
	if(!beantwortet){
		if(folieAktiv && folienTyp != 'A'){
			var offset_x = $(this).offset().left - $(window).scrollLeft();
			var offset_y = $(this).offset().top - $(window).scrollTop();
		
			var x = (e.clientX - offset_x);
			var y = (e.clientY - offset_y);
		
			var imgW = $(this).width();
			var imgH = $(this).height();
		
			var relX = Math.round((x / imgW) * 100);
			var relY = Math.round((y / imgH) * 100);
		
			if(folienTyp == 'C'){
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
			}
			else if (folienTyp == 'H') {
				clickX = relX;
				clickY = relY;
				pinSet = true; 
			}
			
			
			if(pinSet){
				$('#pin').css('left', e.pageX).css('top', e.pageY - 25).show();
				enableButtons();
			}
			else{
				$('#pin').hide;
				disableButtons();
			}
		}
	}
});

//Klick auf Bestaetigen
$('#submitBtn').click(function(e) {
	disableButtons();
	
	beantwortet = true;
	
	if(folienTyp == 'C'){
		var bereichAntwort = {
				type : "bereichAntwort",
				userId : userId,
				kursId : kursId,
				folienId : folienId,
				bereichNr : bereichNr,
				posX : clickX,
				posY : clickY,
				sessionId : 0
			};
		var bereichAntwortJson = JSON.stringify(bereichAntwort);
		socket.send(bereichAntwortJson);
	}
	else if(folienTyp == 'H'){
		var heatplotAntwort = {
				type : "heatplotAntwort",
				userId : userId,
				kursId : kursId,
				folienId : folienId,
				posX : clickX,
				posY : clickY,
				sessionId : 0
			};
		var heatplotAntwortJson = JSON.stringify(heatplotAntwort);
		socket.send(heatplotAntwortJson);
	}
});

//Klick auf Loeschen
$('#clearBtn').click(function(e) {
	disableButtons();
	$('#pin').hide();
	clickX = 0;
	clickY = 0;
});

$('img').on('dragstart', function(event) { event.preventDefault(); });


// SocketEnde beim Schließen
window.onbeforeunload = function (e) {
	var socketEnde = {
			type : "socketEnde",
			userId : userId,
			kursId : kursId
		};
	var socketEndeJson = JSON.stringify(socketEnde);
	socket.send(socketEndeJson);
};