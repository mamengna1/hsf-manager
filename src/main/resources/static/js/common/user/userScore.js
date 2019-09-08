

//初始化数据
var currentPage = 1;  //当前页码
var openId ;
var scoreSourceId = -1   //积分来源
//过滤查询
function searchUserScore(currentPage,scoreSourceId) {
    openId = $("#openId1").val();
    $.getJSON("/manager/userScore/userAll",{"pageCurrentNo":currentPage,"openId":openId,"scoreSourceId":scoreSourceId},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            var createDate = toDate(new Date(data.list[i].createDate).toJSON())
            var a = data.list[i].user.detailId;
            var biaoshi;
            if(a == 0 || a == '' || a== null){
                biaoshi = "用户"
            }else{
                biaoshi = "师傅"
            }
            var sourceOpenId = data.list[i].sourceOpenId
            if(sourceOpenId == 2 || sourceOpenId == 4){
                $("#fenhong").show();
            }else{
               $("#fenhong").hide();
            }
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck'/></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserByOpenId(\""+data.list[i].openId+"\")'>"+ data.list[i].user.nickName+"</a>" +
                "</td>" +
                "<td>" + data.list[i].openId + "</td>" +
                "<td>" + data.list[i].score + "</td>" +
                "<td>" + data.list[i].scoreSourceType.sourceName + "</td>" +
                "<td style='" + ((sourceOpenId == 2|| sourceOpenId ==4) ? '' : 'display:none;') + "'>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserByOpenId(\""+data.list[i].sourceOpenId+"\")' >"+ data.list[i].sourceOpenId+"</a>" +
                "</td>" +
                "<td>" + biaoshi + "</td>" +
                "<td>" + createDate+ "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#updateModal\" class=\"btn bg-olive btn-xs\" onclick='goUpdScore("+data.list[i].id+")'>修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" onclick='delScoreById("+data.list[i].id+")'>删除</a>" +
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
    openId = $("#openId1").val();
  searchUserScore(currentPage,scoreSourceId);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
      searchUserScore(currentPage,scoreSourceId);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
          searchUserScore(currentPage,scoreSourceId);
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
          searchUserScore(currentPage,scoreSourceId);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
      searchUserScore(currentPage,scoreSourceId);
        $("#pageNo").html(currentPage);
    })

})


//删除
function delScoreById(id) {
    var r=confirm("您确认要删除此条积分记录吗？")
    if(r == true){
        $.getJSON("/manager/userScore/delScoreById", {"id": id}, function (data) {
            if(data == true){
                alert("删除成功！")
                window.location.reload();
            }else {
                alert("删除失败！")
            }
        });
    }else{
        alert("您取消了删除")
    }
}

/**
 * 修改数据的渲染
 * @param id
 */
function goUpdScore(id) {
    $.getJSON("/manager/userScore/selScoreById",{"id":id},function (data) {
        $("#ids,#nickName2,#score,#scoreSourceId,#openId3").val("")
        $("#ids").val(data.id)
        $("#openId3").val(data.openId)
        $("#nickName2").val(data.user.nickName)
        $("#score").val(data.score)
        $("#scoreSourceId").val(data.scoreSourceId)
    })
}

/**
 * 保存修改结果
 */
function saveScore() {
    var id = $("#ids").val();
    var score = $("#score").val();
    var scoreSourceId = $("#scoreSourceId").val();
    $.getJSON("/manager/userScore/updScore",{"id":id,"score":score,"scoreSourceId":scoreSourceId},function (data) {
        if(data == true){
            alert("修改成功")
            window.location.reload();
        }else {
            alert("修改失败")
        }
    })
}