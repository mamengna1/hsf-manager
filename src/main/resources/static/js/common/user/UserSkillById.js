

/**
 * 根据id 得到skillName
 * @param id
 * @returns {*}
 */
function userSkillById(id) {
    var name ;
    $.getJSON("/manager/userSkill/getUserSkill",{"id":id},function (data) {
        name =   data.skillName
    })
    return name;

}




/**
 * 根据id 得到工作年限
 * @param id
 * @returns {*}
 */
function userWorkYearById(id) {
    var name ;
    $.getJSON("/manager/userMaster/selYearWorkById",{"id":id},function (data) {
        name =   data.yearName
    })
    return name;

}


