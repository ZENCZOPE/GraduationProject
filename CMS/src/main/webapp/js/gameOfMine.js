function queryMyGame(currPage) {
	var gameName = $("#gameName").val();
	var gameLevel = $("#gameLevel").val();
	var gameAddr = $("#gameAddr").val();
	var jsonStr = {
		"gameName" : gameName,
		"gameLevel" : gameLevel,
		"gameAddr" : gameAddr,
		"currPage" : currPage
	}
	realCommitLog("/CMS/game/ajaxMyGameList.do", jsonStr);
}

function realCommitLog(urls, string) {
	$.ajax({

		data : null,

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
	if(data.userRole=='judge') {
		content += "<table class='table'><tr><td>赛程名称</td><td>赛程等级</td><td>赛程地址</td><td>赛程日期</td>"
			+ "<td>结束日期</td><td>参加人数</td><td>晋级人数</td><td>赛程状态</td><td>分数</td></tr>";
	}else {
		content += "<table class='table'><tr><td>赛程名称</td><td>赛程等级</td><td>赛程地址</td><td>赛程日期</td>"
			+ "<td>结束日期</td><td>参加人数</td><td>晋级人数</td><td>赛程状态</td><td>我的结果</td><td>分数</td></tr>";
	}
	
	// 循环展示数据
	$.each(games, function(i, item) {
		//时间格式转换
		var gameDate = new Date(item.gameDate).toLocaleString();
		var gameEndDate = new Date(item.gameEndDate).toLocaleString();
		item=gameConvert(item);
		content += "<tr><td>" + item.gameName + "</td>"
				+ "<td>" + item.gameLevel + "</td>"
				+ "<td>" + item.gameAddr + "</td>"
				+ "<td style='width:138px;'>" + gameDate + "</td>"
				+ "<td style='width:138px;'>" + gameEndDate + "</td>"
				+ "<td>" + item.gameJoinCount + "</td>"
				+ "<td style='width:100px;'>" + item.gameUpCount + "</td>"
				+ "<td>" + item.gameStatus + "</td>";
		if(data.userRole=='player') {
			content	+= "<td>" + item.userStatus + "</td>";
		}
		if(item.userGrade==null&&parseDouble(item.userGrade)==-999.9) {
			content += "<td>" + '无' + "</td>";
		}else {
			content += "<td><button style='width: 80px;' onclick='detail(\"" + item.gameId + "\")'>详细情况</button></td>";
		}
		content += "</tr>";
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
		game.userStatus = '待定';
		break;
	case '3':
		game.gameStatus = '进行状态';
		game.userStatus = '待定';
		break;
	case '4':
		game.gameStatus = '结束状态';
		if(game.userStatus>0) {
			game.userStatus = '晋级';
		}else {
			game.userStatus = '淘汰';
		}
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

function detail(gameId) {
	parent.layer.open({
		title : [ '详细情况', 'font-weight:bold;' ],
		type : 2,
		area : [ '380px', '300px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/game/getGameDetailPage.do?gameId=' + gameId
	});
}

/*//时间格式转换
function strFormat(str) {
	var date=new Date(str);
	date.setFullYear(1970,1,1);
	date.setTime(str);
	var setDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+"点 "+date.getMinutes()+"分";
	return setDate;
}*/