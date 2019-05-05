function queryDeviceList(currPage) {
	var iemi = $("#iemi").val();
	var socImei = $("#socImei").val();
	var opDate = $("#opDate").val();
	var jsonStr = {
		"iemi" : iemi,
		"socImei" : socImei,
		"opDate" : opDate,
		"currPage" : currPage
	}
	realCommitLog("/CMS/device/deviceUsePage.do", jsonStr);
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
	})
}

function showData(data) {
	var users = data.list;
	var content = "";
	// 表头
	content += "<table class='table'><tr>"
		+ "<td>设备序列号</td><td>设备名称</td><td>添加时间</td><td>分配</td></tr>";
	// 循环展示数据
	$.each(users, function(i, item) {
		var createDate = new Date(item.createDate).toLocaleString();
		content += "<tr><td>" + item.iemi + "</td>"
		+ "<td>" + item.socImei+ "</td>"
		+ "<td>" + createDate + "</td>"
		+ "<td><button style='width: 70px;' onclick='chooseWorker(\"" + item.iemi + "\")'>选择员工</button>"
		+ "<button style='width: 70px;' onclick='chooseJudge(\"" + item.iemi + "\")'>选择评委</button></td></tr>";
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

	content += '<button onclick="queryDeviceList(1);"';
	if (data.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (data.pagenum > 1) {
		content += ' class="button";'
	}
	content += '>首页</button>';

	content += '<button onclick="queryDeviceList(' + (data.pagenum - 1) + ');"';
	if (data.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (data.pagenum > 1) {
		content += ' class="button";'
	}
	content += '>上页</button>';

	content += '<button onclick="queryDeviceList(' + (data.pagenum + 1) + ');"';
	if (data.pagenum == data.totalpage) {
		content += 'class="button"  disabled';
	} else if (data.pagenum < data.totalpage) {
		content += ' class="button";'
	}
	content += '>下页</button>';

	content += '<button onclick="queryDeviceList(' + data.totalpage + ');"';
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

function chooseWorker(iemi) {
	parent.layer.open({
		title : [ '设备序列号：'+ iemi, 'font-weight:bold;' ],
		type : 2,
		area : [ '1000px', '600px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/device/getDeviceToWorker.do?iemi=' + iemi
	});
}

function chooseJudge(iemi) {
	parent.layer.open({
		title : [ '设备序列号：'+ iemi, 'font-weight:bold;' ],
		type : 2,
		area : [ '1000px', '600px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/device/getDeviceToJudge.do?iemi=' + iemi
	});
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