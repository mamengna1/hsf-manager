
function showImg() {
    $("#attach3").on("change", showPicUpd3 );
    $("#attach4").on("change", showPicUpd4 );
    //LOGO图片---------------------
    var logoPicPath = $("#logoPicPath3").val();
    var id = $("#skillsId").val();
    if(logoPicPath == null || logoPicPath == "" ){
        $("#uploadfile3").show();
    }else{
        $("#logoFile3").html("")
        $("#logoFile3").append("<p><img src=\""+logoPicPath+"?m="+Math.random()+"\" width=\"100px;\"/> &nbsp;&nbsp;"+
            "<a href=\"javascript:void(0);\" onclick='delfile(\""+id+"\")'>删除</a></p>");

    }
    //LOGO图片---------------------
    var logoPicPath4 = $("#logoPicPath4").val();
    if(logoPicPath4 == null || logoPicPath4 == "" ){
        $("#uploadfile4").show();
    }else{
        $("#logoFile4").html("")
        $("#logoFile4").append("<p><img src=\""+logoPicPath4+"?m="+Math.random()+"\" width=\"100px;\"/> &nbsp;&nbsp;"+
            "<a href=\"javascript:void(0);\" onclick='delfile4(\""+id+"\")'>删除</a></p>");

    }

}

/**
 * 更换图片
 * @param id
 */
function delfile(id) {
    if(confirm("确认要删除吗？")){
        alert("删除成功！");
        $("#uploadfile3").show();
        $("#logoFile3").html('');
    }
}
function delfile4(id) {
    if(confirm("确认要删除吗？")){
        alert("删除成功！");
        $("#uploadfile4").show();
        $("#logoFile4").html('');
    }
}

/**
 * 修改时上传临时图片到服务器
 */
function showPicUpd3() {
    var usrHidden1 = $("#urlHidden3").val();
    $.ajaxSettings.async = false;
    var data = new FormData();
    data.append("attach",$('#attach3')[0].files[0]);

    $.ajax({
        url: '/manager/userSkill/userSkillSaveImageUrl',
        type: 'POST',
        data: data,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false,
        success:function (data) {
            $("#pic3").attr("src", data.message);
            $("#urlHidden3").val("")
            $("#urlHidden3").val(data.message)
        }
    });

    var usrHidden2 = $("#urlHidden3").val();
    if(usrHidden1 !=null && usrHidden1 !=undefined && usrHidden1 !='' && usrHidden1 != usrHidden2){
        delServerFile3(usrHidden1) ;
    }
    $.ajaxSettings.async = true;
}
function showPicUpd4() {
    var usrHidden1 = $("#urlHidden4").val();
    $.ajaxSettings.async = false;
    var data = new FormData();
    data.append("attach",$('#attach4')[0].files[0]);

    $.ajax({
        url: '/manager/userSkill/userSkillSaveImageUrl',
        type: 'POST',
        data: data,
        dataType: 'JSON',
        cache: false,
        processData: false,
        contentType: false,
        success:function (data) {
            $("#pic4").attr("src", data.message);
            $("#urlHidden4").val("")
            $("#urlHidden4").val(data.message)
        }
    });

    var usrHidden2 = $("#urlHidden4").val();
    if(usrHidden1 !=null && usrHidden1 !=undefined && usrHidden1 !='' && usrHidden1 != usrHidden2){
        delServerFile3(usrHidden1) ;
    }
    $.ajaxSettings.async = true;
}

//删除服务器多余的图片
function delServerFile3(path) {
    $.getJSON("/manager/slideshow/delServerFile",{"path":path},function (data) {
    })
}