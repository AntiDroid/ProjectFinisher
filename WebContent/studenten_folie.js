//Wenns document geladen hat
$(document).ready(function() {

});

var folieAktiv = true;
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
		if(msg.folieAktiv){
			var folienRequest = {
					type : "folienRequest",
					userId : userId,
					kursId : kursId
				};
			var kursInfoRequestJson = JSON.stringify(folienRequest);
			socket.send(folienRequestJson);
		}
	} else if (msg.type == "folienUpdate") {
		folieAktiv = true;
		beantwortet = false;
		interaktiv = false;
		heatplot = false;
		bereiche = false;

		$("#folienImg").attr("src", "ImgServlet?id=" + msg.folienId);
		$("#lehrerName").html(msg.lehrerName);
		$("#folienName").html(msg.fSatzName);

		if (msg.interaktiv) {
			interaktiv = true;
			if (msg.isHeatplot) {
				heatplot = true;
			} else {
				bereiche = true;
				bereichList = msg.bereichList;
			}
		}
		
		folienReset();
	}

};

//Functions

function folienReset(){
	var clickX = 0;
	var clickY = 0;
	$('#pin').hide;
	$("#bestaetigen").attr("disabled");
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
			$("#bestaetigen").removeAttr("disabled");
		}
		if(!pinSet){
			$('#pin').hide;
			$("#bestaetigen").attr("disabled");
		}
	}
});

$('#bestaetigen').click(function(e) {
	beantwortet = true;
	
	if(bereiche){
		var bereichAntwort = {
				type : "bereichAntwort",
				userId : userId,
				kursId : kursId,
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
				posX : clickX,
				posY : clickY
			};
		var heatplotAntwortJson = JSON.stringify(heatplotAntwort);
		socket.send(heatplotAntwortJson);
	}
	
	$("#bestaetigen").attr("disabled");
});


