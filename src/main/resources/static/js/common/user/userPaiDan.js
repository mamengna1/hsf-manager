

//初始化数据
var currentPage = 1;  //当前页码
var skillId = -1;
var serviceProvince = $("#serviceProvince").val();
var serviceCity = $("#serviceCity").val();
var serviceArea = $("#serviceArea").val();

//过滤查询
function searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea) {
    alert("进入")
    $.getJSON("/manager/userDetail/userAll",{"pageCurrentNo":currentPage,"skillId":skillId,"serviceProvince":serviceProvince,"serviceCity":serviceCity,"serviceArea":serviceArea},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
           var workArea = showProvince(data.list[i].workProvince,data.list[i].workCity,data.list[i].workArea);
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck'/></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" + data.list[i].card + "</td>" +
                "<td>" + data.list[i].skills + "</td>" +
                "<td>" + data.list[i].yearWorkId + "</td>" +
                "<td>" + workArea+"</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\"  onclick='updDetail("+data.list[i].id+")'>确认派单</a>" +
                "</td>" +
                "</tr>")

        }

        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        $("#pageNo").html(currentPage);
    }
}

//初始化加载数据
$(function () {

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
