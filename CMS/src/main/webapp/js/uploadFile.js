function upload() {
	// 获取文件对象
	var fileObj = document.getElementById('file').files[0];
	var outName = document.getElementById('file').value.toLowerCase().split('.').splice(-1);
	if(fileObj == null) {
		layer.msg('请选择上传文件', {time:2000});
	}else if(outName != 'zip') {
		layer.msg('请上传后缀名为zip的压缩文件', {time:5000});
	}else {
		document.getElementById('progressBar').style.display='block';
		// 接收上传文件的后台地址 
		var FileController = '/CMS/file/Upload.do';
		// FormData 对象
		var form = new FormData();
		// 文件对象
		form.append("file", fileObj);
		// XMLHttpRequest 对象
		var xhr = new XMLHttpRequest();
		xhr.open("post", FileController, true);
		xhr.onload = function () {
			layer.msg('上传成功', {time:2000});
			};
			xhr.upload.addEventListener('progress', progressFunction, false);
			xhr.send(form);
	}
}

function progressFunction(evt) {

    var progressBar = document.getElementById('progressBar');

    var percentageDiv = document.getElementById('percentage');

    if (evt.lengthComputable) {

        progressBar.max = evt.total;

        progressBar.value = evt.loaded;

        percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + '%';

    }

}

function move(a) {
	a.style.transition = 'all 0.6s';
}

function selectFile() {
	document.getElementById('file').click();
}

function getFileURL(node) {
	var a = document.getElementById('file').value.toLowerCase();
	document.getElementById('path').innerHTML = a;
}