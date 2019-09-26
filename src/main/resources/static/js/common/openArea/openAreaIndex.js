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
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-info \" >删除</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-success\"  >添加子类</a>" +
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
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-info \" >删除</a>" +
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