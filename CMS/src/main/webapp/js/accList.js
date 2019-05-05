function queryGameList(currPage) {
	var billType = $("#billType").val();
	var billNote = $("#billNote").val();
	var status = $("#status").val();
	var jsonStr = {
		"billType" : billType,
		"billNote" : billNote,
		"status" : status,
		"currPage" : currPage
	}
	realCommitLog("/CMS/account/queryAccList.do", jsonStr);
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
	var data = data.page;
	var accs = data.list;
	var content = "";
	// 表头
	content += "<table class='table'><tr><td>账单ID</td><td>账单类型</td><td>账单价格</td><td>账单备注</td>"
			+ "<td>负责人</td><td>操作时间</td><td>生效时间</td><td>账单状态</td><td>修改/删除</td></tr>";
	// 循环展示数据
	$.each(accs, function(i, item) {
		//时间格式转换
		var opTime = new Date(item.opTime).toLocaleString();
		var effectTime = new Date(item.effectTime).toLocaleString();
		item = stringToText(item);
		if(item.status == '未生效') {
			content += "<tr><td>" + item.billId + "</td>"
			+ "<td>" + item.billType + "</td>"
			+ "<td>" + item.price + "</td>"
			+ "<td>" + item.billNote + "</td>"
			+ "<td style='width:138px;'>" + item.opId + "</td>"
			+ "<td>" + opTime + "</td>"
			+ "<td>" + effectTime + "</td>"
			+ "<td>" + item.status + "</td>"
			+ "<td><button onclick='accChange(" + item.billId + ")'>修改</button>"
			+ "<button onclick='accDelete(" + item.billId + ")'>作废</button></td></tr>";
		}else {
			content += "<tr><td>" + item.billId + "</td>"
			+ "<td>" + item.billType + "</td>"
			+ "<td>" + item.price + "</td>"
			+ "<td>" + item.billNote + "</td>"
			+ "<td style='width:138px;'>" + item.opId + "</td>"
			+ "<td>" + opTime + "</td>"
			+ "<td>" + effectTime + "</td>"
			+ "<td>" + item.status + "</td>"
			+ "<td style='padding: 8px;'>-</td></tr>";
		}
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

function accChange(billId) {
	var index = parent.layer.open({
		title : [ '账单ID: ' + billId, 'font-weight:bold;' ],
		type : 2,
		area : [ '800px', '500px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/account/getAccChangePage.do?billId=' + billId
	});
}

function accDelete(billId) {
	layer.confirm(
		'是否要作废该账单？',
		{btn: ['确定','取消']},
		function(){
			var String = { "billId": billId };
			$.ajax({
				
				data: String,
			
				type: "POST",
				
				dataType: 'json',
				
				url: "/CMS/account/stopAcc.do",
				
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
	if(item.status=='N') {
		item.status = '未生效';
	}else if(item.status=='K') {
		item.status = '已生效';
	}else if(item.status=='E') {
		item.status = '已出账';
	}else if(item.status=='D') {
		item.status = '已作废';
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

/*//时间格式转换
function strFormat(str) {
	var date=new Date(str);
	date.setFullYear(1970,1,1);
	date.setTime(str);
	var setDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+"点 "+date.getMinutes()+"分";
	return setDate;
}*/