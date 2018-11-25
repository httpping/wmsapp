$(function(){
	layer.open({
		type: 2,content: '正在检查任务系统'
	});
	setTimeout(function () {gettasklist();}, 200);
	
	
	$(".startinhouse").click(function(){
		window.location.href="inhouse.html";
	})
});

function gettasklist(){
	var dataStr = readInHouseFile("inhouseplan.json");
	var data = JSON.parse(dataStr);
	if(data!=null && data!=""){
				   var nhtml="";
				   $.each(data.InHousePlanList,function(i,item){
						nhtml+="<li class=\'list_box\'>";
						nhtml+="<h2 class=\'list_title\'>";
						nhtml+="<span>"+item.PlanName+"</span>";
						nhtml+="<a style='display:none' href=\'javascript:;\' class=\'delete_btn\'></a>";
						nhtml+="</h2>";
						if(item.tpInfo!=null && item.tpInfo!="")
						{
							nhtml+="<a href=\'javascript:void(0)\' onclick='ckurl("+item.PlanNo+",1)' class=\'list_localtion\'>";
						}else{
							nhtml+="<a href=\'javascript:void(0)\' onclick='ckurl("+item.PlanNo+",0)' class=\'list_localtion\'>";
						}
						nhtml+="<label class=\'list_data\'>";
						nhtml+="<span>任务编码：</span>";
						nhtml+="<span>"+item.PlanNo+"</span>";
						nhtml+="</label>";
						nhtml+="<label class=\'list_data\'>";
						nhtml+="<span>任务状态：</span>";
						//如果有tpInfo节点则认为是执行中
						if(item.tpInfo!=null && item.tpInfo!="")
						{
							nhtml+="<span style='color:#0D74AE'>执行中</span>";
						}else{
							
							nhtml+="<span style='color:red'>待执行</span>";
						}
						nhtml+="</label>";
						nhtml+="<label class=\'list_data\'>";
						nhtml+="<span>详情类型：</span>";
						nhtml+="<span>日常入库</span>";
						nhtml+="</label>";
						nhtml+="</a>";
						nhtml+="</li>";
						
						
					});
					$("#ul_tasklist").html(nhtml);
			   }
			   layer.closeAll();
}

function ckurl(tid,tpstatus){
	window.location.href="taskdetails.html?tid="+tid;
	/*if(tpstatus=="1"){
		window.location.href="in_tplist.html?tid="+tid;
	}else{
		window.location.href="taskdetails.html?tid="+tid;
	}*/
	
}