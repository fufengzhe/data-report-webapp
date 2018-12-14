<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>扶贫商品订单评价查询</title>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/daterangepicker-bs3.css"
          type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/utils/drawChart.js?ver=${jsVersion}"></script>
</head>
<body>
<div class="container-fluid text-center">
    <br/>
    <div class="panel panel-default">
        <div class="panel-heading">
            扶贫商品订单评价详情查询
        </div>
    </div>
    <div class="row panel-heading">
        <div class='col-sm-3'></div>
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
                    <select id="evaluateValue" class="selectpicker" data-live-search="true" multiple>
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
        <div class='col-sm-3'></div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noData">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" >
        <table id="evaluateDetailTable"></table>
    </div>
    <br/>
    <div class="panel panel-default">
        <div class="panel-heading">
            扶贫商品订单评价分值分布
        </div>
    </div>
    <div class="row panel-heading">
        <div class='col-sm-3'></div>
        <div class='col-sm-3'>
            <div class="form-group">
                <div class='input-group date text-center'>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    <input type="text" class="form-control" placeholder="时间范围" id="dateRangeExpress"/>
                </div>
            </div>
        </div>
        <div class='col-sm-1'>
            <div class="form-group">
                <div class='input-group text-center'>
                        <span class="select-group-btn">
                            <button type="button" class="btn btn-primary" id="queryDateValue">开始查询</button>
                        </span>
                </div>
            </div>
        </div>
        <div class='col-sm-2'>
            <button class="btn btn-success" data-toggle="modal" data-target="#pieModal">表格视图
            </button>
        </div>
        <div class='col-sm-3'></div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataValue">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="valuePieChart" style="height:600px;">
    </div>

    <%--模态框--%>
    <div class="modal fade" id="pieModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="pieTable"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</div>
<br/>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/moment.js?ver=${jsVersion}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/date_compare.js?ver=${jsVersion}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/daterangepicker.js?ver=${jsVersion}"></script>
<%--<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZjFuwSnrpwicUzxIxguxFRQEXbWiwxjO"></script>--%>
<script type="text/javascript">
    var list =${orderStatList};
    var dateRange = '${startDate}' + " ~ " + '${endDate}';
    $("#dateRange").val(dateRange);
    $("#dateRangeExpress").val(dateRange);
    var evaluateValueList =${evaluateValueList};
    var evaluateValueDom = $("#evaluateValue");
    evaluateValueDom.selectpicker({
        noneSelectedText: '评价值'
    });
    for (var i = 0; i < evaluateValueList.length; i++) {
        evaluateValueDom.append("<option value=" + evaluateValueList[i] + ">" + evaluateValueList[i] + "</option>");
    }
    evaluateValueDom.selectpicker('refresh');

    if (list[0].length == 0) {
        $("#noData").css('display', 'block');
    } else {
        generateDataTable("evaluateDetailTable", [[{"field": "orderNo"}, {"field": "productName"}, {"field": "target"}, {"field": "indexValue"}, {"field": "indexName"}],
            [{"title": "订单号"}, {"title": "商品名称"}, {"title": "规格"}, {"title": "评价分值"}, {"title": "评价内容"}]])
        $("#evaluateDetailTable").bootstrapTable('load', list[0]);
    }
    if (list[1].length == 0) {
        $("#noDataValue").css('display', 'block');
    } else {
        pieChart(list[1], "valuePieChart", "扶贫商品订单评价分值分布");
    }

    function pieChart(data, divId, chartName) {
        var legendData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].indexValue);
            seriesData.push({value: data[i].orderNum, name: data[i].indexValue});
        }
        drawPieChart(divId, chartName, legendData, seriesData);
    }
    $(function () {
        $("#queryDate").click(function () {
            var dateRange = $("#dateRange").val();
            var startDate = dateRange.substr(0, 10);
            var endDate = dateRange.substr(13);
            var evaluateValue = $('#evaluateValue').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'evaluateNumQuery',
                    data: {
                        "startDate": startDate,
                        "endDate": endDate,
                        "whereCondition1": evaluateValue,
                    },
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list[0].length == 0) {
                                $("#noData").css('display', 'block');
                                $("#evaluateDetailTable").bootstrapTable('load', []);
                            } else {
                                $("#noData").css('display', 'none');
                                $("#evaluateDetailTable").bootstrapTable('load', list[0]);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });

        $("#queryDateValue").click(function () {
            var dateRange = $("#dateRangeExpress").val();
            var startDate = dateRange.substr(0, 10);
            var endDate = dateRange.substr(13);
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDateValue', true);
                $.ajax({
                    url: 'evaluateNumQuery',
                    data: {
                        "startDate": startDate,
                        "endDate": endDate,
                    },
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list[1].length == 0) {
                                $("#noDataValue").css('display', 'block');
                                echarts.init(document.getElementById('valuePieChart')).clear();
                                $("#pieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataValue").css('display', 'none');
                                pieChart(list[1], "valuePieChart", "扶贫商品订单评价分值分布");
                                $("#pieTable").bootstrapTable('load', list[1]);
                            }
                        }
                        setButtonDisabled('queryDateValue', false);
                    }
                });
            }
        });
    });

    generateDataTable("pieTable", [[{"field": "indexValue"}, {"field": "orderNum"}],
        [{"title": "评价分值"}, {"title": "评价数量"}]])
    $("#pieTable").bootstrapTable('load', list[1]);
</script>

</body>
</html>