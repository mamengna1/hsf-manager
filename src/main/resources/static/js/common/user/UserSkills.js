
/**
 * ajax渲染全部
 */
//初始化数据
var currentPage = 1;  //当前页码
var parentId = -1;
var flag = true;
var  B=new Array();;

function skillAll(currentPage,parentId,flag) {
    $.getJSON("/manager/userSkill/showSkills",{"currentPage":currentPage,"parentId":parentId},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            if(flag == true){
                B[data.list[i].id] = data.list[i].skillName;
            }
            var name = B[parentId]+"/"+data.list[i].skillName
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck'/></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)' onclick='selById(\""+data.list[i].id+"\")' style=\"" + ((flag ==true) ? '' : 'display:none;')+"\">"+ B[data.list[i].id]+"</a>" +
                "<span  style=\"" + ((flag ==false) ? '' : 'display:none;')+"\">"+ name+"</span>" +
                "</td>" +
                "<td>" + data.list[i].describes + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" data-toggle=\"modal\" data-target=\"#updateSkills\" onclick='updateSkills("+data.list[i].id+")'>修改</a>" +
                "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" onclick='delSkills("+data.list[i].id+")'>删除</a>" +
                "</td>" +
                "</tr>")
        }
        flag = false;

        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        $("#pageNo").html(currentPage);
    }
}

//初始化加载数据
$(function () {
    $("#fuSkills").hide();
    $("#ziSkills").hide();
    skillAll(currentPage,parentId,flag);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        skillAll(currentPage,parentId,flag);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            skillAll(currentPage,parentId,flag);
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
            skillAll(currentPage,parentId,flag);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        skillAll(currentPage,parentId,flag);
        $("#pageNo").html(currentPage);
    })

})

/**
 * 点击进入二级
 * @param pId
 */
function selById(pId) {
    parentId = pId;
    flag = false;
    skillAll(currentPage,pId,false);
}

/**
 * 新增父类
 */
function saveSkills() {

    $.ajaxSettings.async = false;
    var name = $("#skillName").val();
    var skillName;
    var parentId;
    var describes;
    if(name == null || name == '' || name == undefined){   //添加子类
        skillName = $("#skillName2").val();
        parentId = $("#parentId").val();
        describes = $("#describes").val();
    }else{

        skillName = name;
        parentId = -1;
        describes = null;
    }

    $.getJSON("/manager/userSkill/checkName",{"skillName":skillName},function (data) {
        if(data == true){
            $.getJSON("/manager/userSkill/saveSkills",{"skillName":skillName,"parentId":parentId,"describes":describes},function (data) {
                if(data ==  true){
                    alert("新增成功！")
                    if(parentId == -1){   //父类
                        skillAll(currentPage,parentId,true)
                    }
                    window.location.reload();

                }else{
                    alert("新增失败")
                }
            })
        }else{
            alert("已存在相同的技能名称，请更换!")
        }
    })
    $.ajaxSettings.async = true;
}

/**
 * 修改渲染
 * @param id
 */
function updateSkills(id) {

    $.getJSON("/manager/userSkill/selSkillsById",{"id":id},function (data) {
        $("#skillsId,#skillNamefu,#skillNamefuNew,#parentIdfu,#skillNamezi,#describeszi").val("")
        $("#skillsId").val(data.id);
        $("#skillNamefu").val(data.skillName)
        //子类信息
        $("#parentIdfu").val(data.parentId)
        $("#skillNamezi").val(data.skillName)
        $("#describeszi").val(data.describes)
        if( B[id] == 0 ||  B[id] == '' ||  B[id] == undefined || B[id] == null){   //子类显示
            $("#fuSkills").hide();
            $("#ziSkills").show();

        }else {    // 父类显示
            $("#fuSkills").show();
            $("#ziSkills").hide();

        }
    })
}

/**
 * 保存修改结果
 */
function saveUpdSkills() {
    $.ajaxSettings.async = false;
    var id =  $("#skillsId").val();
    var skillName;
    var parentId;
    var describes;
    if( B[id] == 0 ||  B[id] == '' ||  B[id] == undefined || B[id] == null){   //修改子类
        skillName =  $("#skillNamezi").val();
        parentId =   $("#parentIdfu").val();
        describes =   $("#describeszi").val();
    }else{   //修改父类
        skillName = $("#skillNamefuNew").val();
        parentId = -1;
        describes = null;
    }

    $.getJSON("/manager/userSkill/checkName",{"skillName":skillName},function (data) {
        if(data == true){
            $.getJSON("/manager/userSkill/saveUpdSkill",{"id":id,"skillName":skillName,"parentId":parentId,"describes":describes},function (data) {
                if(data ==  true){
                    alert("修改成功！")
                    if(parentId == -1){
                        B.splice(id,1,skillName);
                    }
                    window.location.reload();
                }else{
                    alert("修改失败")
                }
            })
        }else{
            alert("已存在相同的技能名称，请更换!")
        }
    })
    $.ajaxSettings.async = true;
}

/**
 * 删除
 * @param id
 */
function delSkills(id) {
    if( B[id] == 0 ||  B[id] == '' ||  B[id] == undefined || B[id] == null){    //是子类直接删除
        $.getJSON("/manager/userSkill/delSkillsById",{"id":id},function (data) {
            if(data == true){
                alert("删除子类成功")
                window.location.reload();
            }else{
                alert("删除子类失败")
            }
        })
    }else{    //是父类先把父类数组中的清除 ，先删除包含的所有子类，再删除父类
        var r=confirm("您将要删除的是父类，父类下可能包含相关子类，您确定将其删除吗？")
        if(r == true){
            //清除父类数组中的相关元素
            B.splice(id,1)

            $.getJSON("/manager/userSkill/delSkillsByFu",{"id":id},function (data) {
                if(data == true){
                    alert("删除父类成功")
                    window.location.reload();
                }else{
                    alert("删除父类失败")
                }
            })
        }else{
            alert("您取消了删除")
        }

    }
}