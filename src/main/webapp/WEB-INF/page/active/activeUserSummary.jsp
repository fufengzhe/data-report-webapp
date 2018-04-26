<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>活跃用户数概览</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/utils/drawChart.js"></script>

</head>

<body>

<div class="container-fluid text-center">
    <div class="panel panel-default">
        <div class="panel-heading">
            各渠道昨日及当月（本月1号截止到昨日）活跃用户数分布
        </div>
        <%--<div class="panel-body">--%>
        <%--面板内容--%>
        <%--</div>--%>
    </div>

    <div class="row">
        <div class="text-center col-md-6" id="datePieChart" style="height:700px">

        </div>

        <div class="text-center col-md-6" id="monthPieChart" style="height:700px">

        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            各渠道过去七天活跃用户数趋势
        </div>
        <%--<div class="panel-body">--%>
        <%--面板内容--%>
        <%--</div>--%>
    </div>

    <div class="container-fluid text-center" id="dateTrendChart" style="height:700px;">

    </div>


</div>

<script type="text/javascript">
    var list =${activeUserSummaryList};
    var legendDataOfDate = [];
    var legendDataOfMonth = [];
    var seriesDataOfDate = [];
    var seriesDataOfMonth = [];
    var dateList = list[0];
    var monthList = list[1];
    for (var i = 0; i < dateList.length; i++) {
        legendDataOfDate.push(dateList[i].userSource);
        seriesDataOfDate.push({value: dateList[i].activeUserNum, name: dateList[i].userSource});
    }
    for (var i = 0; i < monthList.length; i++) {
        legendDataOfMonth.push(monthList[i].userSource);
        seriesDataOfMonth.push({value: monthList[i].activeUserNum, name: monthList[i].userSource});
    }
    drawPieChart("datePieChart", "昨日各渠道活跃数分布", legendDataOfDate, seriesDataOfDate);
    drawPieChart("monthPieChart", "当月各渠道活跃数分布", legendDataOfMonth, seriesDataOfMonth);

    dateList = list[2];
    dateStrs =${dates};
    lengendDataOfDate = [];
    xDataOfDate = [];
    seriesDataOfDate = [];

    for (var i = 0; i < dateList.length; i++) {
        lengendDataOfDate.push(dateList[i].userSource);
        seriesDataOfDate.push({
            name: dateList[i].userSource, type: 'line', areaStyle: {normal: {}},
            data: [dateList[i].activeUserNumOf7, dateList[i].activeUserNumOf6, dateList[i].activeUserNumOf5, dateList[i].activeUserNumOf4, dateList[i].activeUserNumOf3,
                dateList[i].activeUserNumOf2, dateList[i].activeUserNumOf1]
        });
    }
    for (var i = 0; i < dateStrs.length; i++) {
        xDataOfDate.push(dateStrs[i]);
    }
    drawTrendChart("dateTrendChart", "各渠道过去七天活跃用户数趋势", lengendDataOfDate, xDataOfDate, seriesDataOfDate);
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>

</body>
</html>