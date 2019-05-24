<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0"/>
    <link rel="icon" href="${pageContext.request.contextPath}/static/img/sale_ico.ico" type="img/x-ico"/>
    <title>用户注册查询分析</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-table.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/daterangepicker-bs3.css"
          type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/utils/drawChart.js?ver=${jsVersion}"></script>
</head>
<body>
<div class="container-fluid text-center">
    <div class="panel panel-default">
        <div class="panel-heading">
            注册用户数分布
        </div>
    </div>

    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-3'></div>
            <div class='col-sm-4'>
                <div class="form-group">
                    <div class='input-group date text-center'>
                        <span class="input-group-addon">注册时间</span>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        <input type="text" class="form-control" placeholder="时间范围" id="dateRange"/>
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-primary" id="queryDate">开始查询</button>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-2'>
                <button class="btn btn-success" data-toggle="modal" data-target="#pieModal">表格视图
                </button>
            </div>
            <div class='col-sm-3'>
            </div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfSourcePie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="sourcePieChart" style="height:600px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfHourPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="hourPieChart" style="height:600px"></div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            过去七天注册人数趋势&nbsp&nbsp
            <button class="btn btn-success" data-toggle="modal" data-target="#dateTrendModal">表格视图
            </button>
        </div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataOfDateTrend">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="dateTrendChart" style="height:700px;">
    </div>
    <%--模态框--%>
    <div class="modal fade" id="pieModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="sourcePieTable"></table>
                    <table id="hourPieTable"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <%--模态框--%>
    <div class="modal fade" id="dateTrendModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" style="width:900px">
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
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bootstrap-table.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/utils/commonUtils.js?ver=${jsVersion}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/date_compare.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/daterangepicker.js"></script>
<script>
    var list =${saleUserRegisterNumList};
    var dateRange = '${startDate}' + " ~ " + '${endDate}';
    $("#dateRange").val(dateRange);
    if (list[0].length == 0) {
        $("#noDataOfSourcePie").css('display', 'block');
    } else {
        pieChart(list[0], "sourcePieChart", "渠道分布", 1);
    }
    if (list[1].length == 0) {
        $("#noDataOfHourPie").css('display', 'block');
    } else {
        pieChart(list[1], "hourPieChart", "注册时段分布", 2);
    }
    function pieChart(data, divId, chartName, legendIndex) {
        var legendData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            var str = legendIndex == 1 ? data[i].registerSource : data[i].registerHour;
            legendData.push(str);
            seriesData.push({value: data[i].registerNum, name: str});
        }
        drawPieChart(divId, chartName, legendData, seriesData, "");
    }
    var dateStrs =${dates};
    if (list[2].length == 0) {
        $("#noDataOfDateTrend").css('display', 'block');
    } else {
        trendChart(list[2], dateStrs, "dateTrendChart", "过去七天注册人数趋势");
    }
    function trendChart(data, dateStrs, divId, chartName) {
        var legendData = [];
        var xData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].registerSource);
            seriesData.push({
                name: data[i].registerSource, type: 'line', areaStyle: {normal: {}},
                data: [data[i].registerNum7, data[i].registerNum6, data[i].registerNum5, data[i].registerNum4, data[i].registerNum3, data[i].registerNum2, data[i].registerNum1]
            });
        }
        for (var i = 0; i < dateStrs.length; i++) {
            xData.push(dateStrs[i]);
        }
        drawTrendChart(divId, chartName, legendData, xData, seriesData, "", "注册数");
    }

    $(function () {
        $("#queryDate").click(function () {
            var dateRange = $("#dateRange").val();
            var startDate = dateRange.substr(0, 10);
            var endDate = dateRange.substr(13);
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'registerNumQuery',
                    data: {"startDate": startDate, "endDate": endDate},
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list[0].length == 0 || list[1].length == 0) {
                                $("#noDataOfSourcePie").css('display', 'block');
                                $("#noDataOfHourPie").css('display', 'block');
                                echarts.init(document.getElementById('sourcePieChart')).clear();
                                echarts.init(document.getElementById('hourPieChart')).clear();
                                $("#sourcePieTable").bootstrapTable('load', []);
                                $("#hourPieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfSourcePie").css('display', 'none');
                                $("#noDataOfHourPie").css('display', 'none');
                                pieChart(list[0], "sourcePieChart", "渠道分布", 1);
                                pieChart(list[1], "hourPieChart", "注册时段分布", 2);
                                $("#sourcePieTable").bootstrapTable('load', list[0]);
                                $("#hourPieTable").bootstrapTable('load', list[1]);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    generateDataTable("sourcePieTable", [[{"field": "registerSource"}, {"field": "registerNum"}],
        [{"title": "渠道"}, {"title": "注册数"}]])
    $("#sourcePieTable").bootstrapTable('load', list[0]);
    generateDataTable("hourPieTable", [[{"field": "registerHour"}, {"field": "registerNum"}],
        [{"title": "注册时段"}, {"title": "注册数"}]])
    $("#hourPieTable").bootstrapTable('load', list[1]);
    generateDataTable("dateTrendTable", [[{"field": "registerSource"}, {"field": "registerNum7"}, {"field": "registerNum6"}, {"field": "registerNum5"},
        {"field": "registerNum4"}, {"field": "registerNum3"}, {"field": "registerNum2"}, {"field": "registerNum1"}],
        [{"title": "产品名称"}, {"title": dateStrs[0]}, {"title": dateStrs[1]}, {"title": dateStrs[2]}, {"title": dateStrs[3]}, {"title": dateStrs[4]}, {"title": dateStrs[5]}, {"title": dateStrs[6]}]])
    $("#dateTrendTable").bootstrapTable('load', list[2]);
</script>

</body>
</html>