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
	realCommitLog("/CMS/device/deviceTroublePage.do", jsonStr);
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
		+ "<td>设备序列号</td><td>设备名称</td><td>添加时间</td><td>操作时间</td>"
		+ "<td>设备状态</td><td>负责人ID</td><td>停用/报损</td></tr>";
	// 循环展示数据
	$.each(users, function(i, item) {
		item = stringToText(item);
		var createDate = new Date(item.createDate).toLocaleString();
		var opDate = new Date(item.opDate).toLocaleString();
		content += "<tr><td>" + item.iemi + "</td>"
		+ "<td>" + item.socImei+ "</td>"
		+ "<td>" + createDate + "</td>"
		+ "<td>" + opDate + "</td>"
		+ "<td>" + item.status + "</td>"
		+ "<td>" + item.userId + "</td>"
		+ "<td><button style='width: 70px;' onclick='deviceOff(\"" + item.iemi + "\")'>停用</button>"
		+ "<button style='width: 70px;' onclick='deviceDamage(\"" + item.iemi + "\")'>报损</button></tr>";
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

function deviceOff(iemi) {
	layer.confirm(
		'是否要停用该设备？',
		{btn: ['确定','取消']},
		function(){
			var String = { "iemi": iemi };
			$.ajax({
				data: String,
			
				type: "POST",
				
				dataType: 'json',
				
				url: "/CMS/device/ajaxDeviceOff.do",
				
				success: function(data) {
					layer.msg(data.msg,{time:2000});
				},
				error: function() {
					layer.msg("操作异常，请联系管理员！",{time: 2000});
				}
			});
		}
	);
}

function deviceDamage(iemi) {
	layer.confirm(
		'是否要报损该设备？',
		{btn: ['确定','取消']},
		function(){
			var String = { "iemi": iemi };
			$.ajax({
				data: String,
			
				type: "POST",
				
				dataType: 'json',
				
				url: "/CMS/device/ajaxDeviceDamage.do",
				
				success: function(data) {
					layer.msg(data.msg,{time:2000});
				},
				error: function() {
					layer.msg("操作异常，请联系管理员！",{time: 2000});
				}
			});
		}
	);
}

function stringToText(item) {
	if(item.status=='B') {
		item.status = '损坏';
	}else if(item.status=='F') {
		item.status = '空闲';
	}else if(item.status=='U') {
		item.status = '使用中';
	}
	return item;
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