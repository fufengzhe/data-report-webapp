<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>财险保费概览</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css"
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
            财险保费分布及KPI完成率
        </div>
    </div>

    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-1'></div>
            <div class='col-sm-4'>
                <div class="form-group">
                    <div class='input-group date text-center'>
                        <input type='text' class="form-control" id="startDate" placeholder="选择时间"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        <input type='text' class="form-control" id="endDate" placeholder="选择时间"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-primary" id="queryDate">开始查询</button>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-1'>
                <button class="btn btn-success" data-toggle="modal" data-target="#pieModal">表格视图
                </button>
            </div>
            <div class='col-sm-1'></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfPremiumPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="premiumPieChart" style="height:700px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfCompleteGauge">无数据，请更改查询条件或联系开发人员。</div>
            <div id="completeGauge" style="height:700px"></div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            过去七天财险保费趋势&nbsp&nbsp
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
                    <table id="pieTable"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <%--模态框--%>
    <div class="modal fade" id="dateTrendModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true" >
        <div class="modal-dialog" style="width:900px">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body" >
                    <table id="dateTrendTable" ></table>
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
<script>
    var list =${premiumList};
    $("#startDate").val('${startDate}');
    $("#endDate").val('${endDate}');
    pieChart(list[0], "premiumPieChart", "财险保费分布(万元)");
    function pieChart(data, divId, chartName) {
        var legendData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].branchName);
            seriesData.push({value: data[i].accumulatedAmount, name: data[i].branchName});
        }
        drawPieChart(divId, chartName, legendData, seriesData);
    }
    gaugeChart(list[1], "completeGauge", "KPI完成率");
    function gaugeChart(data, divId, chartName) {
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            [{value: 50, name: '完成率'}]
            seriesData.push({value: data[i].completeRatio.replace("%", ""), name: "KPI完成率"});
        }
        drawGaugeChart(divId, chartName, seriesData);
    }
    var dateStrs =${dates};
    trendChart(list[2], dateStrs, "dateTrendChart", "过去七天财险保费趋势(万元)");
    function trendChart(data, dateStrs, divId, chartName) {
        var legendData = [];
        var xData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].branchName);
            seriesData.push({
                name: data[i].branchName, type: 'line', areaStyle: {normal: {}},
                data: [data[i].accumulatedAmount7, data[i].accumulatedAmount6, data[i].accumulatedAmount5, data[i].accumulatedAmount4, data[i].accumulatedAmount3,
                    data[i].accumulatedAmount2, data[i].accumulatedAmount1]
            });
        }
        for (var i = 0; i < dateStrs.length; i++) {
            xData.push(dateStrs[i]);
        }
        drawTrendChart(divId, chartName, legendData, xData, seriesData);
    }

    $(function () {
        $("#queryDate").click(function () {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'numQuery',
                    data: {"startDate": startDate, "endDate": endDate},
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list.length == 0) {
                                $("#noDataOfPremiumPie").css('display', 'block');
                                echarts.init(document.getElementById('premiumPieChart')).clear();
                                $("#pieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfPremiumPie").css('display', 'none');
                                pieChart(list, "premiumPieChart", "财险保费分布(万元)");
                                $("#pieTable").bootstrapTable('load', list);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    generateDataTable("pieTable", [[{"field": "branchName"}, {"field": "accumulatedAmount"}],
        [{"title": "分公司"}, {"title": "保费（万元）"}]])
    $("#pieTable").bootstrapTable('load', list[0]);
    generateDataTable("dateTrendTable", [[{"field": "branchName"}, {"field": "accumulatedAmount7"}, {"field": "accumulatedAmount6"}, {"field": "accumulatedAmount5"},
        {"field": "accumulatedAmount4"}, {"field": "accumulatedAmount3"}, {"field": "accumulatedAmount2"}, {"field": "accumulatedAmount1"}],
        [{"title": "渠道"}, {"title": dateStrs[0]}, {"title": dateStrs[1]}, {"title": dateStrs[2]}, {"title": dateStrs[3]}, {"title": dateStrs[4]}, {"title": dateStrs[5]}, {"title": dateStrs[6]}]])
    $("#dateTrendTable").bootstrapTable('load', list[2]);
</script>

</body>
</html>