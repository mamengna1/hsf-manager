$(function () {
    var id = $("#id").val();
    $.getJSON("/manager/userDetail/selUserReleaseById", {"id": id}, function (res) {
        var appointTime = toDate(res.appointTime)
        var newDate=/\d{4}-\d{1,2}-\d{1,2}/g.exec(appointTime)
        $("#appointTime").val(newDate)

        $("#demand").val(res.demand);

        $("#placeProvince").val(res.serviceProvince);
        $("#placeCity").val(res.serviceCity);
        $("#placeArea").val(res.serviceArea);

        if(res.state==2 || res.state == 3 || res.state ==4 ){
            $("#gengGai").hide();
        }else{
            $("#gengGai").show();
        }
    })
});

$("#gengGai").click(function () {
    var ids = $("#id").val();
    location.href="/manager/userDetail/goPaiDan?id="+ids;
})

$("#returnUp").click(function () {
    location.href="/manager/userRelease/goUserReleaseAll";
})