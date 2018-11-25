var tid = "";
var plan_productlist = [];
$(function () {
    //alert("start");
    if (getUrlParam("tid") != null && getUrlParam("tid") != "") {
        tid = getUrlParam("tid");
        getproductlist();
    }

    console.info(plan_productlist);

    $(".cancel").click(function () {
        $(".pop-goods,.mask").hide();
    });



    $('.num').bind('input propertychange', function () {
        if (this.value.length == 1) { this.value = this.value.replace(/[^1-9]/g, '') } else { this.value = this.value.replace(/\D/g, '') }
    });



    $("#txtproductcode").keydown(function (event) {
        if (event.keyCode == 13) {
            var code = $(this).val();
            searchHtml(code);
        }
    })


    //物料条码扫描html进行展示
    $('#txtproductcode').bind('input propertychange', function () {
        var code = $(this).val();
        if (code.indexOf("#") != -1) {
            code = code.replace("#", "");
            $('#txtproductcode').val(code);
            searchHtml(code);
        }
    });

});



var searchHtml = function (code) {
    var nhtml = "";
    var ishasvalue = false;
    $.each(plan_productlist, function (i, item) {
        if (item.ProductCode == code) {
            //先判断是否已经添加过
            if ($("#countNum_" + item.ProductCode) != null && $("#countNum_" + item.ProductCode).length > 0) {
                ishasvalue = true;
                var oldNum = parseInt($("#countNum_" + item.ProductCode).html());
                if (oldNum > 0) {
                    oldNum++;
                    $("#countNum_" + item.ProductCode).html(oldNum);
                } else {
                    $("#countNum_" + item.ProductCode).html("1");
                }
            }
            else {
                var iconNum = $(".cart-add-quantity").html();
                var shopcardnum = $("#shopcardnum").html();
                var currentNum = parseInt(iconNum) + 1;
                $(".cart-add-quantity").html(currentNum);
                currentNum = parseInt(shopcardnum) + 1;
                $("#shopcardnum").html(currentNum + "件");
                nhtml += "<li class=\'data_box\'>";
                nhtml += "                <h2 class=\'data_title\'>";
                nhtml += "                    <span>" + item.ProductName + "</span>";
                nhtml += "                    <a href=\'javascript:;\' class=\'delete_btn\' onclick='delproduct(" + parseInt(item.ProductCode) + ")'></a>";
                nhtml += "                </h2>";
                nhtml += "                <div class=\'data_content\'>";
                nhtml += "                    <p class=\'datas\'>";
                nhtml += "                        <span>物料编码：</span>";
                nhtml += "                        <span>" + item.ProductCode + "</span>";
                nhtml += "                    </p>";
                nhtml += "                    <p class=\'datas\'>";
                nhtml += "                        <span>物料单位：</span>";
                nhtml += "                        <span>" + item.Punit + "</span>";
                nhtml += "                    </p>";
                nhtml += "                    <p class=\'datas\'>";
                nhtml += "                        <span>物料规格：</span>";
                nhtml += "                        <span>" + item.Specs + "</span>";
                nhtml += "                    </p>";
                nhtml += "                    <p class=\'datas\'>";
                nhtml += "                        <span>待入库数量：</span>";
                nhtml += "                        <span>" + item.Quanlity + "</span>";
                nhtml += "                    </p>";
                nhtml += "                    <p class=\'datas\'>";
                nhtml += "                        <span>已入库数量：</span>";
                nhtml += "                        <span data-inhoursenum-val=" + item.ProductCode + " class='s_inhourseNum' id=countNum_" + item.ProductCode + ">0</span>";
                nhtml += "                    </p>";
                nhtml += "                    <a href=\'javascript:void(0)\' class=\'add_btn\' onclick='showmenu(" + parseInt(item.ProductCode) + ")'></a>";
                nhtml += "                </div>";
                nhtml += "            </li>";
            }
        }
    });
    if (nhtml != "") {
        $("#ullist").append(nhtml);
    } else {
        if (!ishasvalue) {
            layer.open({ content: '该物料编码不在日常入库任务中!' });
            $('#txtproductcode').val("");
        }
    }
}

function search(obj) {
    var code = $(obj).val();
    var ishasvalue = false;
    var nhtml = "";

    if (nhtml != "") {
        $("#ullist").append(nhtml);
    }
    else {
        if (!ishasvalue) {
            layer.open({ content: '该物料编码不在日常入库任务中!' });
            $('#txtproductcode').val("");
        }
    }
}




function getproductlist() {
    if (tid != "") {
        //var data = readInHouseFile("inhouseplan.json");
		var dataStr = readInHouseFile("inhouseplan.json");
	    var data = JSON.parse(dataStr);
        if (data != null && data != "") {
            var nhtml = "";
            $.each(data.InHousePlanList, function (i, item) {
                if (item.PlanNo == tid) {
                    if (item.productdetailList != null && item.productdetailList != "") {
                        $.each(item.productdetailList, function (j, val) {
                            plan_productlist.push({ "ProductName": val.ProductName, "ProductCode": val.ProductCode, "Punit": val.Punit, "Specs": val.Specs, "Quanlity": val.Quanlity });
                        });
                    }
                }
            });
        }
    } else {
        layer.closeAll();
        layer.open({
            content: '参数错误'
			, btn: '确认'
			, yes: function (index) { window.location.href = "/tasklist.html" }
        });
    }
}








function showmenu(code) {
    var obj = $("#countNum_" + code);
    if (obj != null && obj.length > 0) {
        $(".determine").attr("data-pcode-value", code);
        $(".mask").show();
        $(".pop-goods").show();
        $(".num").val(parseInt(obj.html()));
        document.addEventListener('touchmove', eventDefault, { passive: false });
    }
}

function cksure(obj) {
    var pcode = $(obj).attr("data-pcode-value");
    if (pcode != "") {
        $(".mask").hide();
        $(".pop-goods").hide();
        $("#countNum_" + pcode).html(parseInt($(".num").val()));
        $(obj).attr("data-pcode-value", "");
    }
}

function add() {
    var json = [];
    var tpproductlist = [];
    var dataStr = readInHouseFile("inhouseplan.json");
	    var data = JSON.parse(dataStr);
    if (data != null && data != "") {
        var nhtml = "";
        $.each(data.InHousePlanList, function (i, item) {
            if (item.PlanNo == tid) {
                if (item.productdetailList != null && item.productdetailList != "") {
                    $.each(item.productdetailList, function (j, val) {
                        var plist = $(".s_inhourseNum");
                        if (plist != null && plist.length > 0) {
                            plist.each(function () {
                                if ($(this).attr("data-inhoursenum-val") == val.ProductCode) {
                                    //tpproductlist.push({"StockDetailID":val.StockDetailID,"ProductName":val.ProductName,"ProductCode":val.ProductCode,"ProductType":val.ProductType,"Punit":val.Punit,"Specs":val.Specs,"Cell":val.Cell,"Quanlity":val.Quanlity,"Remark":val.Remark,"IsFj":val.IsFj,"inhourseNum":parseInt($("#countNum_"+$(this).attr("data-inhoursenum-val")).html())});
                                    tpproductlist.push({ "ProductName": val.ProductName, "ProductCode": val.ProductCode, "Punit": val.Punit, "Specs": val.Specs, "Quanlity": val.Quanlity, "inhourseNum": parseInt($("#countNum_" + $(this).attr("data-inhoursenum-val")).html()) });
                                }
                            })
                        }
                    });
                    json.push({ "tpCode": $("#txttpcode").val(), "productlist": tpproductlist })
                }
                item.tpInfo = json;
                item.PlanStatus = "1";
            }
        });

        //重新写入文件
        console.info("===>最后的文件是:" + JSON.stringify(data));
        var isresult = writefile("inhouseplan.json", JSON.stringify(data));
        if (isresult == "success") {
            layer.closeAll();
            layer.open({
                content: '操作成功'
            , btn: '确认'
            , yes: function (index) { window.location.href = "tasklist.html" }
            });
        }
    }
}


//删除物料
function delproduct(pcode) {
    layer.open({
        content: '您确定删除该项物料？'
           , btn: ['确认', '取消']
   , yes: function (index) {
       var listNo = "";
       var itemNo = "";
       var dataStr = readInHouseFile("inhouseplan.json");
	    var data = JSON.parse(dataStr);
       if (data != null && data != "") {
           var nhtml = "";
           console.info("===>开始的列表是:" + JSON.stringify(data.InHousePlanList));
           $.each(data.InHousePlanList, function (i, item) {
               if (item.PlanNo == tid) {
                   listNo = i;
                   if (item.productdetailList != null && item.productdetailList.length > 0) {
                       $.each(item.productdetailList, function (j, itemlist) {
                           if (itemlist.ProductCode == pcode) {
                               /*itemNo=j;*/
                               val.inhourseNum = 0;
                               console.info("===>itemNo:" + itemNo);
                           }
                       });
                       //item.productdetailList.splice(itemNo,1);不需要删除
                   }
               }
           });
           //重新写入文件
           var isresult = writefile("inhouseplan.json", JSON.stringify(data));
           if (isresult == "success") {
               layer.closeAll();
               layer.open({
                   content: '操作成功'
               , btn: '确认'
               , yes: function (index) { window.location.href = "/tasklist.html" }
               });
           }
       }
   }
    });
}