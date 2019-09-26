

//初始化数据
var currentPage = 1;  //当前页码
var commentTypeId = -1;
//过滤查询
function searchUserOrder(currentPage,commentTypeId) {
    $.getJSON("/manager/userOrder/userAll",{"pageCurrentNo":currentPage,"commentTypeId":commentTypeId},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            var commentTime = toDate(new Date(data.list[i].commentTime).toJSON())
            var a = data.list[i].commentTypeId;

            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck' name='checkbox' /></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#shifuModal\"  onclick='selShifuById(\""+data.list[i].sfId+"\")'>"+ data.list[i].sfId+"</a>" +
                "</td>" +
                "<td>" + data.list[i].comments + "</td>" +
                "<td>" + data.list[i].starLevel + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selById(\""+data.list[i].userId+"\")'>"+ data.list[i].userId+"</a>" +
                "</td>" +
                "<td>" + commentTime+ "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#updOrderModal\" class=\"btn btn-xs btn-warning\" onclick='goUpdOrder("+data.list[i].id+")'>修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-danger\" onclick='delUserOrder("+data.list[i].id+")'>删除</a>" +
                "</td>" +
                "</tr>")
        }

        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        var curr = data.totalPages == 0 ? 0 : currentPage
        $("#pageNo").html(curr);
    }
}

//初始化加载数据
$(function () {

    searchUserOrder(currentPage,commentTypeId);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        searchUserOrder(currentPage,commentTypeId);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            searchUserOrder(currentPage,commentTypeId);
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
            searchUserOrder(currentPage,commentTypeId);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        searchUserOrder(currentPage,commentTypeId);
        $("#pageNo").html(currentPage);
    })

})


//删除
function delUserOrder(id) {
    var r=confirm("您确认要删除此条评论吗？")
    if(r == true){
        $.getJSON("/manager/userOrder/delUserOrder", {"id": id}, function (data) {
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
            //location.href="/manager/user/goUserIndex";
            window.location.reload();
        }else{
            alert("修改失败")
        }
    })
}

/**
 * 查询用户（发布人信息）
 * @param id
 */
function selById(id) {
    $.ajaxSettings.async = false;
    $.getJSON("/manager/user/selUserById",{"id":id},function (data) {
        var openId = data.openId;
        selUserByOpenId(openId);
    })
    $.ajaxSettings.async = true;
}

/**
 * 查询师傅详情信息
 * @param id
 */
function selShifuById(id) {
    $.ajaxSettings.async = false;
    $.getJSON("/manager/userMaster/selRealMessage",{"id":id},function (data) {
        $("#realName,#card,#phones,#workAddress,#skills,#workYear").html("");
        $("#realName").html(data.userDetail.name)
        $("#card").html(data.userDetail.card)
        $("#phones").html(data.user.phone)

        var liveAddress = showProvince(data.userDetail.placeProvince,data.userDetail.placeCity,data.userDetail.placeArea)
        $("#liveAddress").html(liveAddress)
        var workAddress2 = getWorkName(data.userDetail.workProvince,data.userDetail.workCity,data.userDetail.workArea)
        $("#workAddress").html(workAddress2)
        //技能
        var skills = (data.userDetail.skills).split(",");
        var array = new Array();
        for (var i = 0; i < skills.length; i ++){
            array[i] =  userSkillById(skills[i])
        }
        var a = array.join();
        if(a.substr(0,1)==','){
            a = a.substr(1)
        }

        $("#skills").html(a);
        var yearWork = userWorkYearById(data.userDetail.yearWorkId);
        $("#workYear").html(yearWork);
    })
    $.ajaxSettings.async = true;
}



/**
 * 显示修改信息
 * @param id
 */
function goUpdOrder(id) {
    $.getJSON("/manager/userOrder/selUserOrderById",{"id":id},function (data) {
        $("#id,#comments,#starLevel,#userId").val("");
        $("#id").val(data.userOrder.id);
        $("#comments").val(data.userOrder.comments)
        $("#starLevel").val(data.userOrder.starLevel)
        $("#userId").val(data.user.nickName)
    })
}

function saveOrders() {
    var id = $("#id").val();
    var comments = $("#comments").val();
    var starLevel = $("#starLevel").val();
    $.getJSON("/manager/userOrder/updUserOrder",{"id":id,"comments":comments,"starLevel":starLevel},function (data) {
        if(data == true){
            alert("修改成功")
            window.location.reload();
        }else {
            alert("修改失败")
        }
    })
}