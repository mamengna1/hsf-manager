var C ;
$(function () {
    selLevel1();
})
//渲染一级
function  selLevel1() {
    $.getJSON("/manager/userSkill/showLevel",{"parentId":null},callback);
    function callback(data) {
        $(data).each(function () {
            $("[name=skills]").append("<option value='"+this.id+"'>"+this.skillName+"</option>")
        })
    }

    var sel=document.getElementById("skills");
    sel.onchange=function(){
        $("#skills2").html("");
        var parentId = sel.options[sel.selectedIndex].value;
        selLevel2(parentId);
    }
}

//渲染二级
function  selLevel2(parentId1) {
    C = new Array()
    $("#skills2").html("");
    $.getJSON("/manager/userSkill/showLevel",{"parentId":parentId1},callback);
    function callback(data) {
        $("[name=skills2]").append("<option value='-1'> 请选择</option>")
        $(data).each(function () {
            C[this.id] = this.id;
            $("[name=skills2]").append("<option value='"+this.id+"'>"+this.skillName+"</option>")
        })
    }
}

function trimSpace(C){
    for(var i = 0 ;i<C.length;i++)
    {
        if(C[i] == " " || C[i] == null || typeof(C[i]) == "undefined")
        {
            C.splice(i,1);
            i= i-1;
        }
    }
    return C;
}