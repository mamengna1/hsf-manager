
$(function () {
    selLevel1();
})
//渲染一级
function  selLevel1() {
    $.getJSON("/manager/userMaster/showLevelAddress",{"parentId":null},callback);
    function callback(data) {
        $(data).each(function () {
            $("[name=workProvince1]").append("<option value='"+this.id+"'>"+this.addName+"</option>")
        })
    }
    var sel=document.getElementById("workProvince1");
    sel.onchange=function(){
        $("#workCity1").html("");
        var parentId = sel.options[sel.selectedIndex].value;
        selLevel2(parentId);
    }
}

//渲染二级
function  selLevel2(parentId1) {
    $("#workCity1").html("");
    $.getJSON("/manager/userMaster/showLevelAddress",{"parentId":parentId1},callback);
    function callback(data) {
        $("[name=workCity1]").append("<option value='-1'> 请选择</option>")
        $(data).each(function () {
            $("[name=workCity1]").append("<option value='"+this.id+"'>"+this.addName+"</option>")
        })
    }
}
