
$(function () {

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
function updateSlide() {
    var flag = true;
    if($("#title").val() == undefined || $("#title").val() == null || $("#title").val()==''
        || $("#linkUrl").val() == undefined || $("#linkUrl").val() == null || $("#linkUrl").val() == ''
    ){
        alert("必填选项不能为空")
        flag = false;
    }
    if(flag == true ){
        var data = new FormData();
        data.append("id",$("#id").val())
        data.append("title",$("#title").val())
        data.append("imgType",  $("#imgType").val());
        data.append("priority",  $("#priority").val());
        data.append("url",  $("#logoPicPath").val());
        data.append("linkUrl",  $("#linkUrl").val());
        data.append("state",  $("#state").val());
        data.append("attach",$('#attach')[0].files[0]);
        data.append("urlHidden",  $("#urlHidden").val());
        $.ajax({
            url: '/manager/slideshow/updateSlide',
            type: 'POST',
            data: data,
            dataType: 'JSON',
            cache: false,
            processData: false,
            contentType: false,
            success:function (data) {
                if (data == true){
                    alert("修改成功")
                    location.href="/manager/slideshow/goSlide"
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
        url: '/manager/slideshow/updImageUrl',
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
            //delServerFile(data.message)  此时传入的应该是上次的图片路径不应该放在这
        }
    });

    var usrHidden2 = $("#urlHidden").val();
    if(usrHidden1 !=null && usrHidden1 !=undefined && usrHidden1 !='' && usrHidden1 != usrHidden2){
        delServerFile(usrHidden1) ;
    }
    $.ajaxSettings.async = true;
}

function delServerFile(path) {
    $.getJSON("/manager/slideshow/delServerFile",{"path":path},function (data) {
    })
}