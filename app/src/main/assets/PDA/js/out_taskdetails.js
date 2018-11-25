var tid = "";
var tpcode = "";
$(function () {
    layer.open({
        type: 2, content: '正在检查任务系统'
    });
    setTimeout(function () { gettasklist(); }, 1000);


    if (getUrlParam("tid") != null && getUrlParam("tid") != "") {
        tid = getUrlParam("tid");
    }
    if (getUrlParam("tpcode") != null && getUrlParam("tpcode") != "") {
        tpcode = getUrlParam("tpcode");
    }

    $(".startinhouse").click(function () {
        window.location.href = "editouthouse.html?tid=" + tid + "&tpcode=" + tpcode;
    });
});

function gettasklist() {
    if (tid != "") {
		var dataStr = readOutHouseFile("outhouseplan.json");
	    var data = JSON.parse(dataStr);
        if (data != null && data != "") {
            var nhtml = "";
            $.each(data.OutHousePlanList, function (i, item) {
                if (item.PlanNo == tid) {
                    $("#txtPlanNo").val(item.PlanNo);
                    $("#txtPlanName").val(item.PlanName);
                    $("#PlanStatus").val(item.PlanStatus == "0" ? "待执行" : "执行中");
                    $("#txtBoxCode").val(tpcode);
                    if (item.BoxCodeList != null && item.BoxCodeList != "") {
                        $.each(item.BoxCodeList, function (j, val) {
                            if (val.productdetailList != null && val.productdetailList != "") {
                                $.each(val.productdetailList, function (z, zitem) {
                                    nhtml += "<li class=\'list_\'>";
                                    nhtml += "                <h2 class=\'list_title\'>" + zitem.ProductName + "</h2>";
                                    nhtml += "                <div class=\'list_box\'>";
                                    nhtml += "                    <p class=\'list_data\'>";
                                    nhtml += "                        <span>物料编码：</span>";
                                    nhtml += "                        <span>" + zitem.ProductCode + "</span>";
                                    nhtml += "                    </p>";
                                    nhtml += "                   <p class=\'list_data\'>";
                                    nhtml += "                       <span>物料单位：</span>";
                                    nhtml += "                       <span>" + zitem.Punit + "</span>";
                                    nhtml += "                   </p>";
                                    nhtml += "                    <p class=\'list_data\'>";
                                    nhtml += "                        <span>物料规格：</span>";
                                    nhtml += "                        <span>" + zitem.Specs + "</span>";
                                    nhtml += "                    </p>";
                                    nhtml += "                    <p class=\'list_data\'>";
                                    nhtml += "                        <span>是否分拣：</span>";
                                    nhtml += "                        <span>" + (zitem.IsFj == "1" ? "<span style='color:red'>是</span>" : "<span style='color:#0D74AE'>否</span>") + "</span>";
                                    nhtml += "                    </p>";
                                    nhtml += "                    <p class=\'list_data\'>";
                                    nhtml += "                        <span>入库数量：</span>";
                                    nhtml += "                        <span>" + zitem.Quanlity + "</span>";
                                    nhtml += "                    </p>";
                                    nhtml += "                </div>";
                                    nhtml += "            </li>";
                                });
                            }
                        })
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
			, yes: function (index) { window.location.href = "out_tasklist.html" }
        });
    }
}


function ckurl(tid) {
    window.location.href = "out_taskdetails.html?tid=" + tid;
}
