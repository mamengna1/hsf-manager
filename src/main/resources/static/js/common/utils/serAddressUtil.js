
//得到单个名字
function showGetName(id) {
    var name ;
    $.getJSON("/manager/userMaster/getAddName",{"id":id},function (data) {
            name = data.message;
    })
    return name;
}

//得到数组名字
function showGetNameList(id) {
    var name ;
    $.getJSON("/manager/userMaster/getAddNameList",{"ids":id},function (data) {
        name = data.message;
    })
    return name;
}

//得到最终组合名字
function getWorkName(workProvince,workCity,workArea) {
    var workProvince = showGetName(workProvince)
    var workCity = showGetName(workCity)
    var workArea = showGetNameList(workArea)

    return workProvince +"/"+ workCity+"/("+ workArea+")"
}

