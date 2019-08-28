

//初始化数据
var currentPage = 1;  //当前页码
//过滤查询
function searchDistribution(currentPage) {
    $.getJSON("/manager/distribution/userAll",{"pageCurrentNo":currentPage},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            var createTime = toDate(new Date(data.list[i].createTime).toJSON())
            var updateTime = data.list[i].updateTime == null ? '' : toDate(new Date(data.list[i].updateTime).toJSON());
            var message = data.list[i].refusedMessage == null ? '' : data.list[i].refusedMessage
            var a = data.list[i].statusId;
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck'/></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" + data.list[i].realName + "</td>" +
                "<td>" + data.list[i].realTitle + "</td>" +
                "<td>" + data.list[i].sfName + "</td>" +
                "<td>" + data.list[i].statusName + "</td>" +
                "<td>" + message + "</td>" +
                "<td>" + createTime + "</td>" +
                "<td>" + updateTime+ "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\">修改</a>" +
                "&nbsp; &nbsp;  <a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" onclick='confirmAll("+data.list[i].id+")' style=\"" + ((a ==7 ) ? '' : 'display:none;')+"\" >确认完工</a>" +
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

   searchDistribution(currentPage);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
       searchDistribution(currentPage);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
           searchDistribution(currentPage);
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
           searchDistribution(currentPage);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
       searchDistribution(currentPage);
        $("#pageNo").html(currentPage);
    })

})


//删除
function delUser() {
    var delUsers = document.getElementsByClassName("userCheck");
    for (var i = 0; i < delUsers.length; i++) {
        if (delUsers[i].checked) {
            var id = $(delUsers[i]).parent().next().html();
            $.getJSON("/manager/user/delUserById", {"id": id}, function (data) {
                if(data == true){
                    alert("删除成功！")
                    window.location.reload();
                }else {
                    alert("删除失败！")
                }
            });
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
        var workAddress = showProvince(data.userDetail.workProvince,data.userDetail.workCity,data.userDetail.workArea)
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
    location.href="/manager/userDetail/goUpdUserRelease?id="+id;
}


/**
 * 确认完工
 * @param id
 */
function confirmAll(id) {
    var msg = "您确认要确定完工吗？";
    if (confirm(msg)==true){
        $.getJSON("/manager/distribution/confirmAll",{"id":id},function (data) {
            if(data == true ){
                alert("确认完工成功！")
                window.location.reload();
            }else{
                alert("确认完工失败！")
            }
        })
        return true;
    }else{
        alert("取消成功")
        return false;
    }
}