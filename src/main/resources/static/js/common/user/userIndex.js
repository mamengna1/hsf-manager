

//初始化数据
var currentPage = 1;  //当前页码
var isSub ;
var detailId ;
var userName = $("#userName").val();
//过滤查询
function searchCustomer(currentPage,isSub,detailId,userName) {
    $.getJSON("/manager/user/userAll",{"pageCurrentNo":currentPage,"isSub":isSub,"detailId":detailId,"userName":userName},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            var createDate = toDate(new Date(data.list[i].createDate).toJSON())
            var userParent = data.list[i].userParent == null ? '' : data.list[i].userParent;
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
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserByOpenId(\""+data.list[i].userParent+"\")'>"+ userParent+"</a>" +
                "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn btn-xs btn-warning\" data-toggle=\"modal\" data-target=\"#updateModal\" onclick='selUserById("+data.list[i].id+")'>修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-info \"  onclick='goScore(\""+data.list[i].openId+"\")'>积分来源</a>" +
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
    isSub = $("#isSub").val();
    detailId = $("#detailId").val();
    searchCustomer(currentPage,isSub,detailId,userName);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        searchCustomer(currentPage,isSub,detailId,userName);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            searchCustomer(currentPage,isSub,detailId,userName);
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
            searchCustomer(currentPage,isSub,detailId,userName);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        searchCustomer(currentPage,isSub,detailId,userName);
        $("#pageNo").html(currentPage);
    })

})


//删除
function delUser() {
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
        $.getJSON("/manager/user/delUserById",{"ids":arrayId.toString()},function (data) {
            if(data == true){
                alert("批量删除成功！")
                window.location.reload();
            }else {
                alert("批量删除失败！")
            }
        })
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

/**
 * 查看级分来源
 * @param openIds
 */
function goScore(openIds) {
    location.href="/manager/userScore/goUserScoreAll?openId="+openIds;
}

/**
 * 根据昵称进行模糊查询
 */
function searchNames() {
    var name = $("#userName").val();
    searchCustomer(currentPage,isSub,detailId,name)
}



function changeSou(btn) {
    $("#source").val(btn);
}