<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>注册用户数概览</title>
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
            各渠道天维度及月维度注册用户数分布
        </div>
    </div>

    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-1'></div>
            <div class='col-sm-3'>
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
                <button class="btn btn-success" data-toggle="modal" data-target="#datePieModal">表格视图
                </button>
            </div>

            <div class='col-sm-2'></div>
            <div class='col-sm-2'>
                <div class="form-group">
                    <div class='input-group date text-center'>
                        <input type='text' class="form-control" id="startMonth" placeholder="选择时间"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-primary" id="queryMonth">开始查询</button>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-1'>
                <button class="btn btn-success" data-toggle="modal" data-target="#monthPieModal">表格视图
                </button>
            </div>
            <div class='col-sm-2'></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfDatePie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="datePieChart" style="height:700px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfMonthPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="monthPieChart" style="height:700px"></div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            过去七天注册用户数趋势&nbsp&nbsp
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
    <div class="modal fade" id="monthPieModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="monthPieTable"></table>
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
<script>
    var list =${registerUserList};
    $("#startDate").val('${startDate}');
    $("#endDate").val('${endDate}');
    if (list[0].length == 0) {
        $("#noDataOfDatePie").css('display', 'block');
    } else {
        pieChart(list[0], "datePieChart", "天维度各渠道注册数分布");
    }
    $("#startMonth").val('${startMonth}');
    if (list[1].length == 0) {
        $("#noDataOfMonthPie").css('display', 'block');
    } else {
        pieChart(list[1], "monthPieChart", "月维度各渠道注册数分布");
    }
    function pieChart(data, divId, chartName) {
        var legendData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].userSource);
            seriesData.push({value: data[i].registerUserNum, name: data[i].userSource});
        }
        drawPieChart(divId, chartName, legendData, seriesData);
    }
    var dateStrs =${dates};
    if (list[2].length == 0) {
        $("#noDataOfDateTrend").css('display', 'block');
    } else {
        trendChart(list[2], dateStrs, "dateTrendChart", "");
    }
    function trendChart(data, dateStrs, divId, chartName) {
        var legendData = [];
        var xData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].userSource);
            seriesData.push({
                name: data[i].userSource, type: 'line', areaStyle: {normal: {}},
                data: [data[i].registerUserNumOf7, data[i].registerUserNumOf6, data[i].registerUserNumOf5, data[i].registerUserNumOf4, data[i].registerUserNumOf3,
                    data[i].registerUserNumOf2, data[i].registerUserNumOf1]
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
                                $("#noDataOfDatePie").css('display', 'block');
                                echarts.init(document.getElementById('datePieChart')).clear();
                                $("#datePieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfDatePie").css('display', 'none');
                                pieChart(list, "datePieChart", "天维度各渠道注册数分布");
                                $("#datePieTable").bootstrapTable('load', list);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });

    $(function () {
        $("#queryMonth").click(function () {
            var startMonth = $("#startMonth").val();
            if ("" != startMonth.trim()) {
                setButtonDisabled('queryMonth', true);
                $.ajax({
                    url: 'numQuery',
                    data: {"startDate": startMonth, "endDate": (startMonth + "-31")},
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list.length == 0) {
                                $("#noDataOfMonthPie").css('display', 'block');
                                echarts.init(document.getElementById('monthPieChart')).clear();
                                $("#monthPieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfMonthPie").css('display', 'none');
                                pieChart(list, "monthPieChart", "月维度各渠道注册数分布");
                                $("#monthPieTable").bootstrapTable('load', list);
                            }
                        }
                        setButtonDisabled('queryMonth', false);
                    }
                });
            }
        });
    });
    generateDataTable("datePieTable", [[{"field": "userSource"}, {"field": "registerUserNum"}],
        [{"title": "渠道"}, {"title": "注册用户数"}]])
    $("#datePieTable").bootstrapTable('load', list[0]);
    generateDataTable("monthPieTable", [[{"field": "userSource"}, {"field": "registerUserNum"}],
        [{"title": "渠道"}, {"title": "注册用户数"}]])
    $("#monthPieTable").bootstrapTable('load', list[1]);
    generateDataTable("dateTrendTable", [[{"field": "userSource"}, {"field": "registerUserNumOf7"}, {"field": "registerUserNumOf6"}, {"field": "registerUserNumOf5"},
        {"field": "registerUserNumOf4"}, {"field": "registerUserNumOf3"}, {"field": "registerUserNumOf2"}, {"field": "registerUserNumOf1"}],
        [{"title": "渠道"}, {"title": dateStrs[0]}, {"title": dateStrs[1]}, {"title": dateStrs[2]}, {"title": dateStrs[3]}, {"title": dateStrs[4]}, {"title": dateStrs[5]}, {"title": dateStrs[6]}]])
    $("#dateTrendTable").bootstrapTable('load', list[2]);
</script>

</body>
</html>