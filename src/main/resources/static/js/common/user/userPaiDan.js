

//初始化数据
var currentPage = 1;  //当前页码
var skillId = -1;
var serviceProvince
var serviceCity
var serviceArea
var userReleaseId;
//过滤查询
function searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea,userReleaseId) {
    $.getJSON("/manager/userDetail/userAll",{"pageCurrentNo":currentPage,"skillId":skillId,"serviceProvince":serviceProvince,"serviceCity":serviceCity,"serviceArea":serviceArea,"recId":userReleaseId},callback)
    //回调
    function callback(data) {
        $.ajaxSettings.async = false;
        $("#theBody").html("");

        for (var i = 0; i < data.list.length; i++) {
            var workArea = showProvince(data.list[i].workProvince,data.list[i].workCity,data.list[i].workArea);
            var skillName = getSkillName(data.list[i].skills)
            if(data.list[i].isExist== "true"){
                $("#theBody").append("<tr>" +
                    "<td><input type=\"checkbox\" class='userCheck'/></td>" +
                    "<td>" + data.list[i].id + "</td>" +
                    "<td>" + data.list[i].name + "</td>" +
                    "<td>" + data.list[i].card + "</td>" +
                    "<td>" + skillName + "</td>" +
                    "<td>" + data.list[i].yearWorkId + "</td>" +
                    "<td>" + workArea+"</td>" +
                    "<td>" +
                    "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\"  onclick='updPaiDan("+data.list[i].id+")'>确认派单</a>" +
                    "</td>" +
                    "</tr>")
            }


        }

        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        $("#pageNo").html(currentPage);
    }
    $.ajaxSettings.async = true;
}

//初始化加载数据
$(function () {
    serviceProvince = $("#serviceProvince").val();
    serviceCity = $("#serviceCity").val();
    serviceArea = $("#serviceArea").val();
    userReleaseId = $("#userReleaseId").val();
   searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea,userReleaseId)
    //首页
    $("#begin").click(function () {
        currentPage = 1;
           searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea,userReleaseId)
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
               searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea,userReleaseId)
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
               searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea,userReleaseId)
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
           searchPaiDan(currentPage,skillId,serviceProvince,serviceCity,serviceArea,userReleaseId)
        $("#pageNo").html(currentPage);
    })

})

/**
 * 派单下拉选择
 */
function searchPaiDanSkill() {
    var skills = $("#skills").val();
    var skills2 = $("#skills2").val();
   // var S = new Array();
    var skill;
    if(skills == -1){   //都不选择时
        skill = -1;
    }else if (skills != -1 && skills2 == -1){   //只选择大类不选择小类时，得到此大类的所有小类

        var r = trimSpace(C)
        var a = r.join();
        if(a.substr(0,1)=='-1'){
            a = a.substr(1)
        }
        skill = a;
    } else if(skills != -1 && skills2 != -1){    //选择了大类中的小类
        skill = skills2;
    }

    alert("最终skill : "+ skill)

    searchPaiDan(currentPage,skill,serviceProvince,serviceCity,serviceArea)
}

/**
 * 派单
 * @param id
 */
function updPaiDan(id) {
    var userReId  = userReleaseId;   // 发布信息id
    $.getJSON("/manager/userDetail/updPaiDan",{"id":userReId,"userDetailId":id},function (data) {
        if(data == true){
            alert("派单请求发送成功")
          /*location.href="/manager/userRelease/goUserReleaseAll";*/
            window.location.reload()

        }else{
            alert("该条雇佣信息已经为这名师傅派过单了，请勿重复派单！")
        }
    })
}

function getSkillName(s) {
    //技能
    var skills = (s).split(",");
    var array = new Array();
    for (var i = 0; i < skills.length; i ++){
        array[i] =  userSkillById(skills[i])
    }
    var a = array.join();
    if(a.substr(0,1)==','){
        a = a.substr(1)
    }
    return a;
}