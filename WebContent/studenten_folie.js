//Wenns document geladen hat
$(document).ready(function() {

});

var interaktiv = false;
var heatplot = false;
var bereiche = false;

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
};

socket.onclose = function() {

};

// Onmessages
socket.onmessage = function(evt) {
	var msg = $.parseJSON(evt.data);

	if (msg.type == "kursInfo") {
		$("#kursName").html(msg.kursName);
		$("#lehrerName").html(msg.lehrerName);
	} else if (msg.type == "folienUpdate") {
		interaktiv = false;
		heatplot = false;
		bereiche = false;

		$("#folienImg").attr("src", "ImgServ?id=" + msg.folienId);
		$("#lehrerName").html(msg.lehrerName);
		$("#folienName").html(msg.fSatzName);

		if (msg.interaktiv) {
			interaktiv = true;
			if (msg.isHeatplot) {
				heatplot = true;
			} else {
				bereiche = true;
				for (var int = 0; int < msg.bereichList.length; int++) {
					// shit zu machen
				}
			}
		}
	}

};

// Klick auf Folie
var clickX = 0;
var clickY = 0;

$('#folienImg').click(function(e) {
	var offset_x = $(this).offset().left - $(window).scrollLeft();
	var offset_y = $(this).offset().top - $(window).scrollTop();

	var x = (e.clientX - offset_x);
	var y = (e.clientY - offset_y);

	var imgW = $(this).width();
	var imgH = $(this).height();

	var relX = Math.round((x / imgW) * 100);
	var relY = Math.round((y / imgH) * 100);

	clickX = relX;
	clickY = relY;

	$('#pin').css('left', e.pageX).css('top', e.pageY - 25).show();
	$("#bestaetigen").removeAttr("disabled");

});