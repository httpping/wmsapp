$(function () {
    layer.open({
        type: 2, content: '正在检查任务系统'
    });
    setTimeout(function () { gettasklist(); },200);
});

function gettasklist() {
    //var data = readOutHouseFile("outhouseplan.json");
	var dataStr = readOutHouseFile("outhouseplan.json");
	var data = JSON.parse(dataStr);
    if (data != null && data != "") {
        var nhtml = "";
        $.each(data.OutHousePlanList, function (i, item) {
            nhtml += "<li class=\'list_box\'>";
            nhtml += "<h2 class=\'list_title\'>";
            nhtml += "<span>" + item.PlanName + "</span>";
            nhtml += "<a style='display:none' href=\'javascript:;\' class=\'delete_btn\'></a>";
            nhtml += "</h2>";
            nhtml += "<a href=\'javascript:void(0)\' onclick='ckurl(" + item.PlanNo + ")' class=\'list_localtion\'>";
            nhtml += "<label class=\'list_data\'>";
            nhtml += "<span>任务编码：</span>";
            nhtml += "<span>" + item.PlanNo + "</span>";
            nhtml += "</label>";
            nhtml += "<label class=\'list_data\'>";
            nhtml += "<span>任务状态：</span>";
            nhtml += "<span>" + (item.PlanStatus == "0" ? "<span style='color:red'>待执行</span>" : "<span style='color:#0D74AE'>执行中</span>") + "</span>";
            nhtml += "</label>";
            nhtml += "<label class=\'list_data\'>";
            nhtml += "<span>详情类型：</span>";
            nhtml += "<span>日常出库</span>";
            nhtml += "</label>";
            nhtml += "</a>";
            nhtml += "</li>";
        });
        $("#ul_tasklist").html(nhtml);
    }
    layer.closeAll();
}

function ckurl(tid) {
    window.location.href = "out_tplist.html?tid=" + tid;
}