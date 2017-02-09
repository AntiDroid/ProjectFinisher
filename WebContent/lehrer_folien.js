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

