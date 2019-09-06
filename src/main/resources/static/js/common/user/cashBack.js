//初始化数据
var currentPage = 1;  //当前页码
var backStatusId =-1;
var openId = -1
//过滤查询
function searchCommission(currentPage,backStatusId) {

    $.getJSON("/manager/commission/show",{"pageCurrentNo":currentPage,"backStatusId":backStatusId,"openId":openId},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            var createDate = toDate(new Date(data.list[i].createDate).toJSON())
            var sui =(data.list[i].money*0.1).toFixed(2);
            var shi = (data.list[i].money-sui).toFixed(2);
            var comment = data.list[i].comment == null ? '' : data.list[i].comment
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\"/></td>" +
                "<td id='uid'>" + data.list[i].id + "</td>" +
                "<td id='user'>" +
                "<a id='openIds' href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserByOpenId(\""+data.list[i].openId+"\")'>"+ data.list[i].openId+"</a>" +
                "</td>" +
                "<td >" + data.list[i].money +"/"+sui+ "</td>" +
                "<td id='moneys'>" + shi + "</td>" +
                "<td>" + createDate+ "</td>" +
                "<td>" + data.list[i].backStatus.backStatusName + "</td>" +
                "<td>" + comment + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" onclick='savePay(this)' id='pays'>确认支付</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" data-toggle=\"modal\" data-target=\"#editCommission\" onclick='upCommon(\""+data.list[i].id+"\")' id='up'>修改</a>" +
                "</tr>")

            if(data.list[i].backStatusId==2){
                $("#pays").hide()
                $("#up").hide()
            }
        }


        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        $("#pageNo").html(currentPage);
    }
}

//初始化加载数据
$(function () {
    searchCommission(currentPage,backStatusId);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        searchCommission(currentPage,backStatusId);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            searchCommission(currentPage,backStatusId);
            $("#pageNo").html(currentPage);
        }
    })
    //下一页
    $("#next").click(function () {
        currentPage = parseInt(currentPage)+1;
        if (parseInt(currentPage) > parseInt($("#totalPages").html())) {
            alert("已经最后一页了");
            return;
        } else {
            searchCommission(currentPage,backStatusId);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        searchCommission(currentPage,backStatusId);
        $("#pageNo").html(currentPage);
    })

})

/**
 * 进入修改页面状态
 * @param id
 */
function upCommon(id) {
    $.getJSON("/manager/commission/getCommissionById",{"id":id},function (data) {
        $("#cid,#commOpenId,#cashBack,#commissionDate,#commissionType").html("");
        $("#cid").val(data.commission.id)
        var money = (data.commission.money)-(data.commission.money*0.1)
        $("#commOpenId").append(data.commission.openId)
        $("#cashBack").append(money)
        var createDate = toDate(new Date(data.commission.createDate).toJSON())
        $("#commissionDate").append(createDate)
        for (var i = 0; i < data.listCommission.length; i ++){
            if (data.listCommission[i].id == data.commission.backStatusId) {
                $("#commissionType").append("<option  value=\""+ data.listCommission[i].id +" \" id='stateId' selected>" +data.listCommission[i].backStatusName+"</option>")
            } else {
                $("#commissionType").append("<option  value=\""+ data.listCommission[i].id +" \" id='stateId'>" +data.listCommission[i].backStatusName+"</option>")
            }

        }

    })
}

/**
 * 保存修改结果
 */
function saveCommission() {
    var stateId =$("#commissionType").val();
    var cid = $("#cid").val();
    $.getJSON("/manager/commission/saveCommission",{"backStatusId":stateId,"id":cid},function (data) {
        if(data==true){
            alert("修改成功")
            window.location.reload();
        }else{
            alert("修改失败")
        }
    })
}

//确认支付
function savePay(btn) {
    var id = $(btn).parent().siblings("#uid").html()
    var openIds = $(btn).parent().siblings("#user").children("#openIds").html();
    var moneys = $(btn).parent().siblings("#moneys").html();
    $.getJSON("/manager/commission/payCommission", {"openId":openIds,"id":id,"money":moneys}, function (data) {
        if(data.flag ==true){
            alert("支付成功")
            window.location.reload();
        }else{
            alert("支付失败: "+data.errorMsg)
            window.location.reload();
        }
    });

}