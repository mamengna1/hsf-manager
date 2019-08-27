$(function () {
    var id = $("#id").val();
    $.getJSON("/manager/userDetail/selUserReleaseById", {"id": id}, function (res) {
        var appointTime = toDate(new Date(res.appointTime).toJSON())
        var newDate=/\d{4}-\d{1,2}-\d{1,2}/g.exec(appointTime)
        $("#appointTime").val(newDate)

        chooseProvince(res.serviceProvince);
        $("#placeProvince").val(res.serviceProvince);
        chooseCity(res.serviceCity);
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
