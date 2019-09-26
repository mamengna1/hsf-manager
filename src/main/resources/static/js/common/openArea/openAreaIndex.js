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
                "<a href='javascript:void(0)'  class=\"btn btn-xs btn-warning\"  >修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-info \" onclick='delOpenArea("+data[i].id+")'>删除</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-success\"  data-toggle=\"modal\" data-target=\"#insCityModal\" onclick='insCityModal("+parentId+")' >添加子类</a>" +
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
                "<a href='javascript:void(0)'  class=\"btn btn-xs btn-warning\"  >修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-info \" onclick='delOpenArea("+data[j].id+")'>删除</a>" +
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

//保存新增省份
function saveProvinceName() {
    var provinceName = $("#provinceName").val();
    $.getJSON("/manager/openAreaController/saveProvinceName",{"provinceName":provinceName},function (data) {
        if(data == true){
            alert("新增成功")
            window.location.reload()
        }else{
            alert("新增失败")
        }
    })

}

//新增子类
function insCityModal(parentId) {
    $("#cityParentId").val(parentId)

}

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
            alert("删除的父类包含子类")
            delFu(id)
        }else{
            alert("单独删除子类")
            delLeavl3(id)
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