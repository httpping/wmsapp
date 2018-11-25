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
        window.location.href = "editinhouse.html?tid=" + tid + "&tpcode=" + tpcode;
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
                    if (item.tpInfo != null && item.tpInfo != "") {
                        $.each(item.tpInfo, function (j, val) {

                            if (val.tpCode == tpcode) {
                                $("#txtTpCode").val(tpcode);

                                $.each(val.productlist, function (z, zitem) {
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
                                    nhtml += "                        <span>入库数量：</span>";
                                    nhtml += "                        <span>" + zitem.Quanlity + "</span>";
                                    nhtml += "                    </p>";
                                    nhtml += "                </div>";
                                    nhtml += "            </li>";
                                });
                            }
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
			, yes: function (index) { window.location.href = "out_tasklist.html" }
        });
    }
}
function ckurl(tid) {
    window.location.href = "out_taskdetails.html?tid=" + tid;
}
