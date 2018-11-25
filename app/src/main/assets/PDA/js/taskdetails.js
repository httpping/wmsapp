var tid = "";
$(function () {
    layer.open({
        type: 2, content: '正在检查任务系统'
    });
    setTimeout(function () { gettasklist(); }, 200);


    if (getUrlParam("tid") != null && getUrlParam("tid") != "") {
        tid = getUrlParam("tid");
    }

    $(".startinhouse").click(function () {
        window.location.href = "in_tplist.html?tid=" + tid;
    });
});

function gettasklist() {
    if (tid != "") {
		var dataStr = readInHouseFile("inhouseplan.json");
	    var data = JSON.parse(dataStr);
        if (data != null && data != "") {
            var nhtml = "";
            $.each(data.InHousePlanList, function (i, item) {
                if (item.PlanNo == tid) {
                    $("#txtPlanNo").val(item.PlanNo);
                    $("#txtPlanName").val(item.PlanName);
                    $("#PlanStatus").val(item.PlanStatus == "0" ? "待执行" : "执行中");
                    if (item.productdetailList != null && item.productdetailList != "") {
                        $.each(item.productdetailList, function (j, val) {
                            nhtml += "<li class=\'list_\'>";
                            nhtml += "                <h2 class=\'list_title\'>" + val.ProductName + "</h2>";
                            nhtml += "                <div class=\'list_box\'>";
                            nhtml += "                    <p class=\'list_data\'>";
                            nhtml += "                        <span>物料编码：</span>";
                            nhtml += "                        <span>" + val.ProductCode + "</span>";
                            nhtml += "                    </p>";
                            nhtml += "                   <p class=\'list_data\'>";
                            nhtml += "                       <span>物料单位：</span>";
                            nhtml += "                       <span>" + val.Punit + "</span>";
                            nhtml += "                   </p>";
                            nhtml += "                    <p class=\'list_data\'>";
                            nhtml += "                        <span>物料规格：</span>";
                            nhtml += "                        <span>" + val.Specs + "</span>";
                            nhtml += "                    </p>";
                            nhtml += "                    <p class=\'list_data\'>";
                            nhtml += "                        <span>待入库数量：</span>";
                            nhtml += "                        <span>" + val.Quanlity + "</span>";
                            nhtml += "                    </p>";
							if(val.inhourseNum!=null && val.inhourseNum!="")
							{
								nhtml += "                    <p class=\'list_data\'>";
								nhtml += "                        <span>已入库数量：</span>";
								nhtml += "                        <span>" + val.inhourseNum + "</span>";
								nhtml += "                    </p>";
							}
                            nhtml += "                </div>";
                            nhtml += "            </li>";
                        });
                    }
                }
            });
            $("#ul_tasklist").html(nhtml);
        }
        layer.closeAll();
    } else {
        layer.closeAll();
        layer.open({
            content: '参数错误'
			, btn: '确认'
			, yes: function (index) { window.location.href = "tasklist.html" }
        });
    }
}

function ckurl(tid) {
    window.location.href = "taskdetails.html?tid=" + tid;
}
