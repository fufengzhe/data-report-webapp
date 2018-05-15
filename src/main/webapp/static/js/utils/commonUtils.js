function setButtonDisabled(id,disabled) {
    var msg = '';
    if(id == 'toLogin') {
        if (disabled) {
            msg='登录中...';
        } else {
            msg='登录';
        }
        $("#toLogin").html(msg);
        $("#toLogin").attr('disabled',disabled);
        return;
    }
    if (disabled) {
        msg='查询中...';
    } else {
        msg='开始查询';
    }
    $("#"+id).html(msg);
    $("#"+id).attr('disabled',disabled);
}

$(function(){
    $("#startDate").datetimepicker({
        language:"zh-CN",
        weekStart:1,
        todayBtn:1,
        autoclose:1,
        todayHighlight:1,
        startView:2,
        minView:2,
        forceParse:0,
        format:"yyyy-mm-dd"});// 选择时间
});

$(function(){
    $("#endDate").datetimepicker({
        language:"zh-CN",
        weekStart:1,
        todayBtn:1,
        autoclose:1,
        todayHighlight:1,
        startView:2,
        minView:2,
        forceParse:0,
        format:"yyyy-mm-dd"});// 选择时间
});

$(function(){
    $("#startMonth").datetimepicker({
        language:"zh-CN",
        weekStart:1,
        todayBtn:0,
        autoclose:1,
        startView:3,
        minView:3,
        forceParse:1,
        format:"yyyy-mm"});// 选择时间
});