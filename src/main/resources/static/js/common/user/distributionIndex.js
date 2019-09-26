

//初始化数据
var currentPage = 1;  //当前页码
var statusId = -1;
var sfId;
//过滤查询
function searchDistribution(currentPage,statusId) {
    sfId = $("#sfId").val();
    if(sfId !=-1){
        $("#jiedan").show();
    }else if(sfId == -1){
        $("#jiedan").hide();
    }
    $.getJSON("/manager/distribution/userAllIndex",{"pageCurrentNo":currentPage,"statusId":statusId,"sfId":sfId},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
      
        for (var i = 0; i < data.list.length; i++) {
            var createTime = toDate(new Date(data.list[i].createTime).toJSON())
            var updateTime = data.list[i].updateTime == null ? '' : toDate(new Date(data.list[i].updateTime).toJSON());
            var message = data.list[i].refusedMessage == null ? '' : data.list[i].refusedMessage
            var a = data.list[i].statusId;
            $("#reason").hide()
            if(statusId == 3 || statusId == 5){   //拒绝和已取消
                $("#reason").show()
            }
            $("#theBody").append("<tr>" +
               /* "<td><input type=\"checkbox\" class='userCheck'/></td>" +*/
                "<td>" + data.list[i].id + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#guYongModal\"  onclick='selResById(\""+data.list[i].userRelease.id+"\")'>"+ data.list[i].userRelease.nickName+"</a>" +
                "</td>" +
                "<td>" + data.list[i].userRelease.title + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#shifuModal\"  onclick='selShifuById(\""+data.list[i].sfId+"\")'>"+ data.list[i].userDetail.name+"</a>" +
                "</td>" +
                "<td>" + data.list[i].distributionStatus.statusName + "</td>" +
                "<td style='" + ((statusId == 3|| statusId ==5) ? '' : 'display:none;') + "'>" + message + "</td>" +
                "<td>" + createTime + "</td>" +
                "<td>" + updateTime+ "</td>" +
                "<td>" +
                "<a href='javascript:void(0)' data-toggle=\"modal\" data-target=\"#updateModal\"  class=\"btn bg-olive btn-xs\" onclick='updDelById("+data.list[i].id+")'>修改</a>" +
                "&nbsp; &nbsp;  <a href='javascript:void(0)'  class=\"btn btn-xs btn-danger\" onclick='delDetailById("+data.list[i].id+")'>删除</a>" +
                "&nbsp; &nbsp;  <a href='javascript:void(0)'  class=\"btn btn-xs btn-success\" onclick='confirmAll("+data.list[i].id+")' style=\"" + ((a ==7 ) ? '' : 'display:none;')+"\" >确认完工</a>" +
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
   sfId = $("#sfId").val();
    searchDistribution(currentPage,statusId);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        searchDistribution(currentPage,statusId);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            searchDistribution(currentPage,statusId);
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
            searchDistribution(currentPage,statusId);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        searchDistribution(currentPage,statusId);
        $("#pageNo").html(currentPage);
    })

})


//删除
function delDetailById(id) {
    var resMessage=confirm("您确认删除此条记录吗？");
    if(resMessage == true) {
        $.getJSON("/manager/distribution/delDetailById", {"id": id}, function (data) {
            if (data == true) {
                alert("删除成功")
                window.location.reload();
            } else {
                alert("删除失败")
            }
        })
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
            $("#realName,#card,#phones2,#workAddress2,#skills,#workYear2").html("");
            $("#realName").html(data.userDetail.name)
            $("#card").html(data.userDetail.card)
            $("#phones2").html(data.user.phone)
            var liveAddress = showProvince(data.userDetail.placeProvince,data.userDetail.placeCity,data.userDetail.placeArea)
            $("#liveAddress").html(liveAddress)
            var workAddress2 = getWorkName(data.userDetail.workProvince,data.userDetail.workCity,data.userDetail.workArea)
            $("#workAddress2").html(workAddress2)

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
            $("#workYear2").html(yearWork);
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

    /**
     * 根据雇佣人姓名查看雇佣的内容
     * @param resId   雇佣人下单的id
     */
    function selResById(resId) {
        $.ajaxSettings.async = false;
        $.getJSON("/manager/userDetail/selUserReleaseById",{"id":resId},function (data) {
            $("#relName,#title,#phones,#workAddress,#appointTime,#demand").html("");

            $("#relName").append(data.nickName)
            $("#title").append(data.title)
            $("#phones").append(data.phone)
            var workAddress = getUserWork(data.serviceProvince,data.serviceCity,data.serviceArea)+"/"+data.serverDetail
            $("#workAddress").append(workAddress)
            var appointTime = toDate(new Date(data.appointTime).toJSON())
            $("#appointTime").append(appointTime)
            $("#demand").append(data.demand)

        })
        $.ajaxSettings.async = true;
    }

    /**
     * 渲染修改数据
     * @param id
     */
    function updDelById(id) {
        $.getJSON("/manager/distribution/updDelById",{"id" :id},function (data) {
            $("#id,#distName,#sfName,#statusId,#refusedMessage,#statuHidde").val("")
            $("#id").val(data.id)
            $("#distName").val(data.userRelease.nickName)
            $("#sfName").val(data.userDetail.name)
            $("#statusId").val(data.statusId)
            $("#refusedMessage").val(data.refusedMessage)
            $("#statuHidde").val(data.statusId)
        })
    }

    /**
     * 保存修改结果
     */
    function saveDis() {
        var id = $("#id").val();
        var statusId = $("#statusId").val();
        var refusedMessage = $("#refusedMessage").val();

        $.getJSON("/manager/distribution/saveDis",{"id":id,"statusId":statusId,"refusedMessage":refusedMessage},function (data) {
            if(data == true){
                alert("修改状态成功")
                window.location.reload();
            }else{
                alert("修改状态失败")
            }
        })
    }




/**
 * 从接单中心返回全部师傅界面
 */

function returnDis() {
    location.href="/manager/userMaster/selMaster?statusId=-1";
}

/**
 * 状态下拉改变触发事件
 */
/*
function changeStatus(){
    //获取被选中的option标签
    var vs = $('#statusId  option:selected').val();
    var statusId = $("#statuHidde").val();
    if(statusId == 1){   //当数据库中的状态为1 时
        if(vs !=1 || vs != 2){
            alert("您只能选择新订单 或接单")
        }
    }else if(statusId == 2){  //已接单

    }

}*/
