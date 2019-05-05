function queryDeviceList(currPage) {
	var iemi = $("#iemi").val();
	var socImei = $("#socImei").val();
	var status = $("#status").val();
	if(status=='未损坏') {
		status = 0;
	}else if(status=='已损坏') {
		status = 1;
	}
	var jsonStr = {
		"iemi" : iemi,
		"status" : status,
		"socImei" : socImei,
		"currPage" : currPage
	}
	realCommitLog("/CMS/device/ajaxGetDeviceList.do", jsonStr);
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
	var devices = data.list;
	var content = "";
	// 表头
	content += "<table class='table'><tr><td>设备序列号</td><td>设备名称</td><td>设备状态</td><td style='width:140px;'>设备创建时间</td>"
			+ "<td style='width:100px;'>设备操作时间</td><td style='width:80px;'>负责人ID</td><td>设备操作</td></tr>";
	// 循环展示数据
	$.each(devices, function(i, item) {
		//时间格式转换
		var createDate = new Date(item.createDate).toLocaleString();
		var opDate = new Date(item.opDate).toLocaleString();
		content += "<tr><td>" + item.iemi + "</td>"
				+ "<td>" + item.socImei + "</td>"
				+ "<td>" + item.status + "</td>"
				+ "<td>" + createDate + "</td>"
				+ "<td>" + opDate + "</td>"
				+ "<td>" + item.userId + "</td>"
				+ "<button onclick='deviceDistribute(\"" + item.iemi + "\")'>分配</button>" 
				+ "<button onclick='deviceOff(\"" + item.iemi + "\")'>停用</button>" 
				+ "<button onclick='deviceDamage(\"" + item.iemi + "\")'>报损</button></td></tr>";
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

function deviceDistribute(iemi) {
	var index = parent.layer.open({
		title : [ '设备序列号: ' + iemi, 'font-weight:bold;' ],
		type : 2,
		area : [ '800px', '500px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/device/getDeviceDistributePage.do?iemi=' + iemi
	});
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