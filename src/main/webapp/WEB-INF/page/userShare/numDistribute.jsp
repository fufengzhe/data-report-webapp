<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>共享条款签署分布</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-select.min.css"
          type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/utils/drawChart.js?ver=${jsVersion}"></script>
</head>

<body>

<div class="container-fluid text-center">
    <div class="panel panel-default">
        <div class="panel-heading">
            共享条款签署渠道及时间段分布
        </div>
        <%--<div class="panel-body">--%>
        <%--面板内容--%>
        <%--</div>--%>
    </div>

    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-2'></div>
            <div class='col-sm-2'>
                <div class="form-group">
                    <div class='input-group date text-center'>
                        <input type='text' class="form-control" id="startDate" placeholder="选择时间"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-2'>
                <div class="form-group">
                    <div class='input-group date text-center'>
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
                        <select id="userSource" class="selectpicker" data-live-search="true" multiple>
                        </select>
                    </div>
                </div>
            </div>
            <div class='col-sm-2'>
                <div class="form-group">
                    <div class='input-group text-center'>
                        <span class="select-group-btn">
                            <button type="button" class="btn btn-primary" id="queryDate">开始查询</button>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-2'></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfUserSourcePie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="userSourcePieChart" style="height:700px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfHourPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="hourPieChart" style="height:700px"></div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            过去七天共享条款签署数趋势
        </div>
        <%--<div class="panel-body">--%>
        <%--面板内容--%>
        <%--</div>--%>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataOfDateTrend">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="dateTrendChart" style="height:700px;">
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
        src="${pageContext.request.contextPath}/static/js/utils/commonUtils.js?ver=${jsVersion}"></script>
<script>
    var userSourceList =${userSourceList};
    var userSourceDom = $("#userSource");
    userSourceDom.selectpicker({
        noneSelectedText: '全部签署渠道'
    });
    for (var i = 0; i < userSourceList.length; i++) {
        userSourceDom.append("<option value=" + userSourceList[i].userSource + ">" + userSourceList[i].userSource + "</option>");
    }
    userSourceDom.selectpicker('refresh');

    var list =${userShareList};
    $("#startDate").val('${startDate}');
    pieChart(list[0], "userSourcePieChart", "共享条款签署渠道分布");
    $("#endDate").val('${endDate}');
    pieChart(list[1], "hourPieChart", "共享条款签署时间段分布");
    function pieChart(data, divId, chartName) {
        var legendData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].userSource);
            seriesData.push({value: data[i].shareNum, name: data[i].userSource});
        }
        drawPieChart(divId, chartName, legendData, seriesData);
    }
    var dateStrs=${dates};
    trendChart(list[2], dateStrs, "dateTrendChart", "过去七天共享条款签署数趋势");
    function trendChart(data, dateStrs, divId, chartName) {
        var legendData = [];
        var xData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].userSource);
            seriesData.push({
                name: data[i].userSource, type: 'line', areaStyle: {normal: {}},
                data: [data[i].shareNum7, data[i].shareNum6, data[i].shareNum5, data[i].shareNum4, data[i].shareNum3,
                    data[i].shareNum2, data[i].shareNum1]
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
            var userSource = $('#userSource').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'numQuery',
                    data: {"startDate": startDate, "endDate": endDate, "userSource": userSource},
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list[0].length == 0) {
                                $("#noDataOfUserSourcePie").css('display', 'block');
                                echarts.init(document.getElementById('userSourcePieChart')).clear();
                            } else {
                                $("#noDataOfUserSourcePie").css('display', 'none');
                                pieChart(list[0], "userSourcePieChart", "共享条款签署渠道分布");
                            }
                            if (list[1].length == 0) {
                                $("#noDataOfHourPie").css('display', 'block');
                                echarts.init(document.getElementById('hourPieChart')).clear();
                            } else {
                                $("#noDataOfHourPie").css('display', 'none');
                                pieChart(list[1], "hourPieChart", "共享条款签署时间段分布");
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });

</script>

</body>
</html>