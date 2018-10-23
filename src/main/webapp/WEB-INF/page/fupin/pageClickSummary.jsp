<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>扶贫相关页面点击数</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-table.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/daterangepicker-bs3.css" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/utils/drawChart.js?ver=${jsVersion}"></script>
</head>
<body>
<div class="container-fluid text-center">
    <div class="panel panel-default">
        <div class="panel-heading">
            扶贫相关页面点击数分布(数据从2018-10-14日起)
        </div>
    </div>

    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-3'></div>
            <div class='col-sm-4'>
                <div class="form-group">
                    <div class='input-group date text-center'>
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
                <button class="btn btn-success" data-toggle="modal" data-target="#datePieModal">表格视图
                </button>
            </div>
            <div class='col-sm-3'>
            </div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfDatePVPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="datePVPieChart" style="height:700px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfDateUVPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="dateUVPieChart" style="height:700px"></div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            过去七天扶贫相关页面点击次数趋势&nbsp&nbsp
            <button class="btn btn-success" data-toggle="modal" data-target="#dateTrendModal">表格视图
            </button>
        </div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataOfDateTrend">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="dateTrendChart" style="height:700px;">
    </div>
    <%--模态框--%>
    <div class="modal fade" id="datePieModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="datePieTable"></table>
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
    var list =${pageClickList};
    var dateRange='${startDate}'+" ~ "+'${endDate}';
    $("#dateRange").val(dateRange);
    if (list[0].length == 0) {
        $("#noDataOfDatePVPie").css('display', 'block');
        $("#noDataOfDateUVPie").css('display', 'block');
    } else {
        pieChart(list[0], "datePVPieChart", "dateUVPieChart", "扶贫相关页面点击次数分布", "扶贫相关页面点击人数分布");
    }
    function pieChart(data, divId1, divId2, chartName1, chartName2) {
        var legendData = [];
        var seriesData1 = [];
        var seriesData2 = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].pageName);
            seriesData1.push({value: data[i].clickPV, name: data[i].pageName});
            seriesData2.push({value: data[i].clickUV, name: data[i].pageName});
        }
        drawPieChart(divId1, chartName1, legendData, seriesData1);
        drawPieChart(divId2, chartName2, legendData, seriesData2);
    }
    var dateStrs =${dates};
    if (list[1].length == 0) {
        $("#noDataOfDateTrend").css('display', 'block');
    } else {
        trendChart(list[1], dateStrs, "dateTrendChart", "过去七天扶贫相关页面点击次数趋势");
    }
    function trendChart(data, dateStrs, divId, chartName) {
        var legendData = [];
        var xData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].pageName);
            seriesData.push({
                name: data[i].pageName, type: 'line', areaStyle: {normal: {}},
                data: [data[i].clickPV7, data[i].clickPV6, data[i].clickPV5, data[i].clickPV4, data[i].clickPV3,
                    data[i].clickPV2, data[i].clickPV1]
            });
        }
        for (var i = 0; i < dateStrs.length; i++) {
            xData.push(dateStrs[i]);
        }
        drawTrendChart(divId, chartName, legendData, xData, seriesData,"","页面点击次数");
    }

    $(function () {
        $("#queryDate").click(function (){
            var dateRange=$("#dateRange").val();
            var startDate = dateRange.substr(0,10);
            var endDate = dateRange.substr(13);
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
                                $("#noDataOfDatePVPie").css('display', 'block');
                                $("#noDataOfDateUVPie").css('display', 'block');
                                echarts.init(document.getElementById('datePVPieChart')).clear();
                                echarts.init(document.getElementById('dateUVPieChart')).clear();
                                $("#datePieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfDatePVPie").css('display', 'none');
                                $("#noDataOfDateUVPie").css('display', 'none');
                                pieChart(list, "datePVPieChart", "dateUVPieChart", "扶贫相关页面点击次数分布", "扶贫相关页面点击人数分布");
                                $("#datePieTable").bootstrapTable('load', list);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    generateDataTable("datePieTable", [[{"field": "pageName"}, {"field": "clickPV"}, {"field": "clickUV"}, {"field": "clickPVPerPerson"}],
        [{"title": "页面名称"}, {"title": "点击次数"}, {"title": "点击人数"}, {"title": "人均点击次数"}]])
    $("#datePieTable").bootstrapTable('load', list[0]);
    generateDataTable("dateTrendTable", [[{"field": "pageName"}, {"field": "clickPV7"}, {"field": "clickPV6"}, {"field": "clickPV5"},
        {"field": "clickPV4"}, {"field": "clickPV3"}, {"field": "clickPV2"}, {"field": "clickPV1"}],
        [{"title": "页面名称"}, {"title": dateStrs[0]}, {"title": dateStrs[1]}, {"title": dateStrs[2]}, {"title": dateStrs[3]}, {"title": dateStrs[4]}, {"title": dateStrs[5]}, {"title": dateStrs[6]}]])
    $("#dateTrendTable").bootstrapTable('load', list[1]);
</script>

</body>
</html>