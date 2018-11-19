$(function () {

    $('#dateRange').daterangepicker(
        {
            ranges: {
//              '今天': [moment(), moment()],
                '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                '最近7天': [moment().subtract(7, 'days'), moment().subtract(1, 'days')],
                '最近30天': [moment().subtract(30, 'days'), moment().subtract(1, 'days')],
//              '当月': [moment().startOf('month'), moment().endOf('month')],
                '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            locale: {
                applyLabel: '确定',
                cancelLabel: '取消',
                fromLabel: '从',
                toLabel: '到',
                customRangeLabel: '自定义时间段',
                daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                firstDay: 1
            },
            format: 'YYYY-MM-DD',
//			startDate:moment(),
//			endDate: moment()
            startDate: $("#dateRange").val().substr(0, 10),
            endDate: $("#dateRange").val().substr(13)
        }
    );
    // $('#compare-dateselect').daterangepicker(
    //     {
    //         ranges: {
    //             '上星期': [
    //                 moment().subtract('day', 7),
    //                 moment()
    //             ],
    //             '上个月': [moment().subtract('month', 1),
    //                 moment().subtract('month', 1).endOf('month')
    //             ]
    //         },
    //         locale: {
    //             applyLabel: '确定',
    //             cancelLabel: '取消',
    //             fromLabel: '从',
    //             toLabel: '到',
    //             customRangeLabel: '自定义时间段',
    //             daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
    //             monthNames: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
    //             firstDay: 1
    //         },
    //         format: 'YYYY-MM-DD',
    //         startDate: moment(),
    //         endDate: moment()
    //     },
    //     function (start, end, label) {
    //         var compareRange = getCompareRange();
    //         var st = compareRange[0];//比对开始时间
    //         var et = compareRange[1];//比对结束时间
    //         var ms = calculateBetweenStAndEt(st, et);
    //         if (label == '上星期') {
    //             //比对的开始时间减7天
    //             start = moment(new Date(st).getTime() - 6 * 24 * 3600 * 1000);
    //             s = new Date(new Date(st).getTime() - 6 * 24 * 3600 * 1000);
    //             var newEt = new Date(s.getTime() + ms);
    //             end = moment(newEt);
    //         } else if (label == '上个月') {
    //             //比对的开始时间减一个月
    //             start = moment(new Date(st).getTime() - 30 * 24 * 3600 * 1000);
    //             s = new Date(new Date(st).getTime() - 30 * 24 * 3600 * 1000);
    //             var newEt = new Date(s.getTime() + ms);
    //             end = moment(newEt);
    //         } else {
    //             end = moment(new Date(start.format('YYYY-MM-DD')).getTime() + ms);
    //             console.log(end);
    //         }
    //         $('#compare-dateselect').val(start.format("YYYY-MM-DD") + " + " + end.format('YYYY-MM-DD'));
    //
    //
    //         function getCompareRange() {
    //             var range = $('#daterange-btn').val();
    //             if ($.trim(range) != '') {
    //                 return range.split('-');
    //             }
    //         }
    //
    //         function calculateBetweenStAndEt(st, et) {
    //             var st_ms = (new Date(st)).getTime();
    //             var et_ms = new Date(et).getTime();
    //             return et_ms - st_ms;
    //         }
    //     }
    // );
})