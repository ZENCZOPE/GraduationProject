function queryGameList(currPage) {
	var gameName = $("#gameName").val();
	var gameLevel = $("#gameLevel").val();
	var gameAddr = $("#gameAddr").val();
	var jsonStr = {
		"gameName" : gameName,
		"gameLevel" : gameLevel,
		"gameAddr" : gameAddr,
		"currPage" : currPage
	}
	realCommitLog("/CMS/game/ajaxGetGameList.do", jsonStr);
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
	var games = data.list;
	var content = "";
	// 表头
	content += "<table class='table'><tr><td>赛程名称</td><td>赛程等级</td><td>赛程地址</td>"
			+ "<td>赛程日期</td><td>结束日期</td><td>参加人数</td><td>晋级人数</td><td>赛程状态</td></tr>";
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
				+ "<td>" + item.gameStatus + "</td></tr>";
	});
	// 表尾
	content += "</table>";
	content += '<div id="pagecontrol">';
	content += '<div class="pageinfo">';
	content += '共<span><b>' + data.totalpage + '</b></span>页，每页<span>'
			+ data.pagesize + '</span>条，当前是第<span><b>' + data.pagenum
			+ '</b></span>页';
	content += '</div>';

	content += '<div class="pagebar">';

	content += '<button onclick="queryGameList(1)"';
	if (data.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (data.pagenum > 1) {
		content += ' class="button";'
	}
	content += '>首页</button>';

	content += '<button onclick="queryGameList(' + (data.pagenum - 1) + ')"';
	if (data.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (data.pagenum > 1) {
		content += ' class="button";'
	}
	content += '>上页</button>';

	content += '<button onclick="queryGameList(' + (data.pagenum + 1) + ');"';
	if (data.pagenum == data.totalpage) {
		content += 'class="button"  disabled';
	} else if (data.pagenum < data.totalpage) {
		content += ' class="button";'
	}
	content += '>下页</button>';

	content += '<button onclick="queryGameList(' + data.totalpage + ');"';
	if (data.pagenum == data.totalpage) {
		content += 'class="button"  disabled';
	} else if (data.pagenum < data.totalpage) {
		content += ' class="button";'
	}
	content += '>末页</button>';

	content += '</div>';
	content += '</div>';

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