

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



