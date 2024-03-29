//此js是全部订单

//初始化数据
var currentPage = 1;  //当前页码
var state = -1;
var mark;
//过滤查询
function searchRelease(currentPage,state,mark) {
    $.ajaxSettings.async = false;
    $.getJSON("/manager/userRelease/userAll",{"pageCurrentNo":currentPage,"state":state,"mark":mark},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");

        for (var i = 0; i < data.list.length; i++) {
            $("#release1,#release2,#release3,#release4").show();
            if(mark == 1){
                $("#release1,#release2,#release3,#release4").show();
            }else if(mark == 2){
                $("#release1,#release2,#release3,#release4").hide();
            }
            var appointTime = toDate(data.list[i].appointTime)
            var createDate = toDate(data.list[i].createDate)
            var address  = getUserWork(data.list[i].serviceProvince,data.list[i].serviceCity,data.list[i].serviceArea)+"/"+data.list[i].serverDetail;
            var receiveId = data.list[i].receiveId == null ? '' :data.list[i].receiveId;
            var a = data.list[i].state;
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck' name='checkbox'/></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" + data.list[i].title + "</td>" +
                "<td>" + data.list[i].nickName + "</td>" +
                "<td>" + data.list[i].phone + "</td>" +
                "<td>" + address + "</td>" +
                "<td>" + data.list[i].demand + "</td>" +
                "<td>" + appointTime+ "</td>" +
                "<td>" + createDate+ "</td>" +
                "<td>" + data.list[i].distributionStatus.statusName+ "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selById(\""+data.list[i].userId+"\")'>"+ data.list[i].userId+"</a>" +
                "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#shifuModal\"  onclick='selShifuById(\""+data.list[i].receiveId+"\")'>"+ receiveId+"</a>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" onclick='goShowUserRelease("+data.list[i].id+")'>查看</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-warning\" onclick='goUpdUserRelease("+data.list[i].id+")'>修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-info\" data-toggle=\"modal\" data-target=\"#updateModal\" onclick='gopaidan("+data.list[i].id+")' style=\"" + ((a ==0  || a ==1) ? '' : 'display:none;')+"\">派单</a>" +
                "</td>" +
                "</tr>")
        }



        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        var curr = data.totalPages == 0 ? 0 : currentPage
        $("#pageNo").html(curr);
        $.ajaxSettings.async = true;
    }
}

//初始化加载数据
$(function () {
    mark = $("#mark").val()

   searchRelease(currentPage,state,mark);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
       searchRelease(currentPage,state,mark);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
           searchRelease(currentPage,state,mark);
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
           searchRelease(currentPage,state,mark);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
       searchRelease(currentPage,state,mark);
        $("#pageNo").html(currentPage);
    })

})


//删除
function delRelById() {
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
    }else {
        var resMessage = confirm("您确认删除这些数据吗？");
        if (resMessage == true) {
            $.getJSON("/manager/userRelease/delRelById", {"ids": arrayId.toString()}, function (data) {
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
        var workAddress = getWorkName(data.userDetail.workProvince,data.userDetail.workCity,data.userDetail.workArea)
        $("#workAddress").html(workAddress)

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
 * 去到派单页面
 * @param id
 */
function gopaidan(id) {
    location.href="/manager/userDetail/goPaiDan?id="+id;
}

/**
 * 去到修改界面
 * @param id
 */
function goUpdUserRelease(id) {
    location.href="/manager/userDetail/goUpdUserRelease?id="+id+"&mark="+mark;
    //   location.href="/manager/userRelease/goUserReleaseAll?mark="+mark;
}

/**
 * 去到查看信息界面
 * @param id
 */
function goShowUserRelease(id) {
    location.href="/manager/userRelease/goShowUserRelease?id="+id;

}