var currentPage = 1;  //当前页码
var isDelete = 0;
$(function () {
    $("#bigImg").hide();
    search(currentPage, 0);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
        search(currentPage, isDelete);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage) - 1;
        if (parseInt(currentPage) < 1) {
            alert("已经是第一页了")
        } else {
            search(currentPage, isDelete);
            $("#pageNo").html(currentPage);
        }
    })
    //下一页
    $("#next").click(function () {
        currentPage = parseInt(currentPage) + 1;
        if (parseInt(currentPage) > parseInt($("#totalPages").html())) {
            alert("已经最后一页了");
            return;
        } else {
            search(currentPage, isDelete);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
        search(currentPage, isDelete);
        $("#pageNo").html(currentPage);
    })
})
function getUserId(userParent) {
    var userParentId
    $.getJSON("/manager/user/selUserByOpenId",{"openId":userParent},function (data) {
        userParentId = data.id;
    })
    return userParentId;
}
function search(currentPage, isDelete) {
    $.ajaxSettings.async = false;
    $.getJSON("/manager/selAll", {"pageCurrentNo": currentPage,"isDelete":isDelete}, function (res) {

        $("#delCause").hide();
        $("#pilaing").show();
        if (isDelete == 1){
            $("#delCause").show();
            $("#pilaing").hide();
        }
        $("#theBody").html("");
        for (var i = 0; i < res.list.length; i++) {
            var userId = getUserId(res.list[i].openId);

            $("#theBody").append("<tr>\n" +
                "<input type='hidden' name='openId' value='" + res.list[i].openId + "'/>" +
                "<td class=\"\" style=\"padding-right:0px\">\n" +
                "     <input id=\"selall\" type=\"checkbox\" class=\"icheckbox_square-blue\" name='checkbox'>\n" +
                "</td>" +
                "<td>" + res.list[i].id + "</td>\n" +
                "<td>" +
                "<a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal2\"  onclick='selUserByOpenId(\""+res.list[i].openId+"\")'>"+ userId+"</a>" +
                "</td>" +
                "<td>" + res.list[i].name + "</td>\n" +
                "<td>" + res.list[i].content + "</td>\n" +
                "<td>" + res.list[i].lookTotal + "</td>\n" +
                "<td>" + toDate(res.list[i].createDate) + "</td>\n" +
                "<td style='" + ((isDelete == 0) ? 'display:none;' : '') + "'>" + res.list[i].delCause + "</td>\n" +
                "<td><a href='javascript:void(0)'  data-toggle=\"modal\" data-target=\"#editModal\"  onclick='selDetail(" + res.list[i].id + ")'>详情</a></td>" +
                "</tr>")
        }
        $.ajaxSettings.async = true;
        $("#total").html(res.totalCount);
        $("#totalPages").html(res.totalPages);
        $("#pageNo").html(currentPage);
    })
}

function selDetail(id) {
    $("#bigImg").hide();
    $.getJSON("/manager/selByOpenId", {"id": id}, function (res) {
        $("#id").val(res.information.id);
        $("#name").html(res.user.name);
        $("#content").html(res.information.content);
        var images = res.information.images.split(',');
        $("#images").html("");
        for (var i = 0; i < images.length; i++) {
            $("#images").append("<img width='50px' height='50px' src='"+images[i]+"' onclick='showBigImg(this)'/>");
        }
    })
}

function showBigImg(btn){
    $("#bigImg").show();
    var url = $(btn).attr("src");
    $("#big").attr("src", url);
}
function hideImg() {
    $("#bigImg").hide();
}

function del() {
    var delCause = prompt("输入删除原因，点击确定");
    if (delCause){
        $.getJSON("/manager/delUserInfo", {"id" : $("#id").val(), "delCause":delCause, "isDelete":1}, function (res) {
            if (res){
                alert("删除成功")
                search(1,isDelete);
            } else {
                alert("删除失败");
            }
        });
    } else {
        alert("取消");
    }
}

function delList(btn) {
    isDelete = $(btn).attr("delId");
    if (isDelete == 1){
        $("#del").hide();
        $(btn).attr("delId",0);
        $(btn).html("返回");
        $(btn).attr("title", "已删除列表");
    } else {
        $("#del").show();
        $(btn).attr("delId",1);
        $(btn).html("已删除列表");
        $(btn).attr("title", "已删除列表");
    }
    search(1, isDelete);
}

/**
 * 批量删除
 */
function batchDel() {
    var ids = document.getElementsByClassName("icheckbox_square-blue");
    var j = 0;
    var arrayId = new Array() ;
    for (var i = 0; i <ids.length ; i++) {
        if (ids[i].checked) {
            var id = $(ids[i]).parent().next().html();
            arrayId[j] = id
            j++;
        }
    }
    if(j !=0){
        var delCause = prompt("输入删除原因，点击确定");
        if (delCause){
            $.getJSON("/manager/userInformation/batchDel",{"ids":arrayId.toString(),"delCause":delCause},function (data) {
                if(data == true){
                    alert("批量删除成功！")
                    window.location.reload();
                }else {
                    alert("批量删除失败！")
                }
            })
        }
    }else{
        alert("您没有选择,删除失败")
    }




}