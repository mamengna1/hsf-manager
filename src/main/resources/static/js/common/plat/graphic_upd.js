
$(function () {
    $("#attach").on("change", showPicUpd );
    //LOGO图片---------------------
    var logoPicPath = $("#logoPicPath").val();
    var id = $("#ids").val();
    if(logoPicPath == null || logoPicPath == "" ){
        $("#uploadfile").show();
    }else{
        $("#logoFile").append("<p><img src=\""+logoPicPath+"?m="+Math.random()+"\" width=\"100px;\"/> &nbsp;&nbsp;"+
            "<a href=\"javascript:void(0);\" onclick='delfile(\""+id+"\")'>删除</a></p>");

    }

    $("#myForm").submit(function () {
        var ueText = UE.getEditor('editor').getContent();
        $("#neirong").val(ueText)
        return true;
    })

})

//返回
$("#returnGraphics").click(function () {
    location.href="/manager/goGraphicAllView"
})



/**
 * 更换图片
 * @param id
 */
function delfile(id) {
    if(confirm("确认要删除吗？")){
        alert("删除成功！");
        $("#uploadfile").show();
        $("#logoFile").html('');
    }
}

/**
 * 保存修改
 */
function updateGraphic() {
    var flag = true;
    if($("#title").val() == undefined || $("#title").val() == null || $("#title").val()==''
        || $("#subtitle").val() == undefined || $("#subtitle").val() == null || $("#subtitle").val() == ''
    ){
        alert("必填选项不能为空")
        flag = false;
    }
    if(flag == true ){
        var ueText = UE.getEditor('editor').getContent();
        var neiRong =  $("#neirong").val(ueText)
        var data = new FormData();
        data.append("id",$("#ids").val())
        data.append("title",$("#title").val())
        data.append("subtitle",  $("#subtitle").val());
        data.append("imageUrl",  $("#logoPicPath").val());
        data.append("viewCount",  $("#viewCount").val());
        data.append("content", neiRong);
        data.append("attach",$('#attach')[0].files[0]);
        data.append("urlHidden",  $("#urlHidden").val());
        $.ajax({
            url: '/manager/updateGraphic',
            type: 'POST',
            data: data,
            dataType: 'JSON',
            cache: false,
            processData: false,
            contentType: false,
            success:function (data) {
                if (data == true){
                    alert("修改成功")
                    location.href="/manager/goGraphicAllView"
                }else{
                    alert("修改失败")
                }
            }
        });
    }

}


/**
 * 修改时上传临时图片到服务器
 */
function showPicUpd() {
    var usrHidden1 = $("#urlHidden").val();
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
            $("#urlHidden").val("")
            $("#urlHidden").val(data.message)
        }
    });

    var usrHidden2 = $("#urlHidden").val();
    if(usrHidden1 !=null && usrHidden1 !=undefined && usrHidden1 !='' && usrHidden1 != usrHidden2){
        delServerFile(usrHidden1) ;
    }
    $.ajaxSettings.async = true;
}
//删除服务器多余的图片
function delServerFile(path) {
    $.getJSON("/manager/slideshow/delServerFile",{"path":path},function (data) {
    })
}