

//渲染一级
function  selLevel1() {
    $.getJSON("/manager/menu/showLevel",{"parentId":null},callback);
    function callback(data) {
        $(data).each(function () {
            $("[name=queryCategoryLevel1]").append("<option value='"+this.id+"'>"+this.menuName+"</option>")
        })
    }

    var sel=document.getElementById("query1");
    sel.onchange=function(){
        var parentId = sel.options[sel.selectedIndex].value;
        $("#queryCategoryLevel2").html("");
        $("#queryCategoryLevel3").html("");
        selLevel2(parentId);

    }
}

//渲染二级
function  selLevel2(parentId1) {
    $("#queryCategoryLevel2").html("");
    $.getJSON("/manager/menu/showLevel",{"parentId":parentId1},callback);
    function callback(data) {
        $("[name=queryCategoryLevel2]").append("<option value='-1'> 请选择</option>")
        $(data).each(function () {
            $("[name=queryCategoryLevel2]").append("<option value='"+this.id+"'>"+this.menuName+"</option>")
        })
    }
}


