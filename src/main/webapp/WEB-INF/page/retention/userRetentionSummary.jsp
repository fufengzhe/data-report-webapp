<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户留存概况</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-select.min.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-table.css"
          type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/utils/drawChart.js?ver=${jsVersion}"></script>
</head>

<body>

<div class="container-fluid text-center">
    <div class="panel panel-default">
        <div class="panel-heading">
            天维度用户留存趋势 （用户A,B,C在2018-06-01通过掌上国寿注册，之后用户A,C在两天后2018-06-03在掌上国寿登录，则两天后的留存率为2/3，即66.67%）
        </div>
    </div>
    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-2'></div>
            <div class='col-sm-4'>
                <div class="form-group">
                    <div class='input-group date text-center'>
                        <span class="input-group-addon">注册开始日期</span>
                        <input type='text' class="form-control" id="startDate" placeholder="选择时间"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        <span class="input-group-addon">注册截止日期</span>
                        <input type='text' class="form-control" id="endDate" placeholder="选择时间"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-2'>
                <div class="form-group">
                    <div class='select-group text-center'>
                        <select id="userSource" class="selectpicker" data-live-search="true">
                        </select>
                    </div>
                </div>
            </div>
            <div class='col-sm-1'>
                <div class="form-group">
                    <div class='input-group text-center'>
                        <span class="select-group-btn">
                            <button type="button" class="btn btn-primary" id="queryDate">开始查询</button>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-1'>
                <button class="btn btn-success" data-toggle="modal" data-target="#dateTrendModal">表格视图
                </button>
            </div>
            <div class='col-sm-2'></div>
        </div>
        <div class="alert alert-warning" style="display:none;" id="noDataOfDateTrend">无数据，请更改查询条件或联系开发人员。</div>
        <div class="container-fluid text-center" id="dateTrendChart" style="height:700px;">
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            渠道维度用户留存趋势 （用户A,B,C在2018-06-01通过掌上国寿注册，之后用户A,C在两天后2018-06-03在掌上国寿登录，则两天后的留存率为2/3，即66.67%）
        </div>
    </div>
    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-3'></div>
            <div class='col-sm-2'>
                <div class="form-group">
                    <div class='input-group date text-center'>
                        <span class="input-group-addon">注册日期</span>
                        <input type='text' class="form-control" id="statDate" placeholder="选择时间"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-2'>
                <div class="form-group">
                    <div class='select-group text-center'>
                        <select id="userSourceOfSourceDimension" class="selectpicker" data-live-search="true" multiple>
                        </select>
                    </div>
                </div>
            </div>
            <div class='col-sm-1'>
                <div class="form-group">
                    <div class='input-group text-center'>
                        <span class="select-group-btn">
                            <button type="button" class="btn btn-primary" id="queryButton">开始查询</button>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-1'>
                <button class="btn btn-success" data-toggle="modal" data-target="#dateTrendSourceDimensionModal">表格视图
                </button>
            </div>
            <div class='col-sm-3'></div>
        </div>
        <div class="alert alert-warning" style="display:none;" id="noDataOfDateTrendSourceDimension">
            无数据，请更改查询条件或联系开发人员。
        </div>
        <div class="container-fluid text-center" id="dateTrendChartSourceDimension" style="height:700px;">
        </div>
    </div>

    <%--模态框--%>
    <div class="modal fade" id="dateTrendModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="t rue">
        <div class="modal-dialog" style="width: 1000px;height: 1000px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="dateTrendTable"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <%--模态框--%>
    <div class="modal fade" id="dateTrendSourceDimensionModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel"
         aria-hidden="t rue">
        <div class="modal-dialog" style="width: 1000px;height: 1000px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="dateTrendSourceDimensionTable"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bootstrap-select.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bootstrap-table.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/utils/commonUtils.js?ver=${jsVersion}"></script>
<script>
    var list =${userRetentionList};
    var userSourceList =${userSourceList};
    $("#startDate").val('${startDate}');
    $("#endDate").val('${endDate}');
    $("#statDate").val('${startDate}');
    var userSourceDom = $("#userSource");
    var userSourceOfSourceDimensionDom = $("#userSourceOfSourceDimension");
    userSourceDom.selectpicker({
        noneSelectedText: '掌上国寿'
    });
    userSourceOfSourceDimensionDom.selectpicker({
        noneSelectedText: '全部渠道'
    });
    for (var i = 0; i < userSourceList.length; i++) {
        userSourceDom.append("<option value=" + userSourceList[i].userSourceName + ">" + userSourceList[i].userSourceName + "</option>");
        userSourceOfSourceDimensionDom.append("<option value=" + userSourceList[i].userSourceName + ">" + userSourceList[i].userSourceName + "</option>");
    }
    userSourceDom.selectpicker('refresh');
    userSourceOfSourceDimensionDom.selectpicker('refresh');
    if (list[1].length == 0) {
        $("#noDataOfDateTrend").css('display', 'block');
    } else {
        var dates =${dates};
        trendChart(list[1], dates, "dateTrendChart", "天维度用户留存趋势", "1");
    }
    if (list[3].length == 0) {
        $("#noDataOfDateTrendSourceDimension").css('display', 'block');
    } else {
        var dates =${dates};
        trendChart(list[3], dates, "dateTrendChartSourceDimension", "渠道维度用户留存趋势", "2");
    }
    function trendChart(data, dates, divId, chartName, queryType) {
        var legendData = [];
        var xData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            if ("1" === queryType) {
                legendData.push(data[i].registerTime);
            } else {
                legendData.push(data[i].userSource);
            }
            seriesData.push({
                name: "1" === queryType ? data[i].registerTime : data[i].userSource,
                type: 'line',
                areaStyle: {normal: {}},
                data: [data[i].retentionRate01, data[i].retentionRate02, data[i].retentionRate03, data[i].retentionRate04, data[i].retentionRate05,
                    data[i].retentionRate06, data[i].retentionRate07, data[i].retentionRate14, data[i].retentionRate30]
            });
        }
        for (var i = 0; i < dates.length; i++) {
            xData.push(dates[i]);
        }
        drawTrendChart(divId, chartName, legendData, xData, seriesData, '留存间隔', '留存率(%)');
    }

    $(function () {
        $("#queryDate").click(function () {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var userSource = $('#userSource').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'numQuery',
                    data: {"startDate": startDate, "endDate": endDate, "userSource": userSource, "queryType": "1"},
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        list = data.detailInfo;
                        if (list[0].length == 0) {
                            $("#dateTrendTable").bootstrapTable('load', []);
                        } else {
                            $("#dateTrendTable").bootstrapTable('load', list[0]);
                        }
                        if (list[1].length == 0) {
                            $("#noDataOfDateTrend").css('display', 'block');
                            echarts.init(document.getElementById('dateTrendChart')).clear();
                        } else {
                            $("#noDataOfDateTrend").css('display', 'none');
                            trendChart(list[1], dates, "dateTrendChart", "天维度用户留存趋势", "1");
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    $(function () {
        $("#queryButton").click(function () {
            var startDate = $("#statDate").val();
            var endDate = $("#statDate").val();
            var userSource = $('#userSourceOfSourceDimension').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryButton', true);
                $.ajax({
                    url: 'numQuery',
                    data: {"startDate": startDate, "endDate": endDate, "userSource": userSource, "queryType": "2"},
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        list = data.detailInfo;
                        if (list[0].length == 0) {
                            $("#dateTrendSourceDimensionTable").bootstrapTable('load', []);
                        } else {
                            $("#dateTrendSourceDimensionTable").bootstrapTable('load', list[0]);
                        }
                        if (list[1].length == 0) {
                            $("#noDataOfDateTrendSourceDimension").css('display', 'block');
                            echarts.init(document.getElementById('dateTrendChartSourceDimension')).clear();
                        } else {
                            $("#noDataOfDateTrendSourceDimension").css('display', 'none');
                            trendChart(list[1], dates, "dateTrendChartSourceDimension", "渠道维度用户留存趋势", "2");
                        }
                        setButtonDisabled('queryButton', false);
                    }
                });
            }
        });
    });
    $('#dateTrendTable,#dateTrendSourceDimensionTable').bootstrapTable({
        cache: false,
        pagination: true,
        search: true,
        pageList: [5, 10],
        columns: [[
            {
                field: 'registerTime',
                title: '注册日期',
                halign: 'center',
                valign: "middle",
                rowspan: 2
            }, {
                field: 'userSource',
                title: '渠道',
                align: 'center',
                valign: "middle",
                rowspan: 2,
            }, {
                field: 'retentionRatio',
                title: '留存率',
                align: 'center',
                halign: 'center',
                valign: "middle",
                colspan: 9,
                rowspan: 1
            }
        ], [
            {
                field: 'retentionRate01',
                title: '1天后',
                align: 'center',
            }, {
                field: 'retentionRate02',
                title: '2天后',
                align: 'center',
            }, {
                field: 'retentionRate03',
                title: '3天后',
                align: 'center',
            }, {
                field: 'retentionRate04',
                title: '4天后',
                align: 'center',
            }, {
                field: 'retentionRate05',
                title: '5天后',
                align: 'center',
            }, {
                field: 'retentionRate06',
                title: '6天后',
                align: 'center',
            }, {
                field: 'retentionRate07',
                title: '7天后',
                align: 'center',
            }, {
                field: 'retentionRate14',
                title: '14天后',
                align: 'center',
            }, {
                field: 'retentionRate30',
                title: '30天后',
                align: 'center',
            }
        ]]
    });
    $("#dateTrendTable").bootstrapTable('load', list[0]);
    $("#dateTrendSourceDimensionTable").bootstrapTable('load', list[2]);
</script>

</body>
</html>