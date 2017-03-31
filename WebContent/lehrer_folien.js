var folienSatzList = null;
var folienList = null;
var aktuelleFolie = null;
var bereichList = null;
var bAuswerteList = null;
var votings = null;

var nowfolienSatzId = 0;
var nowFolienId = 0;
var aktiveFolienId = 0;
var studentsOnline = 0;
var ausgIntBereich = 0;
var befId = 0;

$("#fSatzLoeschenModalBtn").hide();
$("#notUseThisFoil").hide();

var canvas = document.getElementById("folieCanvas");
var ctx = canvas.getContext("2d");

var heatplotCanvas = document.getElementById("heatplotCanvas");
var heatmap = null;


// Websocket
var wsip = "localhost"
var socket = new WebSocket("ws://"+wsip+":8080/ProjectFinisher/MessageHandler");

socket.onopen = function() {
	var lehrerKursInfoRequest = {
		type : "lehrerKursInfoRequest",
		userId : userId,
		kursId : kursId
	};
	var lehrerKursInfoRequestJson = JSON.stringify(lehrerKursInfoRequest);
	socket.send(lehrerKursInfoRequestJson);
};

socket.onerror = function(evt) {
	console.log(evt.data);
};

socket.onclose = function() {
	console.log("Websocket Closed :(")
};

// Onmessages
socket.onmessage = function(evt) {
	var msg = $.parseJSON(evt.data);
	
	if (msg.type == "lehrerKursInfo") {
		if(msg.folienSatzList != null){
			folienSatzList = msg.folienSatzList;
			aktiveFolienId = msg.aktiveFolienId;
			updateFolienSatzList();
		}
		if(msg.anzOnline != null){
			studentsOnline = msg.anzOnline;
			updateStudentsOnline();
		}
	}
	else if (msg.type == "folienSatz"){
		disableControls();
		$("#allFoliensatzAnsicht").show();
		if(msg.folienList != null){
			folienList = msg.folienList;
			updateFolien();
		}
	}
	else if (msg.type == "onlineUpdate"){
		studentsOnline = msg.anzOnline;
		updateStudentsOnline();
	}
	else if (msg.type == "folienInfo"){
		$("#heatplotCanvas").hide();
		heatmap.clear();
		heatmap.update();
		heatmap.display();
		
		if(msg.folie != null){
			aktuelleFolie = msg.folie;
			
			//CANVAS Bild und Bereiche
			canvas.width = $('#canvasDiv').width();
			canvas.height = $('#canvasDiv').height();
			var img = new Image();
			img.src = 'ImgServlet?id='+nowFolienId;
			
			img.onload = function() {
				var prop = img.height/img.width;
				$('#canvasDiv').height($('#canvasDiv').width()*prop);
				canvas.height = $('#canvasDiv').width()*prop;
				var cw = $('#folieCanvas').width();
				var ch = cw*prop;
				
			    ctx.drawImage(img, 0, 0, cw, ch);
			    
			    //Bereiche Reinmalen
				bereichList = msg.bereichList;
			    if(bereichList != null && msg.folie.folienTyp == 'C'){
				    ctx.globalAlpha = 0.4;
				    ctx.fillStyle="#d000ff";
					for (var i = 0; i < bereichList.length; i++) {
						var oLX = bereichList[i].obenLinksX;
						var oLY = bereichList[i].obenLinksY;
						var uRX = bereichList[i].untenRechtsX;
						var uRY = bereichList[i].untenRechtsY;
					    ctx.fillRect(relToAbsX(oLX), relToAbsY(oLY), relToAbsX(uRX)-relToAbsX(oLX), relToAbsY(uRY)-relToAbsY(oLY));
					}
				}
			    else if (msg.folie.folienTyp == 'H') {
			    	votings = msg.votings;
					if(votings != null)
					try {
						$("#heatplotCanvas").show();
						heatmap.adjustSize();
						
						for (var i = 0; i < votings.length; i++) {
							var relX = votings[i].koordX;
							var relY = votings[i].koordY;
							
							var absX = Math.round(relToAbsX(relX));
							var absY = Math.round(relToAbsY(relY));
							
					        heatmap.addPoint(absX, absY, 65, 0.45);
							}
						heatmap.update();
						heatmap.display();
					} catch (e) {
						console.log(e);
					}
				}
			};
			//CANVAS ENDE
			
			if(msg.folie.folienTyp == 'A'){
				$("#interaktivSwitch").prop('checked', false);
				$("#allIntDiv").fadeOut(350);
			}
			else if(msg.folie.folienTyp == 'C'){
				$("#interaktivSwitch").prop('checked', true);
				$("#bereichRadio").prop('checked', true);
				$("#bereichRadioBtn").addClass("active");
				$("#heatplotRadioBtn").removeClass("active");
				$("#allIntDiv").fadeIn(350);
				$("#intBerDiv").slideDown(200);
				$("#histoBtn").show(300);
				
				bereichList = msg.bereichList;
				var htmlString = "";
				if(bereichList != null){
					for (var i = 0; i < bereichList.length; i++) {
						var bId = bereichList[i].auswahlBereichsID;
						var oLX = bereichList[i].obenLinksX;
						var oLY = bereichList[i].obenLinksY;
						var uRX = bereichList[i].untenRechtsX;
						var uRY = bereichList[i].untenRechtsY;
						htmlString += "<option class='' value='"+bId+"'>"+(i+1)+": "+oLX+","+oLY+" ; "+uRX+","+uRY+"</option>";
					}
				}
				$("#intBereichList").html(htmlString);
				
				befList = msg.befList;
				befListUpdate(befList);
				
				bAuswerteList = msg.bAuswerteList;
				var htmlString = "";
				if(bAuswerteList != null){
					for (var i = 0; i < bAuswerteList.length; i++) {
						var wert = bAuswerteList[i];
						htmlString += "<option class='' value='"+wert+"'>"+(i+1)+": "+wert+"</option>";
					}
				}
				$("#auswerteList").html(htmlString);
			}
			else if(msg.folie.folienTyp == 'H'){
				$("#interaktivSwitch").prop('checked', true);
				$("#heatplotRadio").prop('checked', true);
				$("#heatplotRadioBtn").addClass("active");
				$("#bereichRadioBtn").removeClass("active");
				$("#allIntDiv").fadeIn(350);
				$("#intBerDiv").slideUp(200);
				$("#histoBtn").hide(300);
				
				votings = msg.votings;
				var htmlString = "";
				if(votings != null){
					for (var i = 0; i < votings.length; i++) {
						var x = votings[i].koordX;
						var y = votings[i].koordY;
						htmlString += "<option class='' value='"+i+"'>"+(i+1)+": "+x+","+y+"</option>";
					}
				}
				$("#auswerteList").html(htmlString);
				$("#intBereichList").html("");
			}
		}
	}
	

};


//Functions

function befListUpdate(befList){
	
	var htmlString = "<option value='0'>--None--</option>";
	if(befList != null){
		if(befList.length > 0) htmlString = "<option value='0'>--All--</option>";
		for (var i = 0; i < befList.length; i++) {
			htmlString += "<option value='"+befList[i].id+"'>("+(i+1)+") "+befList[i].date+"</option>";
		}
	}
	$("#befList").html(htmlString);
	$("#befList").val(befId);
}

function updateFolienSatzList() {
	var htmlString = "";
	for (var i = 0; i < folienSatzList.length; i++) {
		htmlString += "<option class='folienSatzOption' value='"+folienSatzList[i].folienSatzID+"'>"+folienSatzList[i].name+"</option>";
	}
	$("#folienSatzListe").html(htmlString);
}
function updateFolien() {
	$("#heatplotCanvas").hide();
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	
	$("#folienNavAnzahl").html(folienList.length+" Seiten");
	
	
	var anzFolienVorher = $("#folienNavThumbsSlick div").length;
	for (var i = 0; i < anzFolienVorher; i++) {
		$("#folienNavThumbsSlick").slick('slickRemove', "");
	}
	
	for (var i = 0; i < folienList.length; i++) {
		var htmlString = "";
		var fId = folienList[i].folienID;
		htmlString = ""
		+'<div>'
	      +'<img class="folieThumbnail" src="ImgServlet?id='+fId+'&thumb=1" name="'+fId+'" alt="'+(i+1)+'">'
	      +'<div class="text-center">'+(i+1)+'</div>'
	    +'</div>';
		$("#folienNavThumbsSlick").slick('slickAdd', htmlString);
	}
	
	$(".folieThumbnail").removeClass("xAusgFolie");
	$(".folieThumbnail[name='"+aktiveFolienId+"']").addClass("xAktiveFolie");
	
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

function enableControls() {
	$("#useThisFoil").prop("disabled", false);
	$("#delThisFoil").prop("disabled", false);
	$("#interaktivControlsDiv").fadeIn(60);
	
	$("#notUseThisFoil").slideUp(220);
}

function disableControls() {
	$("#useThisFoil").prop("disabled", true);
	$("#delThisFoil").prop("disabled", true);
	$("#interaktivControlsDiv").fadeOut(180);
}

function changeFolienType(type) {
	var folienTypChange = {
			type : "folienTypChange",
			userId : userId,
			folienId : nowFolienId,
			folienTyp : type,
			sessionId : 0
		};
	var folienTypChangeJson = JSON.stringify(folienTypChange);
	socket.send(folienTypChangeJson);
}

function updateStudentsOnline() {
	$("#studentsOnline").html(studentsOnline+" Studenten Online");
}

function relToAbsX(rel) {
	var abs = (rel/100)*canvas.width;
	return abs;
}
function relToAbsY(rel) {
	var abs = (rel/100)*canvas.height;
	return abs;
}
function absToRelX(abs) {
	var rel = Math.round((abs / canvas.width) * 100);
	return rel;
}
function absToRelY(abs) {
	var rel = Math.round((abs / canvas.height) * 100);
	return rel;
}

function folienNavSelect(element){
	quitNewBereich();
	nowFolienId = $(element).prop("name");
	
	if(nowFolienId == aktiveFolienId){
		disableControls();
		$("#notUseThisFoil").slideDown(280);
	}
	else enableControls();
	
	var folienInfoRequest = {
			type : "folienInfoRequest",
			userId : userId,
			sessionId : 0,//sessionId,
			folienId : nowFolienId
		};
	var folienInfoRequestJson = JSON.stringify(folienInfoRequest);
	socket.send(folienInfoRequestJson);
	
	$(".folieThumbnail").removeClass("xAusgFolie");
	$(element).addClass("xAusgFolie");
}






//Onklick
//Folienatz laden
$('#folienSatzListe').change(function(e) {
	$("#fSatzLoeschenModalBtn").show();
	$("#notUseThisFoil").slideUp(280);
	quitNewBereich();
	nowfolienSatzId = $(this).children(":selected").val();
	
	var folienSatzRequest = {
			type : "folienSatzRequest",
			userId : userId,
			folienSatzId : nowfolienSatzId
		};
	var folienSatzRequestJson = JSON.stringify(folienSatzRequest);
	socket.send(folienSatzRequestJson);
});

$('#useThisFoil').click(function(e) {
	quitNewBereich();
	disableControls();
	$("#notUseThisFoil").slideDown(220);
	aktiveFolienId = nowFolienId;
	$(".folieThumbnail").removeClass("xAktiveFolie");
	$(".folieThumbnail").removeClass("xAusgFolie");
	$(".folieThumbnail[name='"+nowFolienId+"']").addClass("xAktiveFolie");
	
	var folienUpdateRequest = {
			type : "folienUpdateRequest",
			userId : userId,
			kursId : kursId,
			folienId : nowFolienId,
			sessionId : 0
		};
	var folienUpdateRequestJson = JSON.stringify(folienUpdateRequest);
	socket.send(folienUpdateRequestJson);
	
});
$('#delThisFoil').click(function(e) {
	quitNewBereich();
	disableControls();
	var folienDeleteRequest = {
			type : "folienDeleteRequest",
			userId : userId,
			kursId : kursId,
			folienId : nowFolienId
		};
	var folienDeleteRequestJson = JSON.stringify(folienDeleteRequest);
	socket.send(folienDeleteRequestJson);
	var folienSatzRequest = {
			type : "folienSatzRequest",
			userId : userId,
			folienSatzId : nowfolienSatzId
		};
	var folienSatzRequestJson = JSON.stringify(folienSatzRequest);
	socket.send(folienSatzRequestJson);
});

$('#intBereichList').change(function(e) {
	$('#delIntBereich').prop("disabled", false);
	
	ausgIntBereich = $(this).children(":selected").val();
});
$('#delIntBereich').click(function(e) {
	$('#delIntBereich').prop("disabled", true);
	
	var delBereich = {
			type : "delBereich",
			userId : userId,
			kursId : kursId,
			folienId : nowFolienId,
			sessionId : 0,
			bereichId : ausgIntBereich
		};
	var delBereichJson = JSON.stringify(delBereich);
	socket.send(delBereichJson);
});

$('#histoBtn').click(function(e) {
	if(aktuelleFolie.folienTyp == "C"){
		$("#chartContainer").html();
		
		var dataPoints = [];
		if(bAuswerteList != null){
			for (var i = 0; i < bAuswerteList.length; i++) {
				var o = { y: bAuswerteList[i], label: ""+(i+1) };
				dataPoints.push(o);
			}
		}
		
		var chart = new CanvasJS.Chart("chartContainer", {
		      animationEnabled: true,
		      legend: {
		        verticalAlign: "bottom",
		        horizontalAlign: "center"
		      },
		      theme: "theme1",
		      data: [{
				type: "column",
				dataPoints: dataPoints
			}]
		});
		chart.render();
	}
	
	else if(aktuelleFolie.folienTyp == "H"){
		$("#chartContainer").html();
		
	}
});

$('#notUseThisFoil').click(function(e) {
	aktiveFolienId = 0;
	enableControls();
	$(".folieThumbnail").removeClass("xAktiveFolie");
	$(".folieThumbnail[name='"+nowFolienId+"']").addClass("xAusgFolie");
	
	var folieInaktivieren = {
			type : "folieInaktivieren",
			userId : userId,
			kursId : kursId,
			folienId : nowFolienId,
			sessionId : 0
		};
	var folieInaktivierenJson = JSON.stringify(folieInaktivieren);
	socket.send(folieInaktivierenJson);
	
	var folienInfoRequest = {
			type : "folienInfoRequest",
			userId : userId,
			sessionId : 0,//sessionId,
			folienId : nowFolienId
		};
	var folienInfoRequestJson = JSON.stringify(folienInfoRequest);
	socket.send(folienInfoRequestJson);
	
});

$('#delFolienSatzBtn').click(function(e) {
	disableControls();
	
	var deleteFoliensatz = {
			type : "deleteFoliensatz",
			userId : userId,
			folienSatzId : nowfolienSatzId
		};
	var deleteFoliensatzJson = JSON.stringify(deleteFoliensatz);
	socket.send(deleteFoliensatzJson);
	
	location.reload(); 
});

$('#folienNavThumbsSlick').on('click', 'img', function(e) {
	folienNavSelect(this);
});
$('#backButton').click(function(e) {
	var currentIndex = $(".xAusgFolie").parent().index();
	if(currentIndex > 0){
		var toIndex = currentIndex-1;
		
		folienNavSelect($("img").eq(toIndex));
	}
});
$('#forwardButton').click(function(e) {
	var currentIndex = $(".xAusgFolie").parent().index();
	if(currentIndex < folienList.length-1){
		var toIndex = currentIndex+1;
		
		folienNavSelect($("img").eq(toIndex));
	}
});
	

//OnChange
$("#interaktivSwitch").change(function() {
	if(this.checked) {
		if($('input[name=intModus]:checked').val() == "Bereiche") {
			changeFolienType('C');
	    }
	    else if($('input[name=intModus]:checked').val() == "Heatplot") {
	    	changeFolienType('H');
	    }
		
        $("#allIntDiv").fadeIn(350);
	}
	else{
		changeFolienType('A');
		
		$("#allIntDiv").fadeOut(450);
	}
});

$("input[name=intModus]").change(function() {
    if($(this).val() == "Bereiche") {
		changeFolienType('C');
		
        $("#intBerDiv").slideDown(200);
        $("#histoBtn").show(300);
    }
    else if($(this).val() == "Heatplot") {
    	changeFolienType('H');
    	
        $("#intBerDiv").slideUp(200);
        $("#histoBtn").hide(300);
    }
});

$('#befList').change(function(e) {
	befId = $(this).children(":selected").val();
	
	var befRequest = {
			type : "befRequest",
			userId : userId,
			folienId : nowFolienId,
			befId : befId
		};
	var befRequestJson = JSON.stringify(befRequest);
	socket.send(befRequestJson);
});




//OnReady
$(document).ready(function(){
	$(".center").slick({
	  infinite: false,
	  slidesToShow: 4,
	  slidesToScroll: 3
	});
	
	disableControls();

	heatmap = createWebGLHeatmap({canvas: heatplotCanvas, intensityToAlpha:true});
});

$('img').on('dragstart', function(event) { event.preventDefault(); });


window.onbeforeunload = function (e) {
	endSocket();
};