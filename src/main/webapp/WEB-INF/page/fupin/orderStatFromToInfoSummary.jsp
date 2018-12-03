<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>扶贫商品订单下单及收货地域分析</title>
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
            扶贫商品订单下单及收货地域分析查询（xx>yy表示xx地域下单，yy地域收件；独立的小红点表示该地域所有订单的下单和收件地域都相同）
        </div>
    </div>
    <div class="row panel-heading">
        <div class='col-sm-1'></div>
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
                    <select id="source" class="selectpicker" data-live-search="true" multiple>
                    </select>
                </div>
            </div>
        </div>
        <div class='col-sm-2'>
            <div class="form-group">
                <div class='select-group text-center'>
                    <select id="target" class="selectpicker" data-live-search="true" multiple>
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
            <button class="btn btn-success" data-toggle="modal" data-target="#graphModal">表格视图
            </button>
        </div>
        <div class='col-sm-1'></div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noData">无数据，请更改查询条件或联系开发人员。</div>
    <div class="alert alert-warning" style="display:none;" id="dataHint">地域太多，不予展示，请选择特定的下单地域和收货地域查询（支持多选）。</div>
    <div class="container-fluid text-center" id="migrateGraphChart" style="height:800px;">
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            物流快递分布
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
                            <button type="button" class="btn btn-primary" id="queryDateExpress">开始查询</button>
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
    <div class="alert alert-warning" style="display:none;" id="noDataExpress">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="expressPieChart" style="height:800px;">
    </div>

    <%--模态框--%>
    <div class="modal fade" id="graphModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="graphTable"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
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
    var fromList =${fromList};
    var toList =${toList};
    var fromDom = $("#source");
    var toDom = $("#target");
    fromDom.selectpicker({
        noneSelectedText: '下单地域'
    });
    toDom.selectpicker({
        noneSelectedText: '收货地域'
    });
    for (var i = 0; i < fromList.length; i++) {
        fromDom.append("<option value=" + fromList[i].source + ">" + fromList[i].source + "</option>");
    }
    for (var i = 0; i < toList.length; i++) {
        toDom.append("<option value=" + toList[i].target + ">" + toList[i].target + "</option>");
    }
    fromDom.selectpicker('refresh');
    toDom.selectpicker('refresh');
    if (list[0].length == 0 && list[1].length == 0) {
        $("#noData").css('display', 'block');
    } else {
        graphChart(list, "migrateGraphChart", "扶贫商品订单下单及收货地域分析");
    }
    if (list[2].length == 0) {
        $("#noDataExpress").css('display', 'block');
    } else {
        pieChart(list[2], "expressPieChart", "扶贫商品订单物流快递分布");
    }
    function graphChart(data, divId, chartName) {
        if (data[0].length > 13) {
            $("#dataHint").css('display', 'block');
            echarts.init(document.getElementById('migrateGraphChart')).clear();
            return;
        }
        var formatterFunction = function (params) {
            return params.data.name
                + (params.data.fromIndexValue == undefined ? ('<br/> 下单订单数: ' + params.data.indexValue) : ('<br/>下单地域订单数: ' + params.data.fromIndexValue)) +
                (params.data.toIndexValue == undefined ? '' : ('<br/>收件地域订单数: ' + params.data.toIndexValue));
        }
        var seriesData = [];
        var linksData = [];
        for (var i = 0; i < data[1].length; i++) {
//            var x = i + 1;
//            var y = (i % 2 == 0 ? 1 : 2);
            seriesData.push({
                name: data[1][i].source,
//                x: x, y: y,
                fromIndexValue: data[1][i].indexValue, toIndexValue: data[1][i].orderNum
            });
        }
        for (var i = 0; i < data[0].length; i++) {
            linksData.push({
                name: data[0][i].source + '->' + data[0][i].target,
                indexValue: data[0][i].indexValue,
                source: data[0][i].source,
                target: data[0][i].target,
                label: {
                    normal: {
                        show: true
                    }
                },
                lineStyle: {
                    normal: {
                        curveness: 0.2
                    }
                }
            });
        }
        drawGraphChart(divId, chartName, formatterFunction, seriesData, linksData);
    }

    function pieChart(data, divId, chartName) {
        var legendData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].company);
            seriesData.push({value: data[i].orderNum, name: data[i].company});
        }
        drawPieChart(divId, chartName, legendData, seriesData);
    }
    $(function () {
        $("#queryDate").click(function () {
            var dateRange = $("#dateRange").val();
            var startDate = dateRange.substr(0, 10);
            var endDate = dateRange.substr(13);
            var source = $('#source').selectpicker('val');
            var target = $('#target').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'fromToInfoNumQuery',
                    data: {
                        "startDate": startDate,
                        "endDate": endDate,
                        "whereCondition": source,
                        "whereCondition1": target,
                    },
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list[0].length == 0 && list[1].length == 0) {
                                $("#dataHint").css('display', 'none');
                                $("#noData").css('display', 'block');
                                echarts.init(document.getElementById('migrateGraphChart')).clear();
                                $("#graphTable").bootstrapTable('load', []);
                            } else {
                                $("#dataHint").css('display', 'none');
                                $("#noData").css('display', 'none');
                                graphChart(list, "migrateGraphChart", "扶贫商品订单下单及收货地域分析");
                                $("#graphTable").bootstrapTable('load', list[1]);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });

        $("#queryDateExpress").click(function () {
            var dateRange = $("#dateRangeExpress").val();
            var startDate = dateRange.substr(0, 10);
            var endDate = dateRange.substr(13);
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDateExpress', true);
                $.ajax({
                    url: 'expressNumQuery',
                    data: {
                        "startDate": startDate,
                        "endDate": endDate,
                    },
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list.length == 0) {
                                $("#noDataExpress").css('display', 'block');
                                echarts.init(document.getElementById('expressPieChart')).clear();
                                $("#pieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataExpress").css('display', 'none');
                                pieChart(list, "expressPieChart", "扶贫商品订单物流快递分布");
                                $("#pieTable").bootstrapTable('load', list);
                            }
                        }
                        setButtonDisabled('queryDateExpress', false);
                    }
                });
            }
        });
    });

    generateDataTable("graphTable", [[{"field": "source"}, {"field": "indexValue"}, {"field": "orderNum"}],
        [{"title": "地域"}, {"title": "该地域下单订单数数"}, {"title": "该地域收件订单数"}]])
    $("#graphTable").bootstrapTable('load', list[1]);
    generateDataTable("pieTable", [[{"field": "company"}, {"field": "orderNum"}],
        [{"title": "快递公司名称"}, {"title": "订单量"}]])
    $("#pieTable").bootstrapTable('load', list[2]);
</script>

</body>
</html>