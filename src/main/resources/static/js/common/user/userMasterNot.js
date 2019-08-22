

//初始化数据
var currentPage = 1;  //当前页码
var names = $("#names").val();
var statusId = 2;  //已审核信息
//过滤查询
function searchCustomer(currentPage,names,statusId) {
    $.getJSON("/userMaster/userAll",{"pageCurrentNo":currentPage,"names":names,"statusId":statusId},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            var a = data.list[i].status;
            var message = data.list[i].statusMessage == null ? '' : data.list[i].statusMessage;
            var workArea = showProvince(data.list[i].workProvince,data.list[i].workCity,data.list[i].workArea);
            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck'/></td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selUserDetail(\""+data.list[i].id+"\")'>"+ data.list[i].id+"</a>" +
                "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#detailModal\"  onclick='selUserDetailById(\""+data.list[i].id+"\")'>"+ data.list[i].name+"</a>" +
                "</td>" +
                "<td>" + data.list[i].card + "</td>" +
                "<td><img src='"+  data.list[i].cardOne +"' width='50px' height='50px'/></td>" +
                "<td><img src='"+  data.list[i].cardTwo +"' width='50px' height='50px'/></td>" +
                "<td>" + workArea+"</td>" +
                "<td>" +message+"</td>" +
                "<td>" +
                " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span   class=\"btn bg-olive btn-xs\" style=\"" + ((a == 2 ) ? '' : 'display:none;')+"\">审核未通过</span>"+
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

    searchCustomer(currentPage,names,statusId);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        searchCustomer(currentPage,names,statusId);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
            searchCustomer(currentPage,names,statusId);
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
            searchCustomer(currentPage,names,statusId);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        searchCustomer(currentPage,names,statusId);
        $("#pageNo").html(currentPage);
    })

})


//删除
function delUser() {
    var delUsers = document.getElementsByClassName("userCheck");
    for (var i = 0; i < delUsers.length; i++) {
        if (delUsers[i].checked) {
            var id = $(delUsers[i]).parent().next().html();
            $.getJSON("/user/delUserById", {"id": id}, function (data) {
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
    var balanceMoney = $("#balanceMoney2").val()
    var totalScore = $("#totalScore2").val();
    var balanceScore = $("#balanceScore2").val();
    $.getJSON("/user/updateUser",{"id":id,"userType":userType,"balanceMoney":balanceMoney,"totalScore":totalScore,"balanceScore":balanceScore},function (data) {
        if(data == true){
            alert("修改成功")
            location.href="/user/goUserIndex";
        }else{
            alert("修改失败")
        }
    })
}


function searchNames() {
    var na = $("#names").val();
    searchCustomer(1,na,statusId)
}
