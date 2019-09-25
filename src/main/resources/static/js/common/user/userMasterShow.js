$(function () {
    $.ajaxSettings.async = false;
    var id = $("#id").val();
    $.getJSON("/manager/userMaster/selById", {"id": id}, function (res) {
        $("#name,#card,#address,#placeArea,#yearWorkId,#workArea,#skills,#card，#workYear2,#totalRefuse").val("");
        $("#name").val(res.name);
        $("#card").val(res.card);
        $("#address").val(res.address);

        var placeArea =   showProvince(res.placeProvince,res.placeCity,res.placeArea) +"/"+ res.address;
        $("#placeArea").val(placeArea);
       // var workArea =   showProvince(res.workProvince,res.workCity,res.workArea);
        var workArea = getWorkName(1,2,"2,3,4");
        $("#workArea").val(workArea);

        //技能
        var skills = (res.skills).split(",");
        var array = new Array();
        for (var i = 0; i < skills.length; i ++){
            array[i] =  userSkillById(skills[i])
        }
        var a = array.join();
        if(a.substr(0,1)==','){
            a = a.substr(1)
        }
        $("#skills").val(a);
        //工作年限
        var yearWork = userWorkYearById(res.yearWorkId);
        $("#workYear2").val(yearWork);
        var totalRefuse = res.totalRefuse == null ? 0 : res.totalRefuse
        var totalOrder = res.totalOrder == null ? 0 : res.totalOrder
        $("#totalRefuse").val(totalRefuse)
        $("#totalOrder").val(totalOrder)
})
    $.ajaxSettings.async = true;

});

/**
 * 修改中的 返回
 */
$("#returnUpUserMaster").click(function () {
    location.href="/manager/userMaster/selMaster"
})