<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>活跃用户数概览</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>

</head>

<body>
<div class="container text-center">
    <div class="panel panel-default">
        <div class="panel-heading">
            本页面用于展示各个前端的昨日及当月（本月1号截止到昨日）活跃用户数分布
        </div>
        <%--<div class="panel-body">--%>
            <%--面板内容--%>
        <%--</div>--%>
    </div>

    <div class="text-center" id="dateChart" style="height:800px">

    </div>
</div>


<div class="container text-center">
    <div class="text-center" id="monthChart" style="height:800px">

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
    drawChart("dateChart", "昨日各前端活跃数分布", legendDataOfDate, seriesDataOfDate);
    drawChart("monthChart", "当月各前端活跃数分布", legendDataOfMonth, seriesDataOfMonth);

    function drawChart(divId, chartName, legendData, seriesData) {
        var myChart = echarts.init(document.getElementById(divId));
        var option = {
            title: {
                text: chartName,
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                align:'right',
                left: 'left',
                data: legendData
            },
            series: [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: seriesData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
    }
</script>


<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>

</body>
</html>