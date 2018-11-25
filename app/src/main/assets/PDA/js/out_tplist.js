var tid = "";
$(function () {
    layer.open({
        type: 2, content: '正在检查托盘信息'
    });
    if (getUrlParam("tid") != null && getUrlParam("tid") != "") {
        tid = getUrlParam("tid");
    }

    $(".startinhouse").click(function () {
        window.location.href = "outhouse.html?tid=" + tid;
    });
    setTimeout(function () { gettasklist(); }, 200);


    $("#txttpcode").keydown(function (event) {
        if (event.keyCode == 13) {
            var code = $(this).val();
            searchHtml(code);
        }
    })


    //物料条码扫描html进行展示
    $('#txttpcode').bind('input propertychange', function () {
        var code = $(this).val();
        if (code.indexOf("#") != -1) {
            code = code.replace("#", "");
            $('#txttpcode').val(code);
            searchHtml(code);
        }
    });



});

function gettasklist() {
	var dataStr = readOutHouseFile("outhouseplan.json");
	var data = JSON.parse(dataStr);
    if (data != null && data != "") {
        var nhtml = "";
        $.each(data.OutHousePlanList, function (i, item) {
            if (item.BoxCodeList != null && item.BoxCodeList != "") {
                //var list =JSON.parse(item.tpInfo);
                $.each(item.BoxCodeList, function (z, val) {
                    nhtml += "<li class=\'list_box\'>";
                    nhtml += "<h2 class=\'list_title\'>";
                    nhtml += "<span>托盘信息</span>";
                    nhtml += "<a style='display:none' href=\'javascript:;\' class=\'delete_btn\'></a>";
                    nhtml += "</h2>";
                    nhtml += "<a href=\'javascript:void(0)\' onclick='ckurl(this)' data-tid-value=" + item.PlanNo + " data-tpcode-value=" + val.BoxCode + " class=\'list_localtion\'>";
                    nhtml += "<label class=\'list_data\'>";
                    nhtml += "<span>拖盘编码：</span>";
                    nhtml += "<span>" + val.BoxCode + "</span>";
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

function ckurl(obj) {
    //window.location.href = "editouthouse.html?tid=" + $(obj).attr("data-tid-value") + "&tpcode=" + $(obj).attr("data-tpcode-value");
	
	window.location.href = "out_taskdetails.html?tid=" + $(obj).attr("data-tid-value") + "&tpcode=" + $(obj).attr("data-tpcode-value");
}
function gourl(tid) {

    window.location.href = "taskdetails.html?tid=" + tid;
}
function searchHtml(code) {
    var index = "";
    var dataStr = readOutHouseFile("outhouseplan.json");
	var data = JSON.parse(dataStr);
    if (data != null && data != "") {
        var nhtml = "";
        $.each(data.OutHousePlanList, function (i, item) {
            if (item.BoxCodeList != null && item.BoxCodeList != "") {
                $.each(item.BoxCodeList, function (z, val) {
                    if (val.BoxCode == code) {
                        index = code;
                    }
                });
            }
        });

        if (index == "") {
            layer.open({ content: '该托盘编码不在日常出库任务中!' });
            $('#txttpcode').val("");
        } else {
            window.location.href = "editouthouse.html?tid=" + tid + "&tpcode=" + code;
        }
    }
}
