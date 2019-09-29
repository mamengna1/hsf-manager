function selUserByOpenId(openId) {
    $.getJSON("/manager/user/selUserByOpenId",{"openId":openId},function (data) {
        $("#imageUrl,#openId,#nickName,#sex,#address,#userType,#phone,#totalMoney,#balanceMoney,#totalScore,#balanceScore,#lastLoginTime,#openId").html("");
        $("#imageUrl").attr("src",data.headPic);
        $("#nickName").append(data.nickName)
        var sex= data.sex == 1 ? "男" : "女";
        $("#sex").append(sex)
        $("#address").append(data.country+"/"+data.province+"/"+data.city)
        var type  = data.memberType==0 ? "普通会员":"VIP会员";
        $("#userType").append(type)
        $("#phone").append(data.phone)
        $("#totalMoney").append(data.totalMoney)
        $("#balanceMoney").append(data.balanceMoney)
        $("#totalScore").append(data.totalScore);
        $("#balanceScore").append(data.balanceScore);
        $("#openId").append(data.openId)
        var date
        if(data.lastLoginTime == null ){
            data = '';
        }else{
          /*  var da = new Date(data.lastLoginTime).toJSON();
            date = new Date(da+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'')*/
            // 日期转换
            var da = new Date(data.lastLoginTime);
            return new Date(+new Date(da)+8*3600*1000).toISOString().replace(/\-/g, '/').replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
        }

        $("#lastLoginTime").append(date)
    })
}