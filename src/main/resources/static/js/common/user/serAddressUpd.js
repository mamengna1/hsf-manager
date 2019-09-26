
$(function () {
    selLevel1();

})
//渲染一级
function  selLevel1() {
    sel=document.getElementById("workProvince");
    sel.onchange=function(){
        $("#workCity").html("");
       $("#bbb").html("");
       $("#workAddress1").val("")
        $("#workAddress").val("")
        var parentId = sel.options[sel.selectedIndex].value;
        selLevel2(parentId);
    }
}

//渲染二级
function  selLevel2(parentId1) {
    $("#workCity").html("");
    $.getJSON("/manager/userMaster/showLevelAddress",{"parentId":parentId1},callback);
    function callback(data) {
        $("[name=workCity]").append("<option value='-1'> 请选择</option>")
        $(data).each(function () {
            $("[name=workCity]").append("<option value='"+this.id+"'>"+this.addName+"</option>")
        })
    }
}

var sel=document.getElementById("workCity");
sel.onchange=function(){
    var parentId = sel.options[sel.selectedIndex].value;
    $("#bbb").html("");
    selLevel3(parentId);
}

function selLevel3(parentId2) {
   /* if(parentId2 == null || parentId2 == undefined || parentId2 ==''){
        parentId2 = $("#workCity").val()
    }*/
    $("#bbb").html("");
    $.getJSON("/manager/userMaster/showLevelAddress",{"parentId":parentId2},callback);
    function callback(data) {
        $(data).each(function () {
            $("[name=bbb]").append("<td><label style=\"margin-right: 20px\"><input type=\"checkbox\" name=\"checkbox\" class=\"quanXian\"  value='"+this.id+"' /><span>"+this.addName+"</span></label></td>")
        })
    }

}
