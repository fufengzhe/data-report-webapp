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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-table.css"
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
            <button class="btn btn-success" data-toggle="modal" data-target="#myModal">表格视图
            </button>
        </div>
        <div class='col-sm-2'></div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            用户中心请求分布
        </div>
    </div>

    <div class="row">
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfFun">无数据，请更改查询条件或联系开发人员。</div>
            <div id="funPieChart" style="height:700px"> </div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfReturn">无数据，请更改查询条件或联系开发人员。</div>
            <div id="returnPieChart" style="height:700px"> </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            活跃用户数小时维度分布
        </div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataOfActiveHour">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="activeHourBarChart" style="height:800px;">
    </div>
    <%--模态框--%>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="funPieTable"></table>
                    <table id="returnPieTable"></table>
                    <table id="hourBarTable"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
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
        src="${pageContext.request.contextPath}/static/js/bootstrap-table.js"></script>
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
    if(list[1].length==0){
        $("#noDataOfFun").css('display', 'block');
    }else{
        pieChart(list[1], "funPieChart", "用户中心服务请求分布");
    }
    if(list[2].length==0){
        $("#noDataOfReturn").css('display', 'block');
    }else{
        pieChart(list[2], "returnPieChart", "用户中心返回情况分布");
    }
    function pieChart(data, divId, chartName) {
        var legendData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].distributeName);
            seriesData.push({value: data[i].indexValue, name: data[i].distributeName});
        }
        drawPieChart(divId, chartName, legendData, seriesData);
    }
    if(list[0].length==0){
        $("#noDataOfActiveHour").css('display', 'block');
    }else{
        barChart(list[0], "activeHourBarChart", "活跃用户数小时维度分布", "小时", "数量");
    }
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
                            if (list[1].length == 0) {
                                $("#noDataOfFun").css('display', 'block');
                                echarts.init(document.getElementById('funPieChart')).clear();
                                $("#funPieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfFun").css('display', 'none');
                                pieChart(list[1], "funPieChart", "用户中心服务请求分布");
                                $("#funPieTable").bootstrapTable('load', list[1]);
                            }
                            if (list[2].length == 0) {
                                $("#noDataOfReturn").css('display', 'block');
                                echarts.init(document.getElementById('returnPieChart')).clear();
                                $("#returnPieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfReturn").css('display', 'none');
                                pieChart(list[2], "returnPieChart", "用户中心返回情况分布");
                                $("#returnPieTable").bootstrapTable('load', list[2]);
                            }
                            if (list[0].length == 0) {
                                $("#noDataOfActiveHour").css('display', 'block');
                                echarts.init(document.getElementById('activeHourBarChart')).clear();
                                $("#hourBarTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfActiveHour").css('display', 'none');
                                barChart(list[0], "activeHourBarChart", "活跃用户数小时维度分布");
                                $("#hourBarTable").bootstrapTable('load', list[0]);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    generateDataTable("funPieTable", [[{"field": "distributeName"}, {"field": "indexValue"}], [{"title": "接口名"}, {"title": "请求量"}]])
    $("#funPieTable").bootstrapTable('load', list[1]);
    generateDataTable("returnPieTable", [[{"field": "distributeName"}, {"field": "indexValue"}], [{"title": "返回类型"}, {"title": "请求量"}]])
    $("#returnPieTable").bootstrapTable('load', list[2]);
    generateDataTable("hourBarTable", [[{"field": "distributeName"}, {"field": "indexValue"}], [{"title": "小时"}, {"title": "活跃用户数"}]])
    $("#hourBarTable").bootstrapTable('load', list[0]);
</script>

</body>
</html>