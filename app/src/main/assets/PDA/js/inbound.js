$(function(){
	/* 系统文件检查
	layer.open({
		type: 2,content: '正在检查文件系统'
	});
	setTimeout(function () {cklogin();}, 1000);
	*/
	$("#demo_datetime").val(getNowFormatDate());
	var username =sessionStorage.getItem('username');
	if(username!=null && username!=""){
		$("#username").val(username);
	}
	//先加载供应商
	getsupplier();
	setType();
});


//读取供应商信息
function getsupplier()
{
	//现在方法
	$.ajax({
		   url: "supplier.json",
		   type: "GET",
		   dataType: "json", 
		   success: function(data) {
			   if(data!=null && data!=""){
				    //var dataobj =JSON.stringify(data);
					var nhtml="";
					$.each(data,function(i,item){
						nhtml+="<option value="+item.supplierid+">"+item.suppliername+"</option>";
					});
					$("#selsupplier").append(nhtml);
			   }else{
			   }
			}	
		});
}



//判断是新增还是读取文件
function setType()
{
	//现在方法
	$.ajax({
		   url: "inbound.json",
		   type: "GET",
		   dataType: "json", 
		   success: function(data) {
			   if(data!=null && data!=""){
					console.info("===>dataobj:"+data.StockCode);
				    sessionStorage.removeItem('inbound_productlist');
				    sessionStorage.setItem('inbound_productlist',data.productdetailList);//产品列表
					$("#seltype_dummy").val("计划复核");
					$("#seltype").val("02");
					$("#txtStockCode").val(data.StockCode);
					$("#demo_datetime").val(data.CreateDate);
					$("#selinhouse").val(data.inhouse);
					$("#selinhouse_dummy").val(data.inhouse=="1"?"一楼仓库":"二楼仓库");
					$("#txtRemark").val(data.Remark);
			   }else{
				   //单选框选中新增
				   console.info("===>新增");
				   $("#seltype_dummy").val("新增");
				   $("#seltype").val("02");
			   }
			}	
		});
		
	//正式方法
	/*
    var result =readfile(inbound.json)；
	if(result!=null && result!="")
	{
		//单选框选中计划复核
		var dataobj =JSON.stringify(result);
		
	}else{
		//单选框选中新增
	}*/
	//
}





	
