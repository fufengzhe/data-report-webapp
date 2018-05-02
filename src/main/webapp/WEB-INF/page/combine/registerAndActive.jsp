<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>注册用户&活跃用户</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/utils/drawChart.js?ver=${jsVersion}"></script>
</head>

<body>

<div class="container-fluid text-center">

    <div class="panel panel-default">
        <div class="panel-heading">
            昨日注册用户&活跃用户分布
        </div>
        <%--<div class="panel-body">--%>
        <%--面板内容--%>
        <%--</div>--%>
    </div>

    <div class="container-fluid text-center" id="dateScatterChart" style="height:700px;">

    </div>
    <br/>
    <div class="panel panel-default">
        <div class="panel-heading">
            当月注册用户&活跃用户分布
        </div>
        <%--<div class="panel-body">--%>
        <%--面板内容--%>
        <%--</div>--%>
    </div>

    <div class="container-fluid text-center" id="monthScatterChart" style="height:700px;">

    </div>
    <br/>

</div>

<script type="text/javascript">
    var list =${analysisIndexList};
    prepareDataAndDraw('dateScatterChart', '昨日注册&活跃用户分布', list[0])
    prepareDataAndDraw('monthScatterChart', '当月注册&活跃用户分布', list[1])
    function prepareDataAndDraw(divId, chartName, list) {
        var legendData = [];
        legendData.push('注册&活跃');
        var tooltipFormatter = function (params) {
            if (params.value.length > 1) {
                return '渠道：' + params.value[2] + '<br/>' + '注册用户：' + params.value[0] + '<br/>' + '活跃用户：' + params.value[1];
                ;
            }
        }
        var xAxisName = '注册用户';
        var yAxisName = '活跃用户';
        var data = [];
        var minAndMax = [0, 0, 0, 0];
        for (var i = 0; i < list.length; i++) {
            var temp = [];
            temp.push(list[i].registerNum);
            if (list[i].registerNum > minAndMax[1]) {
                minAndMax[1] = list[i].registerNum;
            }
            temp.push(list[i].activeNum);
            if (list[i].activeNum > minAndMax[3]) {
                minAndMax[3] = list[i].activeNum;
            }
            temp.push(list[i].indexSource);
            data.push(temp);
        }
        var registerNumStep = (minAndMax[1] - minAndMax[0]) / 51;
        var activeNumStep = (minAndMax[3] - minAndMax[2]) / 51;
        var series = [{
            name: '注册&活跃',
            data: data,
            type: 'scatter',
            symbolSize: function (data) {
                return Math.round(((data[0] - minAndMax[0]) / registerNumStep + (data[1] - minAndMax[2]) / activeNumStep + 10));
            },
            itemStyle: {
                normal: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(120, 36, 50, 0.5)',
                    shadowOffsetY: 5,
                    color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                        offset: 0,
                        color: 'rgb(251, 118, 123)'
                    }, {
                        offset: 1,
                        color: 'rgb(204, 46, 72)'
                    }])
                }
            }
        }]

        drawScatterChart(divId, chartName, legendData, tooltipFormatter, xAxisName, yAxisName, series)
    }
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>

</body>
</html>