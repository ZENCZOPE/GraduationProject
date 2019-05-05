function queryGameList(currPage) {
	var billType = $("#billType").val();
	var billNote = $("#billNote").val();
	var opId = $("#opId").val();
	var jsonStr = {
		"billType" : billType,
		"billNote" : billNote,
		"opId" : opId,
		"currPage" : currPage
	}
	realCommitLog("/CMS/account/financeList.do", jsonStr);
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
	var page = data.page;
	var accs = page.list;
	var content = "";
	// 表头
	content += "<table class='table'><tr><td>账单ID</td><td>账单类型</td><td>账单价格</td><td>账单备注</td>"
			+ "<td>负责人</td><td>操作时间</td><td>生效时间</td><td>账单状态</td></tr>";
	// 循环展示数据
	$.each(accs, function(i, item) {
		//时间格式转换
		var opTime = new Date(item.opTime).toLocaleString();
		var effectTime = new Date(item.effectTime).toLocaleString();
		item = stringToText(item);
			content += "<tr><td>" + item.billId + "</td>"
			+ "<td>" + item.billType + "</td>"
			+ "<td>" + item.price + "</td>"
			+ "<td>" + item.billNote + "</td>"
			+ "<td style='width:138px;'>" + item.opId + "</td>"
			+ "<td>" + opTime + "</td>"
			+ "<td>" + effectTime + "</td>"
			+ "<td>" + item.status + "</td></tr>";
	});
	// 表尾
	content += "</table>";
	content += '<div id="pagecontrol">';
	content += '<div class="pageinfo">';
	content += '共<span><b>' + page.totalpage + '</b></span>页，每页<span>'
			+ page.pagesize + '</span>条，当前是第<span><b>' + page.pagenum
			+ '</b></span>页';
	content += '</div>';

	content += '<div class="pagebar">';

	content += '<button onclick="queryGameList(1)"';
	if (page.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (page.pagenum > 1) {
		content += ' class="button"';
	}
	content += '>首页</button>';

	content += '<button onclick="queryGameList(' + (page.pagenum - 1) + ')"';
	if (page.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (page.pagenum > 1) {
		content += ' class="button"';
	}
	content += '>上页</button>';

	content += '<button onclick="queryGameList(' + (page.pagenum + 1) + ')"';
	if (page.pagenum == page.totalpage) {
		content += 'class="button"  disabled';
	} else if (page.pagenum < page.totalpage) {
		content += ' class="button"';
	}
	content += '>下页</button>';

	content += '<button onclick="queryGameList(' + page.totalpage + ')"';
	if (page.pagenum == page.totalpage) {
		content += 'class="button"  disabled';
	} else if (page.pagenum < page.totalpage) {
		content += ' class="button"';
	}
	content += '>末页</button>';
	
	content += '</span>';
	content += '</div>';
	content += '<div class="finance" style="width: 40%;float: right;">';
	content += '<span style="width: 200px;margin-top: 50px;padding-right: 0px;">';
	content += '总收入:' + data.income + '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp总支出:' + data.pay + '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp总利润:' + data.sum;
	content += '</div>';
	content += '</div>';

	// 展示数据
	$("#dataList").html(content);
}

function stringToText(item) {
	if(item.status=='N') {
		item.status = '未生效';
	}else if(item.status=='K') {
		item.status = '生效中';
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