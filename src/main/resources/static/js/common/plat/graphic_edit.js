

function subGraphic() {
    var flag = true;
    if($("#title").val() == undefined || $("#title").val() == null || $("#title").val()==''
        || $("#subtitle").val() == undefined || $("#subtitle").val() == null || $("#subtitle").val() == ''
    ){
        alert("必填选项不能为空")
        flag = false;
    }
    if(flag == true ){
        var ueText = UE.getEditor('editor').getContent();
        var neiRong = $("#neirong").val(ueText)
        var data = new FormData();
        data.append("title",$("#title").val())
        data.append("subtitle",  $("#subtitle").val());
        data.append("imageUrl",  $("#url").val());
        data.append("viewCount",  $("#viewCount").val());
        data.append("content", neiRong);
        data.append("graTypeId",$("#graTypeId").val())
        $.ajax({
            url: '/manager/insGraphic',
            type: 'POST',
            data: data,
            dataType: 'JSON',
            cache: false,
            processData: false,
            contentType: false,
            success:function (data) {
                if (data == true){
                    alert("新增成功")
                    location.href="/manager/goGraphicAllView"
                }else{
                    alert("新增失败")
                }
            }
        });
    }

}

$(function () {
    $("#attach").on("change", showPicGraphic );
})
function showPicGraphic() {
    var url1 = $("#url").val();
    $.ajaxSettings.async = false;
    var data = new FormData();
    data.append("attach",$('#attach')[0].files[0]);
    $.ajax({
        url: '/manager/graphicSaveImageUrl',
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


function delServerFile(path) {
    $.getJSON("/manager/slideshow/delServerFile",{"path":path},function (data) {
    })
}

//返回
$("#returnGraphic").click(function () {
    location.href="/manager/goGraphicAllView"
})