var folienSatzList = null;
var nowfolienSatzId = 0;
var folienList = null;

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


//OnReady
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

//OnChange
$("#interaktivSwitch").change(function() {
    if(this.checked) {
        $("#allIntDiv").fadeIn(350);
    }
    else{
		$("#allIntDiv").fadeOut(450);
	}
});

