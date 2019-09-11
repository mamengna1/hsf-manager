

//初始化数据
var currentPage = 1;  //当前页码
//过滤查询
function searchNewUser(currentPage) {
    $.getJSON("/manager/user/userAllNew",{"pageCurrentNo":currentPage},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            var createDate = toDate(new Date(data.list[i].createDate).toJSON())
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck' name='checkbox'/></td>" +
                "<td>" + data.list[i].id + "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserByOpenId(\""+data.list[i].openId+"\")'>"+ data.list[i].openId+"</a>" +
                "</td>" +
                "<td>" + data.list[i].nickName + "</td>" +
                "<td><img src='"+  data.list[i].headPic +"' width='50px' height='50px'/></td>" +
                "<td>" + data.list[i].city+ "</td>" +
                "<td>" + createDate+ "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserByOpenId(\""+data.list[i].userParent+"\")'>"+ data.list[i].userParent+"</a>" +
                "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn bg-olive btn-xs\" data-toggle=\"modal\" data-target=\"#updateModal\" onclick='selUserById("+data.list[i].id+")'>修改</a>" +
                "</td>" +
                "</tr>")
        }



        $("#total").html(data.totalCount);
        $("#totalPages").html(data.totalPages);
        var curr = data.totalPages == 0 ? 0 : currentPage
        $("#pageNo").html(curr);
    }
}

//初始化加载数据
$(function () {

   searchNewUser(currentPage);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
       searchNewUser(currentPage);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
           searchNewUser(currentPage);
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
           searchNewUser(currentPage);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
       searchNewUser(currentPage);
        $("#pageNo").html(currentPage);
    })

})


//删除
function delUser() {
    var delUsers = document.getElementsByClassName("userCheck");
    for (var i = 0; i < delUsers.length; i++) {
        if (delUsers[i].checked) {
            var id = $(delUsers[i]).parent().next().html();
            $.getJSON("/manager/user/delUserById", {"id": id}, function (data) {
                if(data == true){
                    alert("删除成功！")
                    window.location.reload();
                }else {
                    alert("删除失败！")
                }
            });
        }
    }
}


/**
 * 保存修改结果
 */
function saveUser() {
    var id = $("#ids").val();
    var userType = $("#userType2").val()
    var phone = $("#phone2").val();
    var totalS = $("#totalS").val();   //奖励积分
    var sources = $("#sources").val();   //积分来源
    var source = $("#source").val();   //是否发送模板
    var note = $("#note").val();   //备注
    $.getJSON("/manager/user/updateUser",{"id":id,"userType":userType,"phone":phone,"score":totalS,"sources":sources,"source":source,"note":note},function (data) {
        if(data == true){
            alert("修改成功")
            window.location.reload();
        }else{
            alert("修改失败")
        }
    })
}
function changeSou(btn) {
    $("#source").val(btn);
}