$(function () {
    var id = $("#id").val();
    $.getJSON("/manager/userRelease/selReleaseById", {"id": id}, function (res) {
        $("#worksArea,#demand").val("");

        var worksArea =   showProvince(res.serviceProvince,res.serviceCity,res.serviceArea);
        $("#worksArea").val(worksArea);
        $('#demand').val(res.demand)
    })

})