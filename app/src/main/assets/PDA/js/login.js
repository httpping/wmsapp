$(function(){
	/* 系统文件检查
	layer.open({
		type: 2,content: '正在检查文件系统'
	});
	setTimeout(function () {cklogin();}, 1000);
	*/
});

function login(){
	var username =$("#username").val();
		var pass= $("#pass").val();
		if(username==""){
			layer.open({
				content: '请输入用户名'
		  });
		  return  false;
		}
		if(pass==""){
			layer.open({
				content: '请输入密码'
		  });
		  return  false;
		};
	jsReadFiles(username,pass);
	
}

function jsReadFiles(username,pass){
	var dataobj =readfile("login.json");
    console.info(dataobj)
	dataobj = JSON.parse(dataobj);

	//var dataobj =JSON.stringify(result);
	if(dataobj!=null && dataobj!=""){
		if(dataobj.code=="200"){
			var result =JSON.parse(dataobj.data);
			console.info(dataobj.data)
	        console.info(dataobj.data.username)
			if(username==result.username && pass==result.pass){
				    sessionStorage.removeItem('username');
				    sessionStorage.setItem('username',username);
				    window.location.href='home.html';
			   }else{
				   layer.open({content: '用户名或者密码错误'});
			   }
		}else{
			return "系统错误，错误原因是:"+dataobj.error;
		}
	}
}
function cklogin(){
	var logindata =readfile("login.json");
	if(logindata.length<=0){
		layer.open({
			type: 2,content: '正在生成登录文件'
		});
		var resule_write=writefile("login.json","");
		if(resule_write=="success"){
			setTimeout(function () {ckinbound();}, 1000);
		}
	}
}


function ckinbound(){
	var logindata =readfile("task.json");
	if(logindata.length<=0){
		layer.open({
		type: 2,content: '正在生成任务文件'
		});
	}
	var resule_write=writefile("task.json","");
		if(resule_write=="success"){
			//setTimeout(function () {ckoutbound();}, 1000);
		}
}


//检查入库
/*
function ckinbound(){
	var logindata =readfile("inbound.json");
	if(logindata.length<=0){
		layer.open({
		type: 2,content: '正在生成入库文件'
		});
	}
	var resule_write=writefile("inbound.json","");
		if(resule_write=="success"){
			setTimeout(function () {ckoutbound();}, 1000);
		}
}

//检查出库
function ckoutbound(){
	var logindata =readfile("outbound.json");
	if(logindata.length<=0){
		layer.open({
		type: 2,content: '正在生成出库文件'
		});
	}
	var resule_write=writefile("outbound.json","");
		if(resule_write=="success"){
			setTimeout(function () {checkfile();}, 1000);
		}
}

//检查盘点
function ckcheck(){
	var logindata =readfile("check.json");
	if(logindata.length<=0){
		layer.open({
		type: 2,content: '正在生成盘点文件'
		});
	}
	var resule_write=writefile("check.json","");
		if(resule_write=="success"){
			setTimeout(function () {checkfile();}, 1000);
		}
}

//检查移库
function ckoutbound(){
	var logindata =readfile("move.json");
	if(logindata.length<=0){
		layer.open({
		type: 2,content: '正在生成移库文件'
		});
	}
	var resule_write=writefile("move.json","");
		if(resule_write=="success"){
			setTimeout(function () {checkfile();}, 1000);
		}
}

//检查调拨
function ckoutbound(){
	var logindata =readfile("allot.json");
	if(logindata.length<=0){
		layer.open({
		type: 2,content: '正在生成调拨文件'
		});
	}
	var resule_write=writefile("allot.json","");
		if(resule_write=="success"){
			setTimeout(function () {checkfile();}, 1000);
		}
}

//检查空托盘入库
function ckoutbound(){
	var logindata =readfile("empty.json");
	if(logindata.length<=0){
		layer.open({
		type: 2,content: '正在生成出库文件'
		});
	}
	var resule_write=writefile("empty.json","");
		if(resule_write=="success"){
			setTimeout(function () {checkfile();}, 1000);
		}
}
*/







	
