
$(function () {
    selLevel1();
})
//渲染一级
function  selLevel1() {
    sel=document.getElementById("workProvince");
    sel.onchange=function(){
        $("#workCity").html("");
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

    var sel=document.getElementById("workCity");
    sel.onchange=function(){
        alert(1111)
        var parentId = sel.options[sel.selectedIndex].value;
        $("#areas").html("");
        $("#areas").val("");
        selLevel3(parentId);

    }
}
 //  <label style="margin-right: 20px"><input type="checkbox" name="checkbox" class="quanXian"  th:value="${workArea1.id}"/><span>[[${workArea1.addName}]]</span></label>
function selLevel3(parentId1) {
    alert(2222)
    $("#areas").html("");
    $("#areas").val("");
    $.getJSON("/manager/userMaster/showLevelAddress",{"parentId":parentId1},callback);
    function callback(data) {
        alert(33333)
        $(data).each(function () {
           // $("[name=areas]").append("<option value='"+this.id+"'>"+this.addName+"</option>")
            $("[name=areas]").append("<label style=\"margin-right: 20px\"><input type=\"checkbox\" name=\"checkbox\" class=\"quanXian\"  value='"+this.id+"' />"+this.addName+"</label>")
        })
    }
}