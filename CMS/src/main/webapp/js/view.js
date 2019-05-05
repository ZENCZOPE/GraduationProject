$(document).ready(function() {

		  
		$(".春").css("background-color", "#00FF00");
		$(".夏").css("background-color", "#FF0000");
		$(".秋").css("background-color", "#FFFF00");
		$(".冬").css("background-color", "#C8C8C8");
		$("#hide1").hide();
		$("#shoe").hide();
	});
	function show1() {
		if ($("#hide1").css('display') == 'none') {
			$("#hide1").show();

		}

		else {
			$("#hide1").hide();

		}
	}
	function show2() {
		if ($("#shoe").css('display') == 'none') {
			$("#shoe").show();
		} else {
			$("#shoe").hide();
		}
	}