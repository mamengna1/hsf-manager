function selUserById(id) {
    $.getJSON("/manager/user/selUserById",{"id":id},function (data) {
        $("#ids,#imageUrl,#openId,#nickName,#userType,#balanceMoney,#totalScore,#balanceScore").val("");
        $("#ids").val(data.id)
        $("#imageUrl2").attr("src",data.headPic);
        $("#openId2").val(data.openId)
        $("#nickName2").val(data.nickName)
        $("#phone2").val(data.phone)
        var t = data.memberType ==null ? 0 : data.memberType;
        $("#userType2").val(t)
        $("#totalScore2").val(data.totalScore);
        $("#balanceScore2").val(data.balanceScore);
    })
}