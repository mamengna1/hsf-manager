$(function () {
    var workAddress1 = ($("#workAddress1").val()).split(",");
    for (var i = 0; i < workAddress1.length; i++) {
        $("[value="+[workAddress1[i]]+"][class=quanXian]").prop("checked","checked")
    }


    $("#bbb").on("click",":checkbox[class=quanXian]",function () {
        var workAddress = $("#workAddress1").val().split(",");
        if ($(this).prop('checked')) {
            if($(this).val() != -1){
                workAddress.push($(this).val());
            }
        } else {
            workAddress.splice(jQuery.inArray($(this).val(), workAddress), 1);  //从数组中删除当前项，删除1个
        }
        $("#workAddress1").val(workAddress.join());
        var s =workAddress.join()
        if (s.substr(0,1)==',')
            s=s.substr(1);
        $("#workAddress").val(s);

    })
    $.ajaxSettings.async = false;
    var id = $("#id").val();
    $.getJSON("/manager/userMaster/selById", {"id": id}, function (res) {
        $("#name").val(res.name);
        $("#card").val(res.card);
        $("#address").val(res.address);
        chooseProvince(res.placeProvince);
        $("#placeProvince").val(res.placeProvince);
        chooseCity(res.placeCity);
        $("#placeCity").val(res.placeCity);
        $("#placeArea").val(res.placeArea);

        $("#workProvince").val(res.workProvince);
        $("#workCity").val(res.workCity);
        


        var totalRefuse = res.totalRefuse == null ? 0 : res.totalRefuse
        var totalOrder = res.totalOrder == null ? 0 : res.totalOrder
        $("#totalRefuse").val(totalRefuse);
        $("#totalOrder").val(totalOrder);


        $("#yearWorkId").val(res.yearWorkId);
        var skills = (res.skills).split(",");
        var tags = document.getElementsByClassName("skills");
        for (var i = 0; i < skills.length; i++) {
            $("[sid="+skills[i]+"]").prop("checked", "checked");
        }
        $("#skills").val(res.skills);
        $("#statusMessage").val(res.statusMessage)
        var stateType;
        if( res.status == 1){   //审核通过
            stateType = 1
            $("#statusMessage").attr("disabled","disabled")

        }else if(res.status == 2 || res.status == 3){   //审核不通过及再次提交的信息
            stateType = 2
            if($('#statusMessage').attr("disabled")==true){//判断input元素是否已经设置了disabled属性
                $('#statusMessage').removeAttr("disabled");//去除textarea元素的disabled属性
            }

        }else if(res.status == 0 || res.status == ''){   //审核中
            stateType = -1
            $("#statusMessage").attr("disabled","disabled")
        }else { //4 待激活 5  待恢复
            stateType = res.status
            $("#statusMessage").attr("disabled","disabled")
        }
        $("#status").val(stateType)

    })
    $.ajaxSettings.async = true;
    var skills = $("#skills").val().split(",");
    $(".skills").click(function () {
        var len = $(".skills").filter(":checked").length;
        if (len > 3) {
            if ($(this).prop('checked')) {
                return false;
            }
        }
        if ($(this).prop('checked')) {
            skills.push($(this).attr("sid"));
        } else {
            skills.splice(jQuery.inArray($(this).attr("sid"), skills), 1);
        }
        $("#skills").val(skills.join());
    })



});

function changeSou(btn) {
    $("#source").val(btn);
}

/**
 * 修改中的 返回
 */
$("#returnUpUserMaster").click(function () {
    location.href="/manager/userMaster/selMaster"
})

/**
 * 审核下拉改变触发事件
 */
function func(){
    //获取被选中的option标签
    var vs = $('#status  option:selected').val();
    if(vs == 1){
        $("#statusMessage").val("")
        $("#statusMessage").attr("disabled","disabled")
    }else if(vs == 2){
        $('#statusMessage').removeAttr("disabled");//去除textarea元素的disabled属性

    }
}

