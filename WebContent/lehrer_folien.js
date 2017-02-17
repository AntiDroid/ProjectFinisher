var folienSatzList = null;
var nowfolienSatzId = 0;
var nowfolienId = 0;
var folienList = null;
var aktuelleFolie = null;

// Websocket
var socket = new WebSocket("ws://localhost:8080/ProjectFinisher/MessageHandler");

socket.onopen = function() {
	console.log("Websocketverbindung hergestellt :)");

	var lehrerKursInfoRequest = {
		type : "lehrerKursInfoRequest",
		userId : userId,
		kursId : kursId
	};
	var lehrerKursInfoRequestJson = JSON.stringify(lehrerKursInfoRequest);
	socket.send(lehrerKursInfoRequestJson);
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

	if (msg.type == "lehrerKursInfo") {
		if(msg.folienSatzList != null){
			folienSatzList = msg.folienSatzList;
			updateFolienSatzList();
		}
	}
	else if (msg.type == "folienSatz"){
		if(msg.folienList != null){
			folienList = msg.folienList;
			updateFolien();
		}
	}
	else if (msg.type == "folienInfo"){
		if(msg.folie != null){
			if(msg.folie.folienTyp == 'A'){
				$("#interaktivSwitch").prop('checked', false);
			}
			else if(msg.folie.folienTyp == 'C'){
				$("#interaktivSwitch").prop('checked', true);
				$("#bereichRadio").prop('checked', true);
				$("#allIntDiv").show();
				$("#intBerDiv$").show();
				
				var bereichList = folie.bereichList;
				var htmlString = "";
				for (var i = 0; i < bereichList.length; i++) {
					var oLX = bereichList[i].obenLinksX;
					var oLY = bereichList[i].obenLinksY;
					var uRX = bereichList[i].untenRechtsX;
					var uRY = bereichList[i].untenRechtsY;
					htmlString += "<option class='' value='"+i+"'>"+(i+1)+": "+oLX+","+oLY+";"+uRX+","+uRY+"</option>";
				}
				$("#intBereichList").html(htmlString);
				
				var bAuswerteList = folie.bAuswerteList;
				var htmlString = "";
				for (var i = 0; i < bAuswerteList.length; i++) {
					var wert = bAuswerteList[i];
					htmlString += "<option class='' value='"+wert+"'>"+(i+1)+": "+wert+"</option>";
				}
				$("#auswerteList").html(htmlString);
			}
			else if(msg.folie.folienTyp == 'H'){
				$("#interaktivSwitch").prop('checked', true);
				$("#heatplotRadio").prop('checked', true);
				$("#allIntDiv").show();
				$("#intBerDiv$").hide();
				
				var hAuswerteList = folie.hAuswerteList;
				var htmlString = "";
				for (var i = 0; i < hAuswerteList.length; i++) {
					var x = hAuswerteList[i].koordX;
					var y = hAuswerteList[i].koordY;
					htmlString += "<option class='' value='"+i+"'>"+(i+1)+": "+x+","+y+"</option>";
				}
				$("#auswerteList").html(htmlString);
			}
		}
	}
	

};


//Functions
function updateFolienSatzList() {
	var htmlString = "";
	for (var i = 0; i < folienSatzList.length; i++) {
		htmlString += "<option class='folienSatzOption' value='"+folienSatzList[i].folienSatzID+"'>"+folienSatzList[i].name+"</option>";
	}
	$("#folienSatzListe").html(htmlString);
}
function updateFolien() {
	$("#folienNavAnzahl").html(folienList.length+" Seiten");
	
	var htmlString = "";
	var fId = 0;
	for (var i = 0; i < folienList.length; i++) {
		fId = folienList[i].folienID;
		htmlString = ""
		+'<div>'
	      +'<img class="folieThumbnail" src="ImgServlet?id='+fId+'" value="'+fId+'">'
	      +'<div class="text-center">'+(i+1)+'</div>'
	    +'</div>';
	}
	$("#folienNavThumbs").html(htmlString);
}



//Onklick
//Folienatz laden
$('.folienSatzOption').click(function(e) {
	nowfolienSatzId = $(this).value;
	
	var folienSatzRequest = {
			type : "folienSatzRequest",
			userId : userId,
			folienSatzId : nowfolienSatzId
		};
	var folienSatzRequestJson = JSON.stringify(folienSatzRequest);
	socket.send(folienSatzRequestJson);
});
$('.folieThumbnail').click(function(e) {
	var fId = $(this).val();
	
	var folienInfoRequest = {
			type : "folienInfoRequest",
			userId : userId,
			folienId : fId
		};
	var folienInfoRequestJson = JSON.stringify(folienInfoRequest);
	socket.send(folienInfoRequestJson);
	
	
	//Ab hier kommts wahrscheinlich in FolienInfo
	//<------------------------------------>
	$(".folieThumbnail").removeClass("xAusgFolie");
	$(this).addClass("xAusgFolie");
	
	
	//CANVAS PROBLEME TODO
	var canvas = document.getElementById("folieCanvas");
	canvas.width = $('#canvasDiv').width();
	canvas.height = $('#canvasDiv').height();
	var img = new Image();
	img.onload = function() {
		var prop = img.height/img.width;
		$('#canvasDiv').height($('#canvasDiv').width()*prop);
		$('#folieCanvas').height($('#canvasDiv').width()*prop);
		var cw = $('#folieCanvas').width();
		var ch = $('#folieCanvas').width()*prop;
		
		var ctx = canvas.getContext("2d");
	    ctx.drawImage(img, 0, 0, cw, ch);
	};
	img.src = 'ImgServlet?id='+fId;
	
});


//OnReady
$(document).ready(function(){
	$(".center").slick({
	  infinite: false,
	  slidesToShow: 4,
	  slidesToScroll: 3
	});

	if($("#interaktivSwitch").prop("checked")){
		$("#allIntDiv").show();
	}
	else{
		$("#allIntDiv").hide();
	}

});

//OnChange
$("#interaktivSwitch").change(function() {
    if(this.checked) {
        $("#allIntDiv").fadeIn(350);
    }
    else{
		$("#allIntDiv").fadeOut(450);
	}
});

$("input[name=intModus]").change(function() {
    if($(this).val() == "Bereiche") {
        $("#intBerDiv").fadeIn(350);
    }
    else if($(this).val() == "Heatplot") {
        $("#intBerDiv").fadeOut(350);
    }
    
});

