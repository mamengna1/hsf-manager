var currentPage = 1;  //当前页码
var isDelete = 0;
$(function () {
    search(currentPage, 0);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        search(currentPage, isDelete);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage) - 1;
        if (parseInt(currentPage) < 1) {
            alert("已经是第一页了")
        } else {
            search(currentPage, isDelete);
            $("#pageNo").html(currentPage);
        }
    })
    //下一页
    $("#next").click(function () {
        currentPage = parseInt(currentPage) + 1;
        if (parseInt(currentPage) > parseInt($("#totalPages").html())) {
            alert("已经最后一页了");
            return;
        } else {
            search(currentPage, isDelete);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        search(currentPage, isDelete);
        $("#pageNo").html(currentPage);
    })
})

function search(currentPage, isDelete) {
    $.getJSON("/manager/selAll", {"pageCurrentNo": currentPage,"isDelete":isDelete}, function (res) {

        $("#delCause").hide();
        if (isDelete == 1){
            $("#delCause").show();
        }
        $("#theBody").html("");
        for (var i = 0; i < res.list.length; i++) {
            $("#theBody").append("<tr>\n" +
                "<input type='hidden' name='openId' value='" + res.list[i].openId + "'/>" +
                "<td class=\"\" style=\"padding-right:0px\">\n" +
                "     <input id=\"selall\" type=\"checkbox\" class=\"icheckbox_square-blue\">\n" +
                "</td>" +
                "<td>" + res.list[i].id + "</td>\n" +
                "<td>" + res.list[i].name + "</td>\n" +
                "<td>" + res.list[i].content + "</td>\n" +
                "<td>" + res.list[i].lookTotal + "</td>\n" +
                "<td>" + toDate(res.list[i].createDate) + "</td>\n" +
                "<td style='" + ((isDelete == 0) ? 'display:none;' : '') + "'>" + res.list[i].delCause + "</td>\n" +
                "<td><a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selDetail(" + res.list[i].id + ")'>详情</a></td>" +
                "</tr>")
        }
        $("#total").html(res.totalCount);
        $("#totalPages").html(res.totalPages);
        $("#pageNo").html(currentPage);
    })
}

function selDetail(id) {
    $.getJSON("/manager/selByOpenId", {"id": id}, function (res) {
        $("#id").val(res.information.id);
        $("#name").html(res.user.name);
        $("#content").html(res.information.content);
        var images = res.information.images.split(',');
        $("#images").html("");
        for (var i = 0; i < images.length; i++) {
            $("#images").append("<img width='50px' height='50px' src=\"" + images[i] + "\"/>")
        }
    })
}

function del() {
    var delCause = prompt("输入删除原因，点击确定");
    if (delCause){
        $.getJSON("/manager/delUserInfo", {"id" : $("#id").val(), "delCause":delCause, "isDelete":1}, function (res) {
           if (res){
               search(1,isDelete);
           } else {
               alert("删除失败");
           }
        });
    } else {
        alert("取消");
    }
}

function delList(btn) {
    isDelete = $(btn).attr("delId");
    if (isDelete == 1){
        $("#del").hide();
        $(btn).attr("delId",0);
        $(btn).html("返回");
        $(btn).attr("title", "已删除列表");
    } else {
        $("#del").show();
        $(btn).attr("delId",1);
        $(btn).html("已删除列表");
        $(btn).attr("title", "已删除列表");
    }
    search(1, isDelete);
}