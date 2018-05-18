<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>活跃用户时间段及用户中心请求分布</title>
    <style type="text/css">
        html {
            height: 100%
        }

        body {
            height: 100%;
            margin: 0px;
            padding: 0px
        }

        #container {
            height: 100%
        }
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-select.min.css"
          type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/utils/drawChart.js?ver=${jsVersion}"></script>
</head>

<body>

<div class="container-fluid text-center">
    <br/>
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
    <div class="panel panel-default">
        <div class="panel-heading">
            用户中心请求分布
        </div>
    </div>

    <div class="row">
        <div class="text-center col-md-6" id="funPieChart" style="height:700px">
        </div>
        <div class="text-center col-md-6" id="returnPieChart" style="height:700px">
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            活跃用户数小时维度分布
        </div>
    </div>
    <div class="container-fluid text-center" id="activeHourBarChart" style="height:800px;">
    </div>
</div>

<br/>
<div id="main" style="width: 600px;height:400px;"></div>
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
<%--<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZjFuwSnrpwicUzxIxguxFRQEXbWiwxjO"></script>--%>

<script type="text/javascript">
    var list =${analysisIndexList};
    var userSourceList =${userSourceList};
    $("#startDate").val('${startDate}');
    $("#endDate").val('${endDate}');
    var userSourceDom = $("#userSource");
    userSourceDom.selectpicker({
        noneSelectedText: '全部渠道'
    });
    for (var i = 0; i < userSourceList.length; i++) {
        userSourceDom.append("<option value=" + userSourceList[i].indexSource + ">" + userSourceList[i].indexSource + "</option>");
    }
    userSourceDom.selectpicker('refresh');
    pieChart(list[1], "funPieChart", "用户中心服务请求分布");
    pieChart(list[2], "returnPieChart", "用户中心返回情况分布");
    function pieChart(data, divId, chartName) {
        var legendData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].distributeName);
            seriesData.push({value: data[i].indexValue, name: data[i].distributeName});
        }
        drawPieChart(divId, chartName, legendData, seriesData);
    }

    barChart(list[0], "activeHourBarChart", "活跃用户数小时维度分布", "小时", "数量");
    function barChart(data, divId, title, xName, yName) {
        var xAixsData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            xAixsData.push(data[i].distributeName);
            seriesData.push(data[i].indexValue);
        }
        drawBarChart(divId, title, xName, xAixsData, yName, seriesData);
    }
    $(function () {
        $("#queryDate").click(function () {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var userSource = $('#userSource').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'activeHourAndUserCollDisNumQuery',
                    data: {"startDate": startDate, "endDate": endDate, "userSource": userSource},
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            pieChart(list[1], "funPieChart", "用户中心服务请求分布");
                            pieChart(list[2], "returnPieChart", "用户中心返回情况分布");
                            barChart(list[0], "activeHourBarChart", "活跃用户数小时维度分布");
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