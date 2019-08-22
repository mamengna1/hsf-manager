/**
 * 点击真实姓名出现
 * @param id
 */
function selUserDetailById(id) {
    $.ajaxSettings.async = false;
    $.getJSON("/userMaster/selRealMessage",{"id":id},function (data) {
        $("#realName,#birthArea,#phones,#nickName,#skills").html("");
        $("#realName").html(data.userDetail.name)
        var birthArea = showProvince(data.userDetail.placeProvince,data.userDetail.placeCity,data.userDetail.placeArea)
        $("#birthArea").html(birthArea)
        $("#phones").html(data.user.phone)

        //技能
        var skills = (data.userDetail.skills).split(",");
        var array = new Array();
        for (var i = 0; i < skills.length; i ++){
            array[i] =  userSkillById(skills[i])
        }

        $("#skills").html(array.join());

    })

    $.ajaxSettings.async = true;
}

/**
 * 点击id出现微信上的信息
 * @param id
 */
function selUserDetail(id) {
    $.getJSON("/userMaster/selRealMessage",{"id":id},function (data) {

        $("#openId").val("")
        $("#imageUrl,#openId,#nickName,#sex,#address,#userType,#phone,#totalMoney,#balanceMoney,#totalScore,#balanceScore,#lastLoginTime").html("");
        var openId = data.user.openId;
        if(openId !=null && openId !=0 && openId !='' && openId !=undefined){
            $("#openId").val(openId)
            var openIds = $("#openId").val();
            selUserByOpenId(openIds);
        }

    })
}

/**
 * 审核数据的渲染
 * @param id
 */
function selAudit(id) {
    $.getJSON("/userMaster/selRealMessage",{"id":id},function (data) {
        $("#realNames,#card").html("");
        $("#idss").val("")

        $("#realNames").html(data.userDetail.name)
        $("#idss").val(data.userDetail.id)
        $("#card").html(data.userDetail.card)
    })
}
/**
 * 审核
 * @param status
 */
function updateAudit(status) {
    var id = $("#idss").val();
    var statusMessage =  status == 1 ? '' : $("#statusMessage").val();
    $.getJSON("/userMaster/updateUserDetail",{"id":id,"status":status,"statusMessage":statusMessage},function (data) {
        if(data ==true){
            alert("修改审核结果成功！")
            location.href="/userMaster/goUserMasterAudit";
        }else{
            alert("修改审核结果失败！")
        }
    })
}