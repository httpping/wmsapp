
//写入文件
function writefile(filename,filedata){
	var params ={"action":"write_file","path":filename,"data":filedata};
	var r = tp.invokeJava(JSON.stringify(params));
	var dataobj =JSON.parse(r);
	if(dataobj!=null){
		if(dataobj.code=="200"){
			return "success";
		}else{
			return "系统错误，错误原因是:"+dataobj.error;
		}
	}
	return "success";
}

//读取文件
function readfile(filename){
   var params ={"action":"read_file","path":filename};
	var r = tp.invokeJava(JSON.stringify(params));
	return r;
}

//获取当前时间，格式YYYY-MM-DD
function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
}
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function readInHouseFile(filename)
{
	var params ={"action":"read_file","path":filename};
	var r = tp.invokeJava(JSON.stringify(params));
	if(r!=null && r!="")
	{
		var result=JSON.parse(r);
		if(result!=null && result!="" ){
			if(result.code=="200")
			{
			    console.info(result.data)
				return result.data;
			}
		}
	}
	return "";
}
function readOutHouseFile(filename)
{
	var params ={"action":"read_file","path":filename};
	var r = tp.invokeJava(JSON.stringify(params));
	if(r!=null && r!="")
	{
		var result=JSON.parse(r);
		if(result!=null && result!="" ){
			if(result.code=="200")
			{
				return result.data;
			}
		}
	}
	return "";
}
