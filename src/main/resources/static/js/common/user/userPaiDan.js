

//初始化数据
var currentPage = 1;  //当前页码
var skillId = -1;
var serviceProvince
var serviceCity
var serviceArea
var userReleaseId;
//过滤查询
function searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea) {
    $.getJSON("/manager/userDetail/userAll",{"pageCurrentNo":currentPage,"skillId":skillId,"serviceProvince":serviceProvince,"serviceCity":serviceCity,"serviceArea":serviceArea},callback)
    //回调
    function callback(data) {
        $.ajaxSettings.async = false;
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
           var workArea = showProvince(data.list[i].workProvince,data.list[i].workCity,data.list[i].workArea);

            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck'/></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" + data.list[i].name + "</td>" +
                "<td>" + data.list[i].card + "</td>" +
                "<td>" + data.list[i].skills + "</td>" +
                "<td>" + data.list[i].yearWorkId + "</td>" +
                "<td>" + workArea+"</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\"  onclick='updPaiDan("+data.list[i].id+")'>确认派单</a>" +
                "</td>" +
                "</tr>")

        }

        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        $("#pageNo").html(currentPage);
    }
    $.ajaxSettings.async = true;
}

//初始化加载数据
$(function () {
    serviceProvince = $("#serviceProvince").val();
    serviceCity = $("#serviceCity").val();
    serviceArea = $("#serviceArea").val();
    userReleaseId = $("#userReleaseId").val();
   searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea)
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea)
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea)
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
            searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea)
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea)
        $("#pageNo").html(currentPage);
    })

})

function searchPaiDanSkill() {
    var skill = $("#skills").val();
    searchPaiDan(currentPage,skill,serviceProvince,serviceCity,serviceArea)
}

/**
 * 派单
 * @param id
 */
function updPaiDan(id) {
    var userReId  = userReleaseId;   // 发布信息id
    $.getJSON("/manager/userDetail/updPaiDan",{"id":userReId,"userDetailId":id},function (data) {
        if(data == true){
            alert("成功发送派单请求通知")
          location.href="/manager/userRelease/goUserReleaseAll";

        }else{
            alert("该条雇佣信息已经为这名师傅派过单了，请勿重复派单！")
        }
    })
}