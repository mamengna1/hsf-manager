//初始化数据
var currentPage = 1;  //当前页码
//过滤查询
function searchCustomer(currentPage) {
    $.getJSON("/manager/showMenu/showMenuIndex",{"pageCurrentNo":currentPage},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck'    name='checkbox'/></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" + data.list[i].menuName + "</td>" +
                "<td>" + data.list[i].parentMenuId + "</td>" +
                "<td>" + data.list[i].appMenuType.typeName + "</td>" +
                "<td>" + data.list[i].message+ "</td>" +
                "<td>" + data.list[i].key+ "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" data-toggle=\"modal\" data-target=\"#editModal\" onclick='updMenuById("+data.list[i].id+")'>修改</a>" +
                "</td>" +
                "</tr>")
        }



        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        $("#pageNo").html(currentPage);
    }
}

//初始化加载数据
$(function () {

    searchCustomer(currentPage);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        searchCustomer(currentPage);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            searchCustomer(currentPage);
            $("#pageNo").html(currentPage);
        }
    })
    //下一页
    $("#next").click(function () {
        currentPage = parseInt(currentPage)+1;
        if (parseInt(currentPage) > parseInt($("#totalPages").html())) {
            alert("已经最后一页了");
            return;
        } else {
            searchCustomer(currentPage);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        searchCustomer(currentPage);
        $("#pageNo").html(currentPage);
    })

})



//删除
function delMenu() {
    var delUsers = document.getElementsByClassName("userCheck");
    var j = 0;
    var arrayId = new Array() ;

    for (var i = 0; i <delUsers.length ; i++) {
        if (delUsers[i].checked) {
            var id = $(delUsers[i]).parent().next().html();
            arrayId[j] = id
            j++;
        }
    }
    if(j ==0 ){
        alert("您没有选择，删除失败")
    }else{
        var resMessage=confirm("您确认删除这些数据吗？");
        if(resMessage == true){
            $.getJSON("/manager/showMenu/delMenuByArray",{"ids":arrayId.toString()},function (data) {
                if(data == true){
                    alert("批量删除成功！")
                    window.location.reload();
                }else {
                    alert("批量删除失败！")
                }
            })
        }else{
            alert("您取消了删除")
        }

    }

}


//新建
function goCreateMenu() {
    location.href="/manager/menu/goCreateMenu";
}

//修改
function updMenuById(id) {
    $("#two").hide();
    $("#oneMessage").hide();
    $("#oneKey").hide();

    //数据渲染
    $.getJSON("/manager/showMenu/selAppMenuById",{"id":id},function (data) {
        $("#ids,#menuName,#menuType,#message,#key,#path").val("");
        $("#ids").val(data.id)
        $("#menuName").val(data.menuName)
        $("#menuType").val(data.menuTypeId)
        $("#message").val(data.message)
        $("#key").val(data.key)
        $("#path").val(data.message)
        if($("#key").val() !=''){
            $("#two").hide();
            $("#oneMessage").show();
            $("#oneKey").show();
            $("#path").val("")
        }else{
            $("#two").show();
            $("#oneMessage").hide();
            $("#oneKey").hide();
            $("#message").val("")

        }
    })



    //下拉改变事件
    var sel=document.getElementById("menuType");
    sel.onchange=function(){
        var parentId = sel.options[sel.selectedIndex].value;
        if (parentId == 1){
            $("#two").hide();
            $("#oneMessage").show();
            $("#oneKey").show();
            $("#message").val("")
            $("#path").val("")

        }else if (parentId == 2 || parentId == 3){
            $("#two").show();
            $("#oneMessage").hide();
            $("#oneKey").hide();
            $("#path").val("")
            $("#message").val("")

        }
    }


}

/**
 * 保存修改结果
 */
function saveAppMenu() {

    var id = $("#ids").val();
    var menuName =  $("#menuName").val();
    var menuTypeId =  $("#menuType").val();
    var message =   $("#message").val();
    var key = $("#key").val();
    var path =  $("#path").val();
    var m = message == '' ? path : message;
    var k = message == '' ? '' : key;
    $.ajaxSettings.async = false;
    var flag  = false;
    $.getJSON("/manager/menu/showMenu",{"parentId":0},function (data) {
        for (var i = 0; i < data.selKey.length; i++) {
            if(data.selKey[i] == key){
                alert("已经存在相同的key,请更换！！！")
                return;
            }
        }
        flag = true;
    })
    if (flag == true) {
        $.getJSON("/manager/showMenu/updateAppMenu", {"id": id, "menuName": menuName, "menuTypeId": menuTypeId, "message": m, "key": k
        }, function (data) {
            if (data == true) {
                alert("修改成功")
                location.href = "/manager/showMenu/goMenuIndex";
            } else {
                alert("修改失败")
            }
        })
    }
    $.ajaxSettings.async = true;

}