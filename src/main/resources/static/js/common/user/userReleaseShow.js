$(function () {
    $.ajaxSettings.async = false;
    var id = $("#id").val();
    $.getJSON("/manager/userRelease/selReleaseById", {"id": id}, function (res) {
        $("#worksArea,#demand").val("");

        var worksArea = getUserWork(res.serviceProvince, res.serviceCity, res.serviceArea);
        $("#worksArea").val(worksArea);
        $('#demand').val(res.demand)
    })
    $.ajaxSettings.async = true;
})