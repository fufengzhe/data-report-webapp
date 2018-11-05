<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>扶贫商品订单数查询</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-select.min.css"
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
            扶贫商品订单数查询(饼图只展示订单数top10订单，所有订单相关信息见表格)
        </div>
    </div>

    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-2'></div>
            <div class='col-sm-3'>
                <div class="form-group">
                    <div class='input-group date text-center'>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        <input type="text" class="form-control" placeholder="时间范围" id="dateRange"/>
                    </div>
                </div>
            </div>
            <div class='col-sm-2'>
                <div class="form-group">
                    <div class='select-group text-center'>
                        <select id="productFilter" class="selectpicker" data-live-search="true" multiple>
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
            <div class='col-sm-2'>
                <button class="btn btn-success" data-toggle="modal" data-target="#datePieModal">表格视图
                </button>
            </div>
            <div class='col-sm-2'>
            </div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfDateOrderNumPie">无数据，请更改查询条件或联系开发人员。
            </div>
            <div id="dateOrderNumPieChart" style="height:700px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfDateOrderAmountPie">无数据，请更改查询条件或联系开发人员。
            </div>
            <div id="dateOrderAmountPieChart" style="height:700px"></div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            过去七天扶贫商品订单数趋势&nbsp&nbsp
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
        src="${pageContext.request.contextPath}/static/js/bootstrap-select.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bootstrap-table.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/utils/commonUtils.js?ver=${jsVersion}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/date_compare.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/daterangepicker.js"></script>
<script>
    var list =${orderStatList};
    var productList =${orderProductList};
    var dateRange = '${startDate}' + " ~ " + '${endDate}';
    $("#dateRange").val(dateRange);
    if (list[0].length == 0) {
        $("#noDataOfDateOrderNumPie").css('display', 'block');
        $("#noDataOfDateOrderAmountPie").css('display', 'block');
    } else {
        pieChart(list[0], "dateOrderNumPieChart", "dateOrderAmountPieChart", "订单数分布", "订单金额分布");
    }
    var productFilterDom = $("#productFilter");
    productFilterDom.selectpicker({
        noneSelectedText: '商品选择'
    });
    for (var i = 0; i < productList.length; i++) {
        productFilterDom.append("<option value=" + productList[i].productId + ">" + productList[i].productName + "</option>");
    }
    productFilterDom.selectpicker('refresh');
    function pieChart(data, divId1, divId2, chartName1, chartName2) {
        var legendData = [];
        var seriesData1 = [];
        var seriesData2 = [];
        var maxLoop = 10 < data.length ? 10 : data.length;
        for (var i = 0; i < maxLoop; i++) {
            legendData.push(data[i].productName);
            seriesData1.push({value: data[i].orderNum, name: data[i].productName});
            seriesData2.push({value: data[i].orderAmount, name: data[i].productName});
        }
        drawPieChart(divId1, chartName1, legendData, seriesData1, "单量及占比");
        drawPieChart(divId2, chartName2, legendData, seriesData2, "单量及占比");
    }
    var dateStrs =${dates};
    if (list[1].length == 0) {
        $("#noDataOfDateTrend").css('display', 'block');
    } else {
        trendChart(list[1], dateStrs, "dateTrendChart", "过去七天扶贫商品订单数趋势");
    }
    function trendChart(data, dateStrs, divId, chartName) {
        var legendData = [];
        var xData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].productName);
            seriesData.push({
                name: data[i].productName, type: 'line', areaStyle: {normal: {}},
                data: [data[i].orderNum7, data[i].orderNum6, data[i].orderNum5, data[i].orderNum4, data[i].orderNum3,
                    data[i].orderNum2, data[i].orderNum1]
            });
        }
        for (var i = 0; i < dateStrs.length; i++) {
            xData.push(dateStrs[i]);
        }
        drawTrendChart(divId, chartName, legendData, xData, seriesData, "", "商品订单数");
    }

    $(function () {
        $("#queryDate").click(function () {
            var dateRange = $("#dateRange").val();
            var startDate = dateRange.substr(0, 10);
            var endDate = dateRange.substr(13);
            var productFilter = $('#productFilter').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'numQuery',
                    data: {"startDate": startDate, "endDate": endDate, "whereCondition": productFilter,},
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list.length == 0) {
                                $("#noDataOfDateOrderNumPie").css('display', 'block');
                                $("#noDataOfDateOrderAmountPie").css('display', 'block');
                                echarts.init(document.getElementById('dateOrderNumPieChart')).clear();
                                echarts.init(document.getElementById('dateOrderAmountPieChart')).clear();
                                $("#datePieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfDateOrderNumPie").css('display', 'none');
                                $("#noDataOfDateOrderAmountPie").css('display', 'none');
                                pieChart(list, "dateOrderNumPieChart", "dateOrderAmountPieChart", "订单数分布", "订单金额分布");
                                $("#datePieTable").bootstrapTable('load', list);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    generateDataTable("datePieTable", [[{"field": "productName"}, {"field": "orderNum"}, {"field": "goodsNum"}, {"field": "orderAmount"}, {"field": "orderAverage"}],
        [{"title": "商品名称"}, {"title": "订单数"}, {"title": "商品数量"}, {"title": "订单总金额"}, {"title": "客单价"}]])
    $("#datePieTable").bootstrapTable('load', list[0]);
    generateDataTable("dateTrendTable", [[{"field": "productName"}, {"field": "orderNum7"}, {"field": "orderNum6"}, {"field": "orderNum5"},
        {"field": "orderNum4"}, {"field": "orderNum3"}, {"field": "orderNum2"}, {"field": "orderNum1"}],
        [{"title": "页面名称"}, {"title": dateStrs[0]}, {"title": dateStrs[1]}, {"title": dateStrs[2]}, {"title": dateStrs[3]}, {"title": dateStrs[4]}, {"title": dateStrs[5]}, {"title": dateStrs[6]}]])
    $("#dateTrendTable").bootstrapTable('load', list[1]);
</script>

</body>
</html>