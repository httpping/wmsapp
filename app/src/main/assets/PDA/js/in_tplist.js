var tid = "";
$(function () {
    layer.open({
        type: 2, content: '正在检查托盘信息'
    });
    if (getUrlParam("tid") != null && getUrlParam("tid") != "") {
        tid = getUrlParam("tid");
    }

    $(".startinhouse").click(function () {
        window.location.href = "inhouse.html?tid=" + tid;
    });
    setTimeout(function () { gettasklist(); }, 1000);
});

function gettasklist() {
	var dataStr = readInHouseFile("inhouseplan.json");
	console.info(dataStr)
	var data = JSON.parse(dataStr);

    if (data != null && data != "") {
        var nhtml = "";
        $.each(data.InHousePlanList, function (i, item) {
            if (item.tpInfo != null && item.tpInfo != "") {

                //var list =JSON.parse(item.tpInfo);
                console.info("tp info " + item.tpInfo.length)
                console.info(JSON.stringify(item.tpInfo))

                $.each(item.tpInfo, function (z, val) {
                    console.info(JSON.stringify(val))
                    nhtml += "<li class=\'list_box\'>";
                    nhtml += "<h2 class=\'list_title\'>";
                    nhtml += "<span>托盘信息</span>";
                    nhtml += "<a style='display:none' href=\'javascript:;\' class=\'delete_btn\'></a>";
                    nhtml += "</h2>";
                    nhtml += "<a href=\'javascript:void(0)\' onclick='ckurl(" + item.PlanNo + "," + val.tpCode + ")' class=\'list_localtion\'>";
                    nhtml += "<label class=\'list_data\'>";
                    nhtml += "<span>拖盘编码：</span>";
                    nhtml += "<span>" + val.tpCode + "</span>";
                    nhtml += "</label>";
                    nhtml += "</a>";
                    nhtml += "</li>";
                });
            }

        });
        $("#ul_tasklist").html(nhtml);
    }
    layer.closeAll();
}

function ckurl(tid, tpcode) {
    window.location.href = "in_taskdetails.html?tid=" + tid + "&tpcode=" + tpcode;
}
function gourl(tid) {

    window.location.href = "taskdetails.html?tid=" + tid;
}
