//保存新增省份
function saveProvinceName() {
    var provinceName = $("#provinceName").val();
    $.getJSON("/manager/openAreaController/saveProvinceName",{"provinceName":provinceName},function (data) {
        if(data == true){
            alert("新增成功")
            window.location.reload()
        }else{
            alert("新增失败")
        }
    })

}

function returnManager() {
    location.href="/manager/openAreaController/goOpenAreaIndex"
}

function selUpd(id) {
    $.getJSON("/manager/openAreaController/selById",{"id":id},function (data) {
        $("#leavel1Id,#leavel1Name").val("")
        $("#leavel1Name").val(data.addName)
        $("#leavel1Id").val(data.id)
    })
}

function saveLeavel1() {
   var leavel1Name = $("#leavel1Name").val()
   var leavel1Id=   $("#leavel1Id").val()
    $.getJSON("/manager/openAreaController/updSerAddress",{"id":leavel1Id,"addName":leavel1Name,"parentId":null},function (data) {
        if(data == true){
            alert("修改成功")
            window.location.reload()
        }else{
            alert("修改失败")
        }
    })
}

function delProvince(id) {
    var resMessage = confirm("删除省份后将会把所属的市区同时删除，确认删除吗？");
    if (resMessage == true) {
        $.getJSON("/manager/openAreaController/delCityLeavl1",{"id":id},function (data) {
            if(data == true){
                alert("删除省份成功")
                window.location.reload()
            }else{
                alert("删除省份失败")
            }
        })
    }else{
        alert("你取消了删除")
    }
}