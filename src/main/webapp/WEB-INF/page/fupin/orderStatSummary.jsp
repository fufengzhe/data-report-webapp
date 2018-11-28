<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>扶贫商品订单查询</title>
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
    <div class="container">
        <h3>数据概览(线下订单核对中，数据仅供参考)</h3>
        <p><h5>截止今天凌晨2018年<abbr title="只计算四县及非丹泉水订单">扶贫订单</abbr>数为<kbd id="sumOrderNum">xx</kbd>件，销售额为<kbd
            id="sumOrderAmount">xx</kbd>万元；内蒙扶贫订单数为<kbd id="neiMengOrderNum">xx</kbd>件，销售额为<kbd
            id="neiMengOrderAmount">xx</kbd>万元；国寿丹泉水订单数为<kbd id="danQuanOrderNum">xx</kbd>件，销售额为<kbd
            id="danQuanOrderAmount">xx</kbd>万元。
        <%--全年销售目标达成率为<kbd id="completeRatio">xx.xx%</kbd>。--%>
    </h5></p>
    </div>

    <div class="row panel-heading">
        <div class='col-sm-4'></div>
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
        <div class='col-sm-1'>
            <div class="form-group">
                <div class='input-group text-center'>
                        <span class="select-group-btn">
                            <button type="button" class="btn btn-primary" id="queryDate">开始查询</button>
                        </span>
                </div>
            </div>
        </div>
        <div class='col-sm-4'>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            四县销售明细&nbsp&nbsp
            <button class="btn btn-success" data-toggle="modal" data-target="#datePieModal">表格视图
            </button>
        </div>
    </div>

    <div class="row">
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
            各单位销售明细&nbsp&nbsp
            <button class="btn btn-success" data-toggle="modal" data-target="#dateCompanyPieModal">表格视图
            </button>
        </div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataOfDateTrend">无数据，请更改查询条件或联系开发人员。</div>
    <div class="row">
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfDateCompanyOrderNumPie">
                无数据，请更改查询条件或联系开发人员。
            </div>
            <div id="dateCompanyOrderNumPieChart" style="height:700px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfDateCompanyOrderAmountPie">
                无数据，请更改查询条件或联系开发人员。
            </div>
            <div id="dateCompanyOrderAmountPieChart" style="height:700px"></div>
        </div>
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
    <div class="modal fade" id="dateCompanyPieModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" style="width:900px">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格（总计之后的记录为非人寿记录）</h4>
                </div>
                <div class="modal-body">
                    <table id="dateCompanyPieTable"></table>
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
    var dateRange = '${startDate}' + " ~ " + '${endDate}';
    var listForTable = new Array();
    $("#dateRange").val(dateRange);
    if (list[0].length == 0) {
        $("#sumOrderNum").text("无数据");
        $("#sumOrderAmount").text("无数据");
        $("#neiMengOrderNum").text("无数据");
        $("#neiMengOrderAmount").text("无数据");
        $("#danQuanOrderNum").text("无数据");
        $("#danQuanOrderAmount").text("无数据");
//        $("#completeRatio").text("无数据");
        $("#noDataOfDateOrderNumPie").css('display', 'block');
        $("#noDataOfDateOrderAmountPie").css('display', 'block');
    } else {
        areaList = list[0];
        $("#sumOrderNum").text(areaList[0].orderNum);
        $("#sumOrderAmount").text(areaList[0].orderAmount);
        $("#neiMengOrderNum").text(areaList[areaList.length - 2].orderNum);
        $("#neiMengOrderAmount").text(areaList[areaList.length - 2].orderAmount);
        $("#danQuanOrderNum").text(areaList[areaList.length - 1].orderNum);
        $("#danQuanOrderAmount").text(areaList[areaList.length - 1].orderAmount);
//        $("#completeRatio").text(areaList[0].completeRatio);
        pieChart(list[0], "dateOrderNumPieChart", "dateOrderAmountPieChart", "订单数（件）分布", "订单金额（万）分布");
    }

    if (list[1].length == 0) {
        $("#noDataOfDateCompanyOrderNumPie").css('display', 'block');
        $("#noDataOfDateCompanyOrderAmountPie").css('display', 'block');
    } else {
        pieChartForCompany(list[1], "dateCompanyOrderNumPieChart", "dateCompanyOrderAmountPieChart", "订单数（件）分布", "订单金额（万）分布");
    }
    function pieChart(data, divId1, divId2, chartName1, chartName2) {
        var legendData = [];
        var seriesData1 = [];
        var seriesData2 = [];
        for (var i = 1; i < data.length - 2; i++) {
            legendData.push(data[i].area);
            window.listForTable.push(data[i]);
            seriesData1.push({value: data[i].orderNum, name: data[i].area});
            seriesData2.push({value: data[i].orderAmount, name: data[i].area});
        }
        window.listForTable.push(data[0]);
        drawPieChart(divId1, chartName1, legendData, seriesData1, "单量（件）及占比");
        drawPieChart(divId2, chartName2, legendData, seriesData2, "金额（万）及占比");
    }
    function pieChartForCompany(data, divId1, divId2, chartName1, chartName2) {
        var legendData = [];
        var seriesData1 = [];
        var seriesData2 = [];
        for (var i = 0; i < data.length - 1; i++) {
            if ("总计" == data[i].company) {
                break;
            }
            legendData.push(data[i].company);
            seriesData1.push({value: data[i].orderNum, name: data[i].company});
            seriesData2.push({value: data[i].orderAmount, name: data[i].company});
        }
        drawPieChart(divId1, chartName1, legendData, seriesData1, "单量（件）及占比");
        drawPieChart(divId2, chartName2, legendData, seriesData2, "金额（万）及占比");
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
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list[0].length == 0) {
                                $("#noDataOfDateOrderNumPie").css('display', 'block');
                                $("#noDataOfDateOrderAmountPie").css('display', 'block');
                                echarts.init(document.getElementById('dateOrderNumPieChart')).clear();
                                echarts.init(document.getElementById('dateOrderAmountPieChart')).clear();
                                $("#datePieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfDateOrderNumPie").css('display', 'none');
                                $("#noDataOfDateOrderAmountPie").css('display', 'none');
                                window.listForTable.length = 0;
                                pieChart(list[0], "dateOrderNumPieChart", "dateOrderAmountPieChart", "订单数（件）分布", "订单金额（万）分布");
                                $("#datePieTable").bootstrapTable('load', window.listForTable);
                            }

                            if (list[1].length == 0) {
                                $("#noDataOfDateCompanyOrderNumPie").css('display', 'block');
                                $("#noDataOfDateCompanyOrderAmountPie").css('display', 'block');
                                echarts.init(document.getElementById('dateCompanyOrderNumPieChart')).clear();
                                echarts.init(document.getElementById('dateCompanyOrderAmountPieChart')).clear();
                                $("#dateCompanyPieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfDateCompanyOrderNumPie").css('display', 'none');
                                $("#noDataOfDateCompanyOrderAmountPie").css('display', 'none');
                                pieChartForCompany(list[1], "dateCompanyOrderNumPieChart", "dateCompanyOrderAmountPieChart", "订单数（件）分布", "订单金额（万）分布");
                                $("#dateCompanyPieTable").bootstrapTable('load', list[1]);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    generateDataTable("datePieTable", [[{"field": "area"}, {"field": "orderNum"}, {"field": "orderAmount"}],
        [{"title": "地区"}, {"title": "订单数（件）"}, {"title": "订单总金额（万）"}]])
    $("#datePieTable").bootstrapTable('load', window.listForTable);
    generateDataTable("dateCompanyPieTable", [[{"field": "company"}, {"field": "orderNum"}, {"field": "orderAmount"}, {"field": "orderAmountGoal"}, {"field": "completeRatio"}],
        [{"title": "单位名称"}, {"title": "订单数（件）"}, {"title": "订单总金额（万）"}, {"title": "任务额（万）"}, {"title": "任务达成率"}]])
    $("#dateCompanyPieTable").bootstrapTable('load', list[1]);
</script>

</body>
</html>