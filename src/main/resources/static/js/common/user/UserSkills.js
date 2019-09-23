
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
            $("#reflushFu").hide();
            $("#returnFu").hide();
            var describes ;
            if(flag == true){
                B[data.list[i].id] = data.list[i].skillName;
                $("#reflushFu").show();
                describes = "父类"
            }else{
                $("#returnFu").show();
                describes = data.list[i].describes == null ? "暂无详情":data.list[i].describes;
            }
            var name = B[parentId]+"/"+data.list[i].skillName
            var isRecommend = data.list[i].isRecommend == 1 ? "已推" : "未推";
            var a = data.list[i].isRecommend
            $("#theBody").append("<tr>" +
               /* "<td><input type=\"checkbox\" class='userCheck'/></td>" +*/
                "<td>" + data.list[i].id + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)' onclick='selById(\""+data.list[i].id+"\")' style=\"" + ((flag ==true) ? '' : 'display:none;')+"\">"+ B[data.list[i].id]+"</a>" +
                "<span  style=\"" + ((flag ==false) ? '' : 'display:none;')+"\">"+ name+"</span>" +
                "</td>" +
                "<td><img src='"+  data.list[i].imgUrl +"' width='50px' height='50px'/></td>" +
                "<td>" + describes+ "</td>" +
                "<td>" + isRecommend+ "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn btn-xs btn-warning\" data-toggle=\"modal\" data-target=\"#updateSkills\" onclick='updateSkills("+data.list[i].id+")'>修改</a>" +
                "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-danger\" onclick='delSkills("+data.list[i].id+")'>删除</a>" +
                "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-success\"  data-toggle=\"modal\" data-target=\"#childModal\"  onclick='selSkillChild("+data.list[i].id+")'>新增子类</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-info\"  onclick='goRecommend2("+data.list[i].id+")' style=\"" + ((a ==1) ? '' : 'display:none;') + "\" >取消推荐</a>" +
                "<a href='javascript:void(0)'  class=\"btn btn-xs bg-gray\" onclick='goRecommend1("+data.list[i].id+")' style=\"" + ((a == 2) ? '' : 'display:none;') + "\" >点击推荐</a>" +
                "</td>" +
                "</tr>")
        }
        flag = false;

        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        var curr = data.totalPages == 0 ? 0 : currentPage
        $("#pageNo").html(curr);
    }
}

//初始化加载数据
$(function () {
    $("#attach").on("change", showPicGraphic );
    $("#attach2").on("change", showPicGraphic2 );

    $("#fuSkills").hide();
    $("#ziSkills").hide();
    skillAll(currentPage,parentId,flag);


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
    var url;
    var isRecommend;  //是否推荐
    if(name == null || name == '' || name == undefined){   //添加子类
        skillName = $("#skillName2").val();
        parentId = $("#parentId").val();
        describes = $("#describes").val();
        url = $("#urlZi").val()
        isRecommend = $("#isRecommend2").val()
    }else{
        skillName = name;
        parentId = -1;
        describes = null;
        url = $("#url").val()
        isRecommend = $("#isRecommend").val()
    }

    $.getJSON("/manager/userSkill/checkName",{"skillName":skillName},function (data) {
        if(data == true){
            $.getJSON("/manager/userSkill/saveSkills",{"skillName":skillName,"parentId":parentId,"describes":describes,"url":url,"isRecommend":isRecommend},function (data) {
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

//保存图标
function showPicGraphic() {
    var url1 = $("#url").val();
    $.ajaxSettings.async = false;
    var data = new FormData();
    data.append("attach",$('#attach')[0].files[0]);
    $.ajax({
        url: '/manager/userSkill/userSkillSaveImageUrl',
        type: 'POST',
        data: data,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false,
        success:function (data) {
            $("#pic").attr("src", data.message);
            $("#url").val("")
            $("#url").val(data.message)
        }
    });
    var url2 = $("#url").val();
    if(url1 !=null && url1 !=undefined && url1 !='' && url1 != url2){
        delServerFile(url1) ;
    }
    $.ajaxSettings.async = true;
}

function showPicGraphic2() {
    var url1 = $("#urlZi").val();
    $.ajaxSettings.async = false;
    var data = new FormData();
    data.append("attach",$('#attach2')[0].files[0]);
    $.ajax({
        url: '/manager/userSkill/userSkillSaveImageUrl',
        type: 'POST',
        data: data,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false,
        success:function (data) {
            $("#picZi").attr("src", data.message);
            $("#urlZi").val("")
            $("#urlZi").val(data.message)
        }
    });
    var url2 = $("#urlZi").val();
    if(url1 !=null && url1 !=undefined && url1 !='' && url1 != url2){
        delServerFile(url1) ;
    }
    $.ajaxSettings.async = true;
}
//删除图标
function delServerFile(path) {
    $.getJSON("/manager/slideshow/delServerFile",{"path":path},function (data) {
    })
}
//是否推荐
function changeSou(btn) {
    $("#isRecommend").val(btn);
}
function changeSou2(btn) {
    $("#isRecommend2").val(btn);
}
function changeSou3(btn) {
    $("#isRecommend3").val(btn);
}
function changeSou4(btn) {
    $("#isRecommend4").val(btn);
}
/**
 * 修改渲染
 * @param id
 */
function updateSkills(id) {
    $.ajaxSettings.async = false;
    $.getJSON("/manager/userSkill/selSkillsById",{"id":id},function (data) {
        $("#skillsId,#skillNamefu,#skillNamefu2,#parentIdfu,#skillNamezi,#describeszi,#logoPicPath3,#logoPicPath4,#isRecommend3,#isRecommend4").val("")
        //$("#fuTui,#ziTui,#fuNotTui,#ziNotTui").attr("checked", false);
        $("#skillsId").val(data.id);
        $("#skillNamefu").val(data.skillName)
        $("#skillNamefu2").val(data.skillName)
        $("#logoPicPath3").val(data.imgUrl)
        $("#isRecommend3").val(data.isRecommend )
        if(data.isRecommend == 1){
            $("#fuTui").attr("checked", true);
            $("#ziTui").attr("checked", true);
           /* $("#fuNotTui").attr("checked", false);
            $("#ziNotTui").attr("checked", false);*/
        }else{
            $("#fuNotTui").attr("checked", true);
            $("#ziNotTui").attr("checked", true);
           /* $("#fuTui").attr("checked", false);
            $("#ziTui").attr("checked", false);*/
        }

        //子类信息
        $("#parentIdfu").val(data.parentId)
        $("#skillNamezi").val(data.skillName)
        $("#describeszi").val(data.describes)
        $("#logoPicPath4").val(data.imgUrl)
        $("#isRecommend4").val(data.isRecommend )
        if( B[id] == 0 ||  B[id] == '' ||  B[id] == undefined || B[id] == null){   //子类显示
            $("#fuSkills").hide();
            $("#ziSkills").show();

        }else {    // 父类显示
            $("#fuSkills").show();
            $("#ziSkills").hide();

        }

    })
    showImg();
    $.ajaxSettings.async = true;
}

/**
 * 保存子类修改结果
 */
function saveUpdSkills() {
    $.ajaxSettings.async = false;
    var id =  $("#skillsId").val();
    var skillName;
    var parentId;
    var describes;
    var url;
    var isRecommend;  //是否推荐
    if( B[id] == 0 ||  B[id] == '' ||  B[id] == undefined || B[id] == null){   //修改子类
        skillName =  $("#skillNamezi").val();
        parentId =   $("#parentIdfu").val();
        describes =   $("#describeszi").val();
        url = $("#urlHidden4").val()
       isRecommend = $("#isRecommend4").val()
    }else{   //修改父类
        skillName = $("#skillNamefu").val();
        parentId = -1;
        describes = null;
        url = $("#urlHidden3").val()
        isRecommend = $("#isRecommend3").val()
    }
    var fuName1 = $("#skillNamefu2").val()
    if(fuName1 == skillName){
        $.getJSON("/manager/userSkill/saveUpdSkill",{"id":id,"skillName":skillName,"parentId":parentId,"describes":describes,"url":url,"isRecommend":isRecommend},function (data) {
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
        $.getJSON("/manager/userSkill/checkName",{"skillName":skillName},function (data) {
            if(data == true){
                $.getJSON("/manager/userSkill/saveUpdSkill",{"id":id,"skillName":skillName,"parentId":parentId,"describes":describes,"url":url,"isRecommend":isRecommend},function (data) {
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
    }

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

function selSkillChild(id) {
    $.getJSON("/manager/userSkill/selSkillsById",{"id":id},function (data) {
        $("#parentId").val("");
        if(data.parentId == null || data.parentId == undefined || data.parentId==-1){
            $("#parentId").val(data.id);
        }else{
            $("#parentId").val(data.parentId);
        }

    })
}

//点击推荐
function goRecommend1(id) {
    getRec(id,1,"推荐成功");
}
//点击取消推荐
function goRecommend2(id) {
    getRec(id,2,"您已取消了推荐此技能");
}
function getRec(id,isRecommend,message) {
    $.getJSON("/manager/userSkill/goRecommend",{"id":id,"isRecommend":isRecommend},function (data) {
        if(data == true){
            alert(message)
            window.location.reload();
        }else{
            alert("服务故障，改变推荐状态失败")
            window.location.reload();
        }
    })
}