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

var secCanvas = document.createElement("canvas");
secCtx = secCanvas.getContext("2d");



function quitNewBereich() {
	breed = false;
	inNewBerMode = false;
	$('#newIntBereich').prop("disabled", false);
	
	$('#delIntBereich').prop("disabled", true);
}

$('#newIntBereich').click(function(e) {
	if(!inNewBerMode){
		inNewBerMode = true;
		$(this).prop("disabled", true);
		ctx.globalAlpha = 0.2;
		ctx.fillStyle="#F4FF4F";
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
		
		secCanvas.width = canvas.width;
		secCanvas.height = canvas.height;
		secCtx.drawImage(canvas, 0, 0);
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
			ctx.drawImage(secCanvas, 0, 0);
		    ctx.globalAlpha = 0.4;
		    ctx.fillStyle="#d000ff";
			ctx.fillRect(berStartX, berStartY, tempX-berStartX, tempY-berStartY);
		}
	}
});
$("#folieCanvas").mouseup(function(e) {
	if(inNewBerMode){
		breed = false;
		var passt = true;
		
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
		else{
			absOLX = 0;
			absOLY = 0;
			absURX = 0;
			absURY = 0;
		}
		
		oLX = absToRelX(absOLX);
		oLY = absToRelY(absOLY);
		uRX = absToRelX(absURX);
		uRY = absToRelY(absURY);
	
		console.log("Rel: "+oLX+","+oLY+";"+uRX+","+uRY);
		
		
		
		if(oLX == uRX || oLY == uRY){
			passt = false;
		}
		
		//Kollision mit anderen Bereichen
		for (var i = 0; i < bereichList.length; i++) {
			var zoLX = bereichList[i].obenLinksX;
			var zoLY = bereichList[i].obenLinksY;
			var zuRX = bereichList[i].untenRechtsX;
			var zuRY = bereichList[i].untenRechtsY;
			
			//RechtsUntere Ecke
			if(oLX > zoLX && oLX < zuRX){
				if(oLY > zoLY && oLY < zuRY){
					passt = false;
				}
			}
			//LinksObere Ecke
			if(uRX > zoLX && uRX < zuRX){
				if(uRY > zoLY && uRY < zuRY){
					passt = false;
				}
			}
			//LinksUntere Ecke
			if(uRX > zoLX && uRX < zuRX){
				if(oLY > zoLY && oLY < zuRY){
					passt = false;
				}
			}
			//RechtsObere Ecke
			if(oLX > zoLX && oLX < zuRX){
				if(uRY > zoLY && uRY < zuRY){
					passt = false;
				}
			}
			
			//ganzes größeres
			if(oLX < zoLX && uRX > zuRX){
				if(oLY < zoLY && uRY > zuRY){
					passt = false;
				}
			}
			//ganzes kleineres
			if(oLX > zoLX && uRX < zuRX){
				if(oLY > zoLY && uRY < zuRY){
					passt = false;
				}
			}
			
			//Rechts größer
			if(oLX > zoLX && oLX < zuRX){
				if(oLY < zoLY && uRY > zuRY){
					passt = false;
				}
			}
			//Rechts kleiner
			if(oLX > zoLX && oLX < zuRX){
				if(oLY > zoLY && uRY < zuRY){
					passt = false;
				}
			}
			
			//Links größer
			if(uRX > zoLX && uRX < zuRX){
				if(oLY < zoLY && uRY > zuRY){
					passt = false;
				}
			}
			//Links kleiner
			if(uRX > zoLX && uRX < zuRX){
				if(oLY > zoLY && uRY < zuRY){
					passt = false;
				}
			}
			
			//Oben größer
			if(oLX < zoLX && uRX > zuRX){
				if(oLY < zoLY && uRY > zoLY){
					passt = false;
				}
			}
			//Oben kleiner
			if(oLX > zoLX && uRX < zuRX){
				if(oLY < zoLY && uRY > zoLY){
					passt = false;
				}
			}
			
			//Unten größer
			if(oLX < zoLX && uRX > zuRX){
				if(oLY < zuRY && uRY > zuRY){
					passt = false;
				}
			}
			//Unten kleiner
			if(oLX > zoLX && uRX < zuRX){
				if(oLY < zuRY && uRY > zuRY){
					passt = false;
				}
			}
			
		}
		
		if(passt){
			var newBereich = {
					type : "newBereich",
					userId : userId,
					kursId : kursId,
					folienId : nowFolienId,
					sessionId : 0,
					oLX : oLX,
					oLY : oLY,
					uRX : uRX,
					uRY : uRY
				};
			var newBereichJson = JSON.stringify(newBereich);
			socket.send(newBereichJson);
		}
		if(!passt){
			var folienInfoRequest = {
					type : "folienInfoRequest",
					userId : userId,
					sessionId : 0,//sessionId,
					folienId : nowFolienId
				};
			var folienInfoRequestJson = JSON.stringify(folienInfoRequest);
			socket.send(folienInfoRequestJson);
		}
		
		
		inNewBerMode = false;
		$('#newIntBereich').prop("disabled", false);
	}
});