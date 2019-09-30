

//初始化数据
var currentPage = 1;  //当前页码
var openId ;
var scoreSourceId = -1   //积分来源
var userName = $("#userName").val();

function getUserId(userParent) {
    var userParentId
    $.getJSON("/manager/user/selUserByOpenId",{"openId":userParent},function (data) {
        userParentId = data.id;
    })
    return userParentId;
}

//过滤查询
function searchUserScore(currentPage,scoreSourceId,userName) {
    $.ajaxSettings.async = false;
    openId = $("#openId1").val();
    $.getJSON("/manager/userScore/userAll",{"pageCurrentNo":currentPage,"openId":openId,"scoreSourceId":scoreSourceId,"userName":userName},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            var createDate = toDate(data.list[i].createDate)
            var a = data.list[i].user.detailId;
            var biaoshi;
            if(a == 0 || a == '' || a== null){
                biaoshi = "用户"
            }else{
                biaoshi = "师傅"
            }
          //  var scoreSourceId = data.list[i].scoreSourceId
            if(scoreSourceId == 2 || scoreSourceId == 4){
                $("#fenhong").show();
            }else{
               $("#fenhong").hide();
            }
            var note  = data.list[i].note == null ? '' : data.list[i].note;
            var userId = getUserId(data.list[i].openId)
            var sourceId = (data.list[i].sourceOpenId == null || data.list[i].sourceOpenId == undefined) ? '' : getUserId(data.list[i].sourceOpenId)
            var name = data.list[i].user.nickName== null ? "无名氏": data.list[i].user.nickName
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck' name='checkbox' /></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserByOpenId(\""+data.list[i].openId+"\")'>"+ name+"</a>" +
                "</td>" +
                "<td>" + userId + "</td>" +
                "<td>" + data.list[i].score + "</td>" +
                "<td>" + data.list[i].scoreSourceType.sourceName + "</td>" +
                "<td style='" + ((scoreSourceId == 2|| scoreSourceId ==4) ? '' : 'display:none;') + "'>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserByOpenId(\""+data.list[i].sourceOpenId+"\")' >"+ sourceId+"</a>" +
                "</td>" +
                "<td>" + biaoshi + "</td>" +
                "<td>" + createDate+ "</td>" +
                "<td>" +note+ "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#updateModal\" class=\"btn btn-xs btn-warning\" onclick='goUpdScore("+data.list[i].id+")'>积分回馈</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-danger\" onclick='delScoreById("+data.list[i].id+")'>删除</a>" +
                "</td>" +
                "</tr>")

        }
        $.ajaxSettings.async = true;
        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        $("#pageNo").html(currentPage);
    }
}

//初始化加载数据
$(function () {
    openId = $("#openId1").val();
  searchUserScore(currentPage,scoreSourceId,userName);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
      searchUserScore(currentPage,scoreSourceId,userName);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
          searchUserScore(currentPage,scoreSourceId,userName);
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
          searchUserScore(currentPage,scoreSourceId,userName);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
      searchUserScore(currentPage,scoreSourceId,userName);
        $("#pageNo").html(currentPage);
    })

})

function searchUserScore1(btn) {
    searchUserScore(1,$(btn).val(),userName);
}

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
        $("#ids").val(data.user.id)
        $("#openId3").val(data.openId)
        $("#nickName2").val(data.user.nickName)
        $("#totalScore2").val(data.user.totalScore)
    })
}

/**
 * 保存修改结果
 */
function saveScore() {
    var id = $("#ids").val();   //用户id
    var totalS = $("#totalS").val();   //奖励积分
    var sources = $("#sources").val();   //积分来源
    var source = $("#source").val();   //是否发送模板
    var note = $("#note").val();   //备注
    $.getJSON("/manager/userScore/updUserScore",{"id":id,"score":totalS,"sources":sources,"source":source,"note":note},function (data) {
        if(data == true){
            alert("修改成功")
            window.location.reload();
        }else {
            alert("修改失败")
        }
    })
}

/**
 * 根据用户昵称进行查找
 */
function searchCash() {
    var name = $("#userName").val();
    searchUserScore(currentPage,scoreSourceId,name)
}

/**
 * 批量删除
 */
function delUserScoreAll() {
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
        var resMessage = confirm("您确认删除这些数据吗？");
        if (resMessage == true) {
            $.getJSON("/manager/userScore/delUserScoreAll", {"ids": arrayId.toString()}, function (data) {
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


function changeSou(btn) {
    $("#source").val(btn);
}