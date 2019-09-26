

//初始化数据
var currentPage = 1;  //当前页码
var names = $("#names").val();
var statusId;  //全部信息
var lineStatus = -1    //在线状态
//过滤查询
function searchCustomer(currentPage,names,statusId,lineStatus) {
    //$.ajaxSettings.async = false;
    $.getJSON("/manager/userMaster/userAll",{"pageCurrentNo":currentPage,"names":names,"statusId":statusId,"lineStatus":lineStatus},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        $("#zaiXian").html(data.zaiXian);
        $("#liXian").html(data.liXian);
        for (var i = 0; i < data.list.length; i++) {
            var a = data.list[i].status;
            var updTime = data.list[i].updTime == null ? '' : toDate(new Date(data.list[i].updTime).toJSON())
            var passTime = data.list[i].passTime == null ? '' : toDate(new Date(data.list[i].passTime).toJSON())
            var lineStatus = data.list[i].lineStatus == 1 ? "在线" : data.list[i].lineStatus == null ? "无状态(审核失败)" : "离线"
            var message = a == 0 ? "待审核" :a == 1 ? "审核成功" : a == 2 ? "审核失败 ："+data.list[i].statusMessage : a==3 ? "再次提交信息，上次审核失败原因是 ："+data.list[i].statusMessage : a==4 ? "待激活" : a == 5 ? "待恢复":''
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck' name='checkbox'/></td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserDetail(\""+data.list[i].id+"\")'>"+ data.list[i].id+"</a>" +
                "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#detailModal\"  onclick='selUserDetailById(\""+data.list[i].id+"\")'>"+ data.list[i].name+"</a>" +
                "</td>" +
                "<td>" + data.list[i].card + "</td>" +
                "<td><img src='"+  data.list[i].cardOne +"' width='50px' height='50px' onclick='showBig(this)'/></td>" +
                "<td><img src='"+  data.list[i].cardTwo +"' width='50px' height='50px' onclick='showBig(this)'/></td>" +
            /*    "<td>" + workArea+"</td>" +*/
                "<td>" +message+"</td>" +
                "<td>" + updTime+"</td>" +
                "<td>" +passTime+"</td>" +
                "<td>" +lineStatus+"</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\"  onclick='goUserMasterShow("+data.list[i].id+")'>查看</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-warning\"  onclick='updDetail("+data.list[i].id+")'>修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-info\" data-toggle=\"modal\" data-target=\"#auditModal\"  onclick='selAudit("+data.list[i].id+")' style=\"" + ((a ==1 || a == 2 ) ? '' : 'display:none;') + "\" >已审核</a>" +
                "<a href='javascript:void(0)'  class=\"btn btn-xs bg-gray\" data-toggle=\"modal\" data-target=\"#auditModal\"  onclick='selAudit("+data.list[i].id+")' style=\"" + ((a == 0 || a == 3 || a==4 || a==5) ? '' : 'display:none;') + "\" >未审核</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\"  onclick='goDistributionIndex("+data.list[i].id+")'>接单记录</a>" +
                "</td>" +
                "</tr>")

        }

        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        var curr = data.totalPages == 0 ? 0 : currentPage
        $("#pageNo").html(curr);

    }
    //$.ajaxSettings.async = true;
}

function showBig(btn){
    $("#showBig img").attr("src", $(btn).attr("src"));
    $("#showBig").show();
}
function myhide(){
    $("#showBig").hide();
}

//初始化加载数据
$(function () {
    $("#showBig").hide();
    statusId = $("#statusId").val();
    searchCustomer(currentPage,names,statusId,lineStatus);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        searchCustomer(currentPage,names,statusId,lineStatus);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            searchCustomer(currentPage,names,statusId,lineStatus);
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
            searchCustomer(currentPage,names,statusId,lineStatus);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        searchCustomer(currentPage,names,statusId,lineStatus);
        $("#pageNo").html(currentPage);
    })

})


/**
 * 批量删除
 */
function delMasterById() {
    var delUsers = document.getElementsByClassName("userCheck");
    var j = 0;
    var arrayId = new Array() ;

    for (var i = 0; i <delUsers.length ; i++) {
        if (delUsers[i].checked) {
            var id = $(delUsers[i]).parent().next().children().html();
            arrayId[j] = id
            j++;
        }
    }
    if(j ==0 ){
        alert("您没有选择，删除失败")
    }else{
        var resMessage=confirm("您确认删除这些数据吗？");
        if(resMessage == true) {
            $.getJSON("/manager/userMaster/delMasterById", {"ids": arrayId.toString()}, function (data) {
                if (data == true) {
                    alert("批量删除成功！")
                    window.location.reload();
                } else {
                    alert("批量删除失败！")
                }
            })
        }else{
            alert("您取消了删除")
        }
    }
}

/**
 * 保存修改结果
 */
function saveUser() {
    var id = $("#ids").val();
    var userType = $("#userType2").val()
    var balanceMoney = $("#balanceMoney2").val()
    var totalScore = $("#totalScore2").val();
    var balanceScore = $("#balanceScore2").val();
    $.getJSON("/manager/user/updateUser",{"id":id,"userType":userType,"balanceMoney":balanceMoney,"totalScore":totalScore,"balanceScore":balanceScore},function (data) {
        if(data == true){
            alert("修改成功")
            location.href="/manager/user/goUserIndex";
        }else{
            alert("修改失败")
        }
    })
}


function searchNames() {
    var na = $("#names").val();
    searchCustomer(1,na,statusId,lineStatus)
}

/**
 * 修改
 * @param id
 */
function updDetail(id) {
    location.href="/manager/userMaster/goUserUpd?id="+id;
}

/**
 * 进入查看信息界面
 * @param id
 */
function goUserMasterShow(id) {
    location.href="/manager/userMaster/goUserMasterShow?id="+id;
}

/**
 * 接单记录
 * @param id
 */
function goDistributionIndex(id) {
    location.href="/manager/distribution/goDistributionIndex?sfId="+id;
}

/**
 * 审核下拉改变触发事件
 */
function func(){
    //获取被选中的option标签
    var vs = $('#stateType  option:selected').val();
    if(vs == 1){
        $("#statusMessage").val("")
        $("#statusMessage").attr("disabled","disabled")
    }else if(vs == 2){
        $('#statusMessage').removeAttr("disabled");//去除textarea元素的disabled属性

    }
}

/**
 * 在线
 */
function zaiXian() {
    searchCustomer(currentPage,names,statusId,1)
}

/**
 * 离线
 */
function liXian() {
    searchCustomer(currentPage,names,statusId,2)
}