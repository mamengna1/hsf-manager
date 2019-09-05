/**
 * 点击真实姓名出现
 * @param id
 */
function selUserDetailById(id) {
    $.ajaxSettings.async = false;
    $.getJSON("/manager/userMaster/selRealMessage",{"id":id},function (data) {
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
        var a = array.join();
        if(a.substr(0,1)==','){
            a = a.substr(1)
        }
        $("#skills").html(a);

    })

    $.ajaxSettings.async = true;
}

/**
 * 点击id出现微信上的信息
 * @param id
 */
function selUserDetail(id) {
    $.getJSON("/manager/userMaster/selRealMessage",{"id":id},function (data) {

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
    $.getJSON("/manager/userMaster/selRealMessage",{"id":id},function (data) {
        $("#realNames,#card").html("");
        $("#idss,#stateType,#statusMessage").val("")
        var stateType;
        if( data.userDetail.status == 1){
            stateType = 1
        }else if(data.userDetail.status == 2 || data.userDetail.status == 3){
            stateType = 2
        }else if(data.userDetail.status == 0 || data.userDetail.status == ''){
            stateType = -1
        }
        $("#stateType").val(stateType)
        $("#statusMessage").val(data.userDetail.statusMessage)
        $("#realNames").html(data.userDetail.name)
        $("#idss").val(data.userDetail.id)
        $("#card").html(data.userDetail.card)
    })
}
/**
 * 审核
 * @param status
 */
function updateAudit() {
    var stateType = $("#stateType").val();
    var id = $("#idss").val();
    var statusMessage =  stateType == 1 ? '' : $("#statusMessage").val();
    var flag= true;
    if(stateType == -1){
        alert("您没有选择改变审核状态，此次审核取消")
        flag = false;
    }
    if(flag == true){
        $.getJSON("/manager/userMaster/updateUserDetail",{"id":id,"status":stateType,"statusMessage":statusMessage},function (data) {
            if(data ==true){
                alert("修改审核结果成功！")
                window.location.reload();
            }else{
                alert("审核失败成功！")
                window.location.reload();
            }
        })
    }

}