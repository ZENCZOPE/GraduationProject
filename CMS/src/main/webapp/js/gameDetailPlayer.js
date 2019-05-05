function queryMyGame(currPage) {
	var gameName = $("#gameName").val();
	var gameLevel = $("#gameLevel").val();
	var gameAddr = $("#gameAddr").val();
	var gameId = $("#gameId").val();
	var jsonStr = {
		"gameName" : gameName,
		"gameLevel" : gameLevel,
		"gameAddr" : gameAddr,
		"currPage" : currPage,
		"gameId" : gameId
	}
	realCommitLog("/CMS/game/ajaxGameDetail.do", jsonStr);
}

function realCommitLog(urls, string) {
	$.ajax({

		data : string,

		type : "POST",

		dataType : 'json',

		url : urls,

		success : function(data) {
			showData(data);
		},
		error : function(data) {
			alert(data.msg);
		}
	});
}

function showData(data) {
	var games = data.games;
	var content = "";
	//表头
	content += "<table class='table'><tr><td>选手ID</td><td>平均分数</td></tr>";
	// 循环展示数据
	$.each(games, function(i, item) {
		//时间格式转换
		var gameDate = new Date(item.gameDate).toLocaleString();
		var gameEndDate = new Date(item.gameEndDate).toLocaleString();
		item=gameConvert(item);
		content += "<tr><td>" + item.playerId + "</td>"
				+ "<td>" + item.userGrade + "</td></tr>";
		});
	
	// 表尾
	content += "</table>";
	
	// 展示数据
	$("#dataList").html(content);
}

function gameConvert(game) {
	switch (game.gameStatus) {
	case '0':
		game.gameStatus = '注销状态';
		break;
	case '1':
		game.gameStatus = '新建状态';
		break;
	case '2':
		game.gameStatus = '准备状态';
		break;
	case '3':
		game.gameStatus = '进行状态';
		break;
	case '4':
		game.gameStatus = '结束状态';
		break;
	}
	return game;
}

//时间格式转换
Date.prototype.toLocaleString = function() {
	var month = this.getMonth() + 1;
	var mon = month < 10 ? '0' + month : '' + month;
	var days = this.getDate();
	var day = days < 10 ? '0' + days : '' + days;
	var hours = this.getHours();
	var hou = hours < 10 ? '0' + hours : '' + hours;
	var minutes = this.getMinutes();
	var min = minutes < 10 ? '0' + minutes : '' + minutes;
    return this.getFullYear() + "/" + mon + "/" + day + " " + hou + ":" + min;
};

/*//时间格式转换
function strFormat(str) {
	var date=new Date(str);
	date.setFullYear(1970,1,1);
	date.setTime(str);
	var setDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+"点 "+date.getMinutes()+"分";
	return setDate;
}*/