<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" href="${pageContext.request.contextPath}/static/img/sale_ico.ico" type="img/x-ico"/>
    <title>保费保额查询分析</title>
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
            保费保额分布
        </div>
    </div>

    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-3'></div>
            <div class='col-sm-4'>
                <div class="form-group">
                    <div class='input-group date text-center'>
                        <span class="input-group-addon">投保时间</span>
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
                <button class="btn btn-success" data-toggle="modal" data-target="#productPieModal">表格视图
                </button>
            </div>
            <div class='col-sm-3'>
            </div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfPremiumPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="premiumPieChart" style="height:600px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfAmountPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="amountPieChart" style="height:600px"></div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            过去七天保费趋势&nbsp&nbsp
            <button class="btn btn-success" data-toggle="modal" data-target="#dateTrendModal">表格视图
            </button>
        </div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataOfDateTrend">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="dateTrendChart" style="height:700px;">
    </div>
    <%--模态框--%>
    <div class="modal fade" id="productPieModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="productPieTable"></table>
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
    var list =${saleOrderList};
    var dateRange = '${startDate}' + " ~ " + '${endDate}';
    $("#dateRange").val(dateRange);
    if (list[0].length == 0) {
        $("#noDataOfPremiumPie").css('display', 'block');
        $("#noDataOfAmountPie").css('display', 'block');
    } else {
        pieChart(list[0], "premiumPieChart", "amountPieChart", "保费分布", "保额分布");
    }
    function pieChart(data, divId1, divId2, chartName1, chartName2) {
        var legendData = [];
        var seriesData1 = [];
        var seriesData2 = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].pageName);
            seriesData1.push({value: data[i].totalPremium, name: data[i].productName});
            seriesData2.push({value: data[i].totalAmount, name: data[i].productName});
        }
        drawPieChart(divId1, chartName1, legendData, seriesData1);
        drawPieChart(divId2, chartName2, legendData, seriesData2);
    }
    var dateStrs =${dates};
    if (list[1].length == 0) {
        $("#noDataOfDateTrend").css('display', 'block');
    } else {
        trendChart(list[1], dateStrs, "dateTrendChart", "过去七天保费趋势");
    }
    function trendChart(data, dateStrs, divId, chartName) {
        var legendData = [];
        var xData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].productName);
            seriesData.push({
                name: data[i].productName, type: 'line', areaStyle: {normal: {}},
                data: [data[i].totalPremium7, data[i].totalPremium6, data[i].totalPremium5, data[i].totalPremium4, data[i].totalPremium3, data[i].totalPremium2, data[i].totalPremium1]
            });
        }
        for (var i = 0; i < dateStrs.length; i++) {
            xData.push(dateStrs[i]);
        }
        drawTrendChart(divId, chartName, legendData, xData, seriesData, "", "保费");
    }

    $(function () {
        $("#queryDate").click(function () {
            var dateRange = $("#dateRange").val();
            var startDate = dateRange.substr(0, 10);
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
                                $("#noDataOfPremiumPie").css('display', 'block');
                                $("#noDataOfAmountPie").css('display', 'block');
                                echarts.init(document.getElementById('premiumPieChart')).clear();
                                echarts.init(document.getElementById('amountPieChart')).clear();
                                $("#productPieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfPremiumPie").css('display', 'none');
                                $("#noDataOfAmountPie").css('display', 'none');
                                pieChart(list, "premiumPieChart", "amountPieChart", "保费分布", "保额分布");
                                $("#productPieTable").bootstrapTable('load', list);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    generateDataTable("productPieTable", [[{"field": "productName"}, {"field": "totalPremium"}, {"field": "totalAmount"}],
        [{"title": "产品名称"}, {"title": "保费"}, {"title": "保额"}]])
    $("#productPieTable").bootstrapTable('load', list[0]);
    generateDataTable("dateTrendTable", [[{"field": "productName"}, {"field": "totalPremium7"}, {"field": "totalPremium6"}, {"field": "totalPremium5"},
        {"field": "totalPremium4"}, {"field": "totalPremium3"}, {"field": "totalPremium2"}, {"field": "totalPremium1"}],
        [{"title": "产品名称"}, {"title": dateStrs[0]}, {"title": dateStrs[1]}, {"title": dateStrs[2]}, {"title": dateStrs[3]}, {"title": dateStrs[4]}, {"title": dateStrs[5]}, {"title": dateStrs[6]}]])
    $("#dateTrendTable").bootstrapTable('load', list[1]);
</script>

</body>
</html>