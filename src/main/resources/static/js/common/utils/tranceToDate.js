
/*日期格式化*/
function toDate(data) {
    var date = new Date(data).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
    return date;
}