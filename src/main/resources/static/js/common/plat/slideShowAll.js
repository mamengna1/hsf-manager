
//初始化数据
var currentPage = 1;  //当前页码
var imgType = -1;
//过滤查询
function searchSlideShow(currentPage,imgType) {
    $.getJSON("/manager/slideshow/slideShowAll",{"pageCurrentNo":currentPage,"imgType":imgType},callback)
    //回调
    function callback(data) {
        $("#theBody").html("");
        for (var i = 0; i < data.list.length; i++) {
            var createDate = data.list[i].createDate ==null ? '' : toDate(data.list[i].createDate)
            var state = data.list[i].state == 1 ? "未审" : "已审";

            $("#theBody").append("<tr>" +
                "<td><input type=\"checkbox\" class='userCheck'    name='checkbox'/></td>" +
                "<td >" + data.list[i].id + "</td>" +
                "<td>" + data.list[i].title + "</td>" +
                "<td><img src='"+  data.list[i].url +"' width='50px' height='50px' onclick='showBig(this)'/></td>" +
                "<td>" + data.list[i].type.imgType+ "</td>" +
                "<td>" + createDate+ "</td>" +
                "<td>" + state+ "</td>" +
                "<td>" + data.list[i].priority+ "</td>" +
                "<td>" +
                "<a href='javascript:void(0)'  class=\"btn btn-xs btn-warning\"  onclick='selSlideById("+data.list[i].id+")'>修改</a>" +
                "&nbsp;&nbsp;<a href='javascript:void(0)'  class=\"btn btn-xs btn-info \"  onclick='delById(\""+data.list[i].id+"\")'>删除</a>" +
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
    $("#showBig").hide();
   searchSlideShow(currentPage,imgType);
    //首页
    $("#begin").click(function () {
        currentPage = 1;
       searchSlideShow(currentPage,imgType);
        $("#pageNo").html(currentPage);
    })
    //上一页
    $("#prev").click(function () {
        currentPage = parseInt(currentPage )-1;
        if (parseInt(currentPage) <1) {
            alert("已经是第一页了")
        } else {
           searchSlideShow(currentPage,imgType);
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
           searchSlideShow(currentPage,imgType);
            $("#pageNo").html(currentPage);
        }
    })
    //最后一页
    $("#end").click(function () {
        currentPage = parseInt($("#totalPages").html());
       searchSlideShow(currentPage,imgType);
        $("#pageNo").html(currentPage);
    })

})

function showBig(btn){
    $("#showBig img").attr("src", $(btn).attr("src"));
    $("#showBig").show();
}
function myhide(){
    $("#showBig").hide();
}

function searchSlide(btn) {
    searchSlideShow(currentPage,$(btn).val());
}
/**
 * 去到新增界面
 */
function goCreateSlide() {
    window.location.href="/manager/slideshow/goCreateSlide"
}
//批量删除
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
        var resMessage=confirm("您确认删除这些数据吗？");
        if(resMessage == true){
            $.getJSON("/manager/slideshow/delSlideById",{"ids":arrayId.toString()},function (data) {
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


//单个删除
function delById(id) {
    var res = confirm("确认要删除吗？")
    if(res == true){
        $.getJSON("/manager/slideshow/delById",{"id":id},function (data) {
            if(data == true){
                alert("删除成功")
                window.location.reload()
            }else{
                alert("删除失败")
            }
        })
    }else{
        alert("您取消了删除")
    }

}

/**
 * 去到修改界面
 * @param id
 */
function selSlideById(id) {
    location.href="/manager/slideshow/selSlideById?id="+id
}


