
$(function () {
    selLevel1();
})
//渲染一级
function  selLevel1() {

    var sel=document.getElementById("placeProvince");
    sel.onchange=function(){
        $("#placeCity").html("");
        $("#placeArea").html("");
        var parentId = sel.options[sel.selectedIndex].value;
        selLevel2(parentId);
    }
}

//渲染二级
function  selLevel2(parentId1) {
    $("#queryCategoryLevel2").html("");
    $.getJSON("/manager/userMaster/showLevelAddress",{"parentId":parentId1},callback);
    function callback(data) {
        $("[name=serviceCity]").append("<option value='-1'> 选择市</option>")
        $(data).each(function () {
            $("[name=serviceCity]").append("<option value='"+this.id+"'>"+this.addName+"</option>")
        })
    }

}
var selll=document.getElementById("placeCity");
selll.onchange=function(){
    var parentId = selll.options[selll.selectedIndex].value;
    $("#placeArea").html("");
    selLevel3(parentId);

}
//渲染三级
function  selLevel3(parentId2) {
    $("#placeArea").html("");
    $.getJSON("/manager/userMaster/showLevelAddress",{"parentId":parentId2},callback);
    function callback(data) {
        $("[name=serviceArea]").append("<option value='-1'>选择区县</option>")
        $(data).each(function () {
            $("[name=serviceArea]").append("<option value='"+this.id+"'>"+this.addName+"</option>")
        })
    }
}

