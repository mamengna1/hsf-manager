var imgType = 1;
$(function () {
    search(imgType);
})

function search(imgType) {
    $.getJSON("/manager/slideshow/selAll",{"imgType":imgType},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.length; i++) {
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck' name='checkbox' /></td>" +
                "<td>" + data[i].id + "</td>" +
                "<td>" +
                "<img src='"+ data[i].url +"'/>" +
                "</td>" +
                "<td>" + data[i].priority + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#updOrderModal\" class=\"btn btn-xs btn-warning\">修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-danger\">删除</a>" +
                "</td>" +
                "</tr>")
        }
    }
}
function selType(type) {
    imgType = type;
}

function sub() {
    var data = new FormData();
    data.append("imgType",  $("#imgType").val());
    data.append("priority",  $("#priority").val());
    data.append("file",$('#file')[0].files[0]);
    $.ajax({
        url: '/manager/slideshow/insHomePageImg',
        type: 'POST',
        data: data,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false,
        success:function (data) {
            if (data > 0){
                search(imgType);
            }
        }
    });
}