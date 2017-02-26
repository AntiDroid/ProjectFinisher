var inNewBerMode = false;
var breed = false;

var berStartX = 0;
var berStartY = 0;
var berEndX = 0;
var berEndY = 0;

var absOLX = 0;
var absOLY = 0;
var absURX = 0;
var absURY = 0;

var oLX = 0;
var oLY = 0;
var uRX = 0;
var uRY = 0;

function quitNewBereich() {
	breed = false;
	inNewBerMode = false;
	$('#newIntBereich').prop("disabled", false);
}

$('#newIntBereich').click(function(e) {
	if(!inNewBerMode){
		inNewBerMode = true;
		$(this).prop("disabled", true);
		ctx.globalAlpha = 0.3;
		ctx.fillStyle="#ffdd00";
		ctx.fillRect(0, 0, canvas.width, canvas.height);
	}
	else{
		inNewBerMode = false;
	}
});

$("#folieCanvas").mousedown(function(e) {
	if(inNewBerMode){
		breed = true;
		
		var offset_x = $(this).offset().left - $(window).scrollLeft();
		var offset_y = $(this).offset().top - $(window).scrollTop();
	
		berStartX = (e.clientX - offset_x);
		berStartY = (e.clientY - offset_y);
	}
});
$("#folieCanvas").mousemove(function(e) {
	if(inNewBerMode){
		if(breed){
			var offset_x = $(this).offset().left - $(window).scrollLeft();
			var offset_y = $(this).offset().top - $(window).scrollTop();
		
			var tempX = (e.clientX - offset_x);
			var tempY = (e.clientY - offset_y);
	
			//RECHTECK ZEICHNEN
		    ctx.globalAlpha = 0.4;
		    ctx.fillStyle="#d000ff";
			ctx.fillRect(berStartX, berStartY, tempX-berStartX, tempY-berStartY);
		}
	}
});
$("#folieCanvas").mouseup(function(e) {
	if(inNewBerMode){
		breed = false;
		
		var offset_x = $(this).offset().left - $(window).scrollLeft();
		var offset_y = $(this).offset().top - $(window).scrollTop();
	
		berEndX = (e.clientX - offset_x);
		berEndY = (e.clientY - offset_y);
		
		//LinksOben nach RechtsUnten
		if(berStartX < berEndX && berStartY < berEndY){
			absOLX = berStartX;
			absOLY = berStartY;
			absURX = berEndX;
			absURY = berEndY;
		}
		//RechtsUnten nach LinksOben
		else if(berStartX > berEndX && berStartY > berEndY){
			absOLX = berEndX;
			absOLY = berEndY;
			absURX = berStartX;
			absURY = berStartY;
		}
		//LinksUnten nach RechtsOben
		else if(berStartX < berEndX && berStartY > berEndY){
			absOLX = berStartX;
			absOLY = berEndY;
			absURX = berEndX;
			absURY = berStartY;
		}
		//RechtsOben nach LinksUnten
		else if(berStartX > berEndX && berStartY < berEndY){
			absOLX = berEndX;
			absOLY = berStartY;
			absURX = berStartX;
			absURY = berEndY;
		}
		
		oLX = absToRelX(absOLX);
		oLY = absToRelY(absOLY);
		uRX = absToRelX(absURX);
		uRY = absToRelY(absURY);
	
		console.log("Rel: "+oLX+","+oLY+";"+uRX+","+uRY);
		
		//Kollision mit anderem Bereichen
		for (var i = 0; i < bereichList.length; i++) {
			var zoLX = bereichList[i].obenLinksX;
			var zoLY = bereichList[i].obenLinksY;
			var zuRX = bereichList[i].untenRechtsX;
			var zuRY = bereichList[i].untenRechtsY;
			
			//...
		}
		
		var newBereich = {
				type : "newBereich",
				userId : userId,
				kursId : kursId,
				folienId : nowFolienId,
				sessionId : sessionId,
				oLX : oLX,
				oLY : oLY,
				uRX : uRX,
				uRY : uRY
			};
		var newBereichJson = JSON.stringify(newBereich);
		socket.send(newBereichJson);
		
		inNewBerMode = false;
		$('#newIntBereich').prop("disabled", false);
	}
});