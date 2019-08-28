$(function () {
    $.ajaxSettings.async = false;
    var id = $("#id").val();
    $.getJSON("/manager/userMaster/selById", {"id": id}, function (res) {
        $("#name").val(res.name);
        $("#card").val(res.card);
        $("#address").val(res.address);
        chooseProvince(res.placeProvince);
        $("#placeProvince").val(res.placeProvince);
        chooseCity(res.placeCity);
        $("#placeCity").val(res.placeCity);
        $("#placeArea").val(res.placeArea);

        chooseProvince2(res.workProvince);
        $("#workProvince").val(res.workProvince);
        chooseCity2(res.workCity);
        $("#workCity").val(res.workCity);
        $("#workArea").val(res.workArea);

        $("#yearWorkId").val(res.yearWorkId);
        var skills = (res.skills).split(",");
        var tags = document.getElementsByClassName("skills");
        for (var i = 0; i < skills.length; i++) {
            $(tags[skills[i] - 1]).prop("checked", "checked");
        }
        $("#skills").val(res.skills);
    })
    $.ajaxSettings.async = true;
    var skills = $("#skills").val().split(",");
    $(".skills").click(function () {
        var len = $(".skills").filter(":checked").length;
        if (len > 3) {
            if ($(this).prop('checked')) {
                return false;
            }
        }
        if ($(this).prop('checked')) {
            skills.push($(this).attr("sid"));
        } else {
            skills.splice(jQuery.inArray($(this).attr("sid"), skills), 1);
        }
        $("#skills").val(skills.join());
        alert($("#skills").val());
    })

});

function changeSou(btn) {
    $("#source").val(btn);
}