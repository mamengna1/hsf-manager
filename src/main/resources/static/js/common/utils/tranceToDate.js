

function toDate(date) {
    // 日期转换
    var da = new Date(date);
    return new Date(+new Date(da)+8*3600*1000).toISOString().replace(/\-/g, '/').replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
}