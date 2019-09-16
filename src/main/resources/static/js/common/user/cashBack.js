//初始化数据
var currentPage = 1;  //当前页码
var backStatusId =-1;
var openId = -1
var userName = $("#userName").val();

function getUserId(userParent) {
    var userParentId
    $.getJSON("/manager/user/selUserByOpenId",{"openId":userParent},function (data) {
        userParentId = data.id;
    })
    return userParentId;
}


//过滤查询
function searchCommission(currentPage,backStatusId,userName) {
    $.ajaxSettings.async = false;
    $.getJSON("/manager/commission/show",{"pageCurrentNo":currentPage,"backStatusId":backStatusId,"openId":openId,"userName":userName},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            var createDate = toDate(new Date(data.list[i].createDate).toJSON())
            var sui =(data.list[i].money*0.1).toFixed(2);
            var shi = (data.list[i].money-sui).toFixed(2);
            var comment = data.list[i].comment == null ? '' : data.list[i].comment
            var userId = getUserId(data.list[i].openId)
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck'  name='checkbox' /></td>" +
                "<td id='uid'>" + data.list[i].id + "</td>" +
                "<td id='user'>" +
                "<a id='openIds' href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserByOpenId(\""+data.list[i].openId+"\")'>"+ userId+"</a>" +
                "</td>" +
                "<td id='user2' style='display: none'>" +
                "<a id='openIds2' href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\" >"+ data.list[i].openId+"</a>" +
                "</td>" +
                "<td >" + data.list[i].user.nickName+ "</td>" +
                "<td >" + data.list[i].money +"/"+sui+ "</td>" +
                "<td style='display: none' id='suiMoney'>" +sui+ "</td>" +
                "<td id='moneys'>" + shi + "</td>" +
                "<td>" + createDate+ "</td>" +
                "<td>" + data.list[i].backStatus.backStatusName + "</td>" +
                "<td>" + comment + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn btn-xs btn-success\" onclick='savePay(this)' id='pays'  style=\"" + ((data.list[i].backStatusId==1) ? '' : 'display:none;')+"\" >确认支付</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-warning\" data-toggle=\"modal\" data-target=\"#editCommission\" onclick='upCommon(\""+data.list[i].id+"\")' id='up'  >修改</a>" +
                "</tr>")

        }

        $.ajaxSettings.async = true;
        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        var curr = data.totalPages == 0 ? 0 : currentPage
        $("#pageNo").html(curr);
    }
}

//初始化加载数据
$(function () {
    searchCommission(currentPage,backStatusId,userName);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        searchCommission(currentPage,backStatusId,userName);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            searchCommission(currentPage,backStatusId,userName);
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
            searchCommission(currentPage,backStatusId,userName);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        searchCommission(currentPage,backStatusId,userName);
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
    var openIds = $(btn).parent().siblings("#user2").children("#openIds2").html();
    var moneys = $(btn).parent().siblings("#moneys").html();
   var suiMoney = $(btn).parent().siblings("#suiMoney").html();
    var mymessage=confirm("您将要支付【"+moneys+"元】，您确认支付吗？");
    if(mymessage==true) {
        $.getJSON("/manager/commission/payCommission", {"openId":openIds,"id":id,"money":moneys,"suiMoney":suiMoney}, function (data) {
            if(data.flag ==true){
                alert("支付成功")
                window.location.reload();
            }else{
                alert("支付失败: "+data.errorMsg)
                window.location.reload();
            }
        });
    } else{
        alert("您取消了支付")
    }



}

/**
 * 批量删除
 */
function delCashBack() {
    var delUsers = document.getElementsByClassName("userCheck");
    var j = 0;
    var arrayId = new Array() ;

    for (var i = 0; i <delUsers.length ; i++) {
        if (delUsers[i].checked) {
            var id = $(delUsers[i]).parent().next().html();
            arrayId[j] = id
            j++;
        }
    }
    if(j ==0 ){
        alert("您没有选择，删除失败")
    }else{
        var resMessage=confirm("您确认删除这些数据吗？");
        if(resMessage == true) {
            $.getJSON("/manager/commission/delCashBack", {"ids": arrayId.toString()}, function (data) {
                if (data == true) {
                    alert("批量删除成功！")
                    window.location.reload();
                } else {
                    alert("批量删除失败！")
                }
            })
        }else {
            alert("您取消了删除")
        }
    }
}

/**
 * 提现人查找
 */
function searchCash() {
    var name = $("#userName").val();
    searchCommission(currentPage,backStatusId,name);
}