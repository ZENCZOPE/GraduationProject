function queryAdList(currPage) {
	var adTitle = $("#adTitle").val();
	var adNote = $("#adNote").val();
	var adOwner = $("#adOwner").val();
	var jsonStr = {
		"adTitle" : adTitle,
		"adNote" : adNote,
		"adOwner" : adOwner,
		"currPage" : currPage
	}
	realCommitLog("/CMS/ad/adList.do", jsonStr);
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
	var ads = data.list;
	var content = "";
	// 表头
	content += "<table class='table'><tr><td>广告ID</td><td>广告标题</td><td>广告备注</td><td>广告位置</td>"
			+ "<td>广告价格</td><td>所属公司</td><td>生效时间</td><td>结束时间</td><td>广告状态</td><td>最近操作工号</td><td>修改/停用</td></tr>";
	// 循环展示数据
	$.each(ads, function(i, item) {
		//时间格式转换
		var adEffectTime = new Date(item.adEffectTime).toLocaleString();
		var adEndTime = new Date(item.adEndTime).toLocaleString();
		item = stringToText(item);
		content += "<tr><td>" + item.adId + "</td>"
				+ "<td>" + item.adTitle + "</td>"
				+ "<td>" + item.adNote + "</td>"
				+ "<td>" + item.adAddr + "</td>"
				+ "<td style='width:138px;'>" + item.adPrice + "</td>"
				+ "<td style='width:138px;'>" + item.adOwner + "</td>"
				+ "<td>" + adEffectTime + "</td>"
				+ "<td style='width:100px;'>" + adEndTime + "</td>"
				+ "<td>" + item.adStatus + "</td>"
				+ "<td>" + item.opId + "</td>"
				+ "<td><button onclick='adChange(" + item.adId + ")'>修改</button>"
				+ "<button onclick='adStop(" + item.adId + ")'>停用</button></td></tr>";
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

	content += '<button onclick="queryAdList(1)"';
	if (data.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (data.pagenum > 1) {
		content += ' class="button";'
	}
	content += '>首页</button>';

	content += '<button onclick="queryAdList(' + (data.pagenum - 1) + ')"';
	if (data.pagenum == 1) {
		content += 'class="button"  disabled';
	} else if (data.pagenum > 1) {
		content += ' class="button";'
	}
	content += '>上页</button>';

	content += '<button onclick="queryAdList(' + (data.pagenum + 1) + ');"';
	if (data.pagenum == data.totalpage) {
		content += 'class="button"  disabled';
	} else if (data.pagenum < data.totalpage) {
		content += ' class="button";'
	}
	content += '>下页</button>';

	content += '<button onclick="queryAdList(' + data.totalpage + ');"';
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

function adChange(adId) {
	var index = parent.layer.open({
		title : [ '广告ID: ' + adId, 'font-weight:bold;' ],
		type : 2,
		area : [ '900px', '550px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : '/CMS/ad/getAdChange.do?adId=' + adId
	});
}

function adStop(adId) {
	layer.confirm(
		'是否要停用该广告？',
		{btn: ['确定','取消']},
		function(){
			var String = { "adId": adId };
			$.ajax({
				
				data: String,
			
				type: "POST",
				
				dataType: 'json',
				
				url: "/CMS/ad/adStop.do",
				
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
	if(item.adStatus=='N') {
		item.adStatus = '未生效';
	}else if(item.adStatus=='K') {
		item.adStatus = '生效中';
	}else if(item.adStatus=='E') {
		item.adStatus = '已失效';
	}else if(item.adStatus=='D') {
		item.adStatus = '已停用';
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