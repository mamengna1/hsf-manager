var parentId = 1;   //初始化默认安徽省


//初始化加载数据
$(function () {
    showOpenAreas(parentId);
})


//过滤查询
function showOpenAreas(parentId) {
    $("#theBody").html("");
    $.ajaxSettings.async = false;
    $.getJSON("/manager/openAreaController/showOpenAreas", {"parentId": parentId}, callback)
    //回调
    function callback(data) {
        for (var i = 0; i < data.length; i++) {
            parentId = data[i].id   // 1  2
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck' name='checkbox'/></td>" +
                "<td >" + data[i].id + "</td>" +
                "<td>" + data[i].addName + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn btn-xs btn-warning\" data-toggle=\"modal\" data-target=\"#updateLeavel2\" onclick='updateLeavel2("+data[i].id+")'>修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-danger \" onclick='delOpenArea("+data[i].id+")'>删除</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-success\"  data-toggle=\"modal\" data-target=\"#insCityModal\" onclick='insCityModal("+parentId+")' >添加子类</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-success\"  data-toggle=\"modal\" data-target=\"#insCitySame\" onclick='insCitySame("+data[i].parentId+")' >添加同级(市)</a>" +
                "</td>" +
                "</tr>")
            parentId = data[i].id   // 1  2
            ziParent(parentId)
        }
    }
    $.ajaxSettings.async = false;
}


function ziParent(parentId) {
    $.getJSON("/manager/openAreaController/showOpenAreas", {"parentId": parentId}, callback2)
    //回调
    function callback2(data) {
        for (var j = 0; j < data.length; j++) {
            var addName = "&nbsp;&nbsp;&nbsp;&nbsp;|- "+data[j].addName
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck' name='checkbox'/></td>" +
                "<td >" + data[j].id + "</td>" +
                "<td>" + addName + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn btn-xs btn-warning\" data-toggle=\"modal\" data-target=\"#updateLeavel3\" onclick='updateLeavel3("+data[j].id+")'>修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-danger \" onclick='delOpenArea("+data[j].id+")'>删除</a>" +
                "</td>" +
                "</tr>")
        }
    }
}

//根据省份查询
function searchOpenArea() {
    var parId = $("#addFu").val()
    showOpenAreas(parId)
}


//新增子类
function insCityModal(parentId) {
    $("#cityParentId").val(parentId)

}
//保存子类添加
function saveAreaName() {
    var parentId =  $("#cityParentId").val()
    var areaName = $("#areaName").val()
    $.getJSON("/manager/openAreaController/insCityModal",{"areaName":areaName,"parentId":parentId},function (data) {
        if(data == true){
            alert("新增成功")
            window.location.reload()
        }else{
            alert("新增失败")
        }
    })
}

//删除
function delOpenArea(id) {
    $.ajaxSettings.async = false;
    alert(id)
    $.getJSON("/manager/openAreaController/showOpenAreas",{"parentId":id},function (data) {
        if(data.length > 0 ){
            var resMessage = confirm("确定删除这个市以及子类吗？");
            if (resMessage == true) {
                delFu(id)
            }else{
                alert("你取消了删除")
            }

        }else{
            var res = confirm("确定删除这个市或区吗？");
            if (res == true) {
                delLeavl3(id)
            }else{
                alert("你取消了删除")
            }
        }
    })
    $.ajaxSettings.async = true;
}

function delFu(id) {
    $.getJSON("/manager/openAreaController/delCityFu",{"parentId":id},function (data) {
        if(data == true){
            alert("删除成功")
            window.location.reload()
        }else{
            alert("删除失败")
        }
    })
}

function delLeavl3(id) {
    $.getJSON("/manager/openAreaController/delCity",{"id":id},function (data) {
        if(data == true){
            alert("删除成功")
            window.location.reload()
        }else{
            alert("删除失败")
        }
    })
}

//添加同级
function insCitySame(parentId) {
    $("#sameParentId").val(parentId)
}
//保存同级添加
function saveSameName() {
    var parentId =  $("#sameParentId").val()
    var sameName = $("#sameName").val()
    $.getJSON("/manager/openAreaController/insCityModal",{"areaName":sameName,"parentId":parentId},function (data) {
        if(data == true){
            alert("新增同级成功")
            window.location.reload()
        }else{
            alert("新增同级失败")
        }
    })
}

//修改市级
function updateLeavel2(id) {
    $.getJSON("/manager/openAreaController/selById",{"id":id},function (data) {
        $("#leavel2Name,#leavel1Parent,#leavel2Id").val("")
        $("#leavel2Name").val(data.addName)
        $("#leavel1Parent").val(data.parentId)
        $("#leavel2Id").val(data.id)
    })
}

function saveLeavel2() {

    var leavel2Name =  $("#leavel2Name").val()
    var leavel1Parent =   $("#leavel1Parent").val()
    var leavel2Id =   $("#leavel2Id").val()
    $.getJSON("/manager/openAreaController/updSerAddress",{"id":leavel2Id,"addName":leavel2Name,"parentId":leavel1Parent},function (data) {
        if(data == true){
            alert("修改成功")
            window.location.reload()
        }else{
            alert("修改失败")
        }
    })
}

//渲染修改子类三级
function updateLeavel3(id) {
    $.ajaxSettings.async = false;
    $.getJSON("/manager/openAreaController/selById",{"id":id},function (data) {
        $("#leavel3Name,#leavel1Parent1,#leavel3Id,#leavel1Parent1").val("")
        $("#leavel3Name").val(data.addName)
        $("#leavel3Id").val(data.id)

        //拼接并渲染二级
      var cityParent = data.parentId

        //渲染一级
        $.getJSON("/manager/openAreaController/selById",{"id":cityParent},function (res) {
            var p = res.parentId
            $("#leavel1Parent1").val(p)
            leavels2(p)   //渲染二级
        })
        $("#leavel2Parent1").val(data.parentId)
    })
    $.ajaxSettings.async = true;
}

var sel=document.getElementById("leavel1Parent1");
sel.onchange=function(){
    $("#leavel2Parent1").html("");
    var parentId = sel.options[sel.selectedIndex].value;
    leavels2(parentId);
}

function leavels2(p) {
    $("#leavel2Parent1").html("");
    $.getJSON("/manager/userMaster/showLevelAddress",{"parentId":p},callback);
    function callback(dada) {
        $("[name=leavel2Parent1]").append("<option value='-1'> 请选择</option>")
        $(dada).each(function () {
            $("[name=leavel2Parent1]").append("<option value='"+this.id+"'>"+this.addName+"</option>")
        })
    }
}

//保存修改
function saveLeavel3() {
    var leavel2Parent1 = $("#leavel2Parent1").val()
    var leavel3Name = $("#leavel3Name").val()
    var leavel3Id = $("#leavel3Id").val()
    $.getJSON("/manager/openAreaController/updSerAddress",{"id":leavel3Id,"addName":leavel3Name,"parentId":leavel2Parent1},function (data) {
        if(data == true){
            alert("修改成功")
            window.location.reload()
        }else{
            alert("修改失败")
        }
    })
}

function insCity2() {
   var parentId = $("#addFu2").val()
   var cityName = $("#cityName").val()
    $.getJSON("/manager/openAreaController/insCityModal",{"areaName":cityName,"parentId":parentId},function (data) {
        if(data == true){
            alert("新增市级成功")
            window.location.reload()
        }else{
            alert("新增市级失败")
        }
    })
}

//去到省份管理界面
function provincesManager() {
   location.href="/manager/openAreaController/goProvincesManager";
}