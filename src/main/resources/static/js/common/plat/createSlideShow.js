
function changeSou(btn) {
    $("#state").val(btn);
}

function sub() {
    var data = new FormData();
    data.append("title",$("#title").val())
    data.append("imgType",  $("#imgType").val());
    data.append("priority",  $("#priority").val());
    data.append("linkUrl",  $("#linkUrl").val());
    data.append("state",  $("#state").val());
    data.append("url",  $("#url").val());
    $.ajax({
        url: '/manager/slideshow/insSlideShow',
        type: 'POST',
        data: data,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false,
        success:function (data) {
            if (data == true){
                alert("新增成功")
                location.href="/manager/slideshow/goSlide"
            }else{
                alert("新增失败")
            }
        }
    });
}

$(function () {
    $("#attach").on("change", showPic );
})
function showPic() {
    var data = new FormData();
    data.append("attach",$('#attach')[0].files[0]);
    $.ajax({
        url: '/manager/slideshow/saveImageUrl',
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
}