
/**
 * ajax渲染全部
 */
//初始化数据
var currentPage = 1;  //当前页码
function skillAll() {
    $.getJSON("/manager/userSkill/skillAll",{"pageCurrentNo":currentPage},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck'/></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" + data.list[i].skillName + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" data-toggle=\"modal\" data-target=\"#updModal\" onclick='updateSkill("+data.list[i].id+")'>修改</a>" +
                "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" onclick='delSkill("+data.list[i].id+")'>删除</a>" +
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

    skillAll(currentPage);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        skillAll(currentPage);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            skillAll(currentPage);
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
            skillAll(currentPage);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        skillAll(currentPage);
        $("#pageNo").html(currentPage);
    })

})

/**
 * 新增
 */
function saveSkill() {
   var skillName = $("#skillName").val();
   $.getJSON("/manager/userSkill/saveSkill",{"skillName":skillName},function (data) {
       if(data ==  true){
           alert("新增成功！")
           window.location.reload();
       }else{
           alert("新增失败")
       }
   })
}

/**
 * 渲染修改
 */
function updateSkill(id) {
    $.getJSON("/manager/userSkill/UserSkillById",{"id":id},function (data) {
        $("#skillName1,#skillId").val("")
        $("#skillName1").val(data.skillName)
        $("#skillId").val(data.id)
    })
}

/**
 * 保存修改结果
 */
function saveUpdSkill() {
    var id =  $("#skillId").val();
    var skillName = $("#skillName2").val();
    $.getJSON("/manager/userSkill/updateSkill",{"id":id,"skillName":skillName},function (data) {
        if(data ==  true){
            alert("修改成功！")
            window.location.reload();
        }else{
            alert("修改失败")
        }
    })
}
/**
 * 删除
 */
function delSkill(id) {
    $.getJSON("/manager/userSkill/delSkill",{"id":id},function (data) {
        if(data ==  true){
            alert("删除成功！")
            window.location.reload();
        }else{
            alert("删除失败")
        }
    })
}