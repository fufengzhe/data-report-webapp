<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户迁徙分布</title>
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
    <div class="panel panel-default">
        <div class="panel-heading">
            用户迁徙分布 (用户A在官网注册，之后在掌上国寿登录，则用户A从官网迁徙到掌上国寿)
        </div>
    </div>
    <div class="row panel-heading">
        <div class='col-sm-1'></div>
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
                    <select id="fromUserSource" class="selectpicker" data-live-search="true" multiple>
                    </select>
                </div>
            </div>
        </div>
        <div class='col-sm-2'>
            <div class="form-group">
                <div class='select-group text-center'>
                    <select id="toUserSource" class="selectpicker" data-live-search="true" multiple>
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
    <div class="alert alert-warning" style="display:none;" id="dataHint">渠道太多，不予展示，请选择特定的迁出渠道和迁入渠道查询（支持多选）。</div>
    <div class="container-fluid text-center" id="migrateGraphChart" style="height:900px;">
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            用户迁入渠道数分布 (用户A在官网注册，之后在掌上国寿和国寿i动登录，则用户A迁入渠道数为2)
        </div>
    </div>
    <div class="row panel-heading">
        <div class='col-sm-4'></div>
        <div class='col-sm-3'>
            <div class="form-group">
                <div class='input-group date text-center'>
                    <span class="input-group-addon">截止日期</span>
                    <input type='text' class="form-control" id="statDate" placeholder="选择时间"/>
                    <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    <span class="input-group-btn">
                            <button type="button" class="btn btn-primary" id="queryButton">开始查询</button>
                        </span>
                </div>
            </div>
        </div>
        <div class='col-sm-1'>
            <button class="btn btn-success" data-toggle="modal" data-target="#pieModal">表格视图
            </button>
        </div>
        <div class='col-sm-4'></div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataOfUserNum">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="migrateUserNumPieChart" style="height:600px;">
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
<%--<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZjFuwSnrpwicUzxIxguxFRQEXbWiwxjO"></script>--%>
<script type="text/javascript">
    var list =${analysisIndexList};
    var userSourceList =${userSourceList};
    $("#startDate").val('${startDate}');
    $("#endDate").val('${endDate}');
    $("#statDate").val('${startDate}');
    var fromUserSourceDom = $("#fromUserSource");
    var toUserSourceDom = $("#toUserSource");
    fromUserSourceDom.selectpicker({
        noneSelectedText: '迁出渠道'
    });
    toUserSourceDom.selectpicker({
        noneSelectedText: '迁入渠道'
    });
    for (var i = 0; i < userSourceList.length; i++) {
        fromUserSourceDom.append("<option value=" + userSourceList[i].indexSource + ">" + userSourceList[i].indexSource + "</option>");
        toUserSourceDom.append("<option value=" + userSourceList[i].indexSource + ">" + userSourceList[i].indexSource + "</option>");
    }
    fromUserSourceDom.selectpicker('refresh');
    toUserSourceDom.selectpicker('refresh');
    graphChart(list, "migrateGraphChart", "用户迁徙分布");
    pieChart(list[2], "migrateUserNumPieChart", "用户迁入渠道数分布");
    function graphChart(data, divId, chartName) {
        if (data[0].length > 14) {
            $("#dataHint").css('display', 'block');
            echarts.init(document.getElementById('migrateGraphChart')).clear();
            return;
        }
        var formatterFunction = function (params) {
            return params.data.name
                + (params.data.fromIndexValue == undefined ? ('<br/> 迁徙用户数: ' + params.data.indexValue) : ('<br/>迁出用户数: ' + params.data.fromIndexValue)) +
                (params.data.toIndexValue == undefined ? '' : ('<br/>迁入用户数: ' + params.data.toIndexValue));
        }
        var seriesData = [];
        var linksData = [];
        for (var i = 0; i < data[1].length; i++) {
//            var x = i + 1;
//            var y = (i % 2 == 0 ? 1 : 2);
            seriesData.push({
                name: data[1][i].indexSource,
//                x: x, y: y,
                fromIndexValue: data[1][i].fromIndexValue, toIndexValue: data[1][i].toIndexValue
            });
        }
        for (var i = 0; i < data[0].length; i++) {
            linksData.push({
                name: data[0][i].fromUserSource + '->' + data[0][i].toUserSource,
                indexValue: data[0][i].indexValue,
                source: data[0][i].fromUserSource,
                target: data[0][i].toUserSource,
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
            legendData.push(data[i].distributeName);
            seriesData.push({value: data[i].indexValue, name: data[i].distributeName});
        }
        drawPieChart(divId, chartName, legendData, seriesData);
    }
    $(function () {
        $("#queryDate").click(function () {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var fromUserSource = $('#fromUserSource').selectpicker('val');
            var toUserSource = $('#toUserSource').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'migrateCollectionDisNumQuery',
                    data: {
                        "startDate": startDate,
                        "endDate": endDate,
                        "fromUserSource": fromUserSource,
                        "toUserSource": toUserSource,
                        "queryType": "1"
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
                            } else {
                                $("#dataHint").css('display', 'none');
                                $("#noData").css('display', 'none');
                                graphChart(list, "migrateGraphChart", "用户迁徙分布");
                                $("#graphTable").bootstrapTable('load', list[1]);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });

    $(function () {
        $("#queryButton").click(function () {
            var startDate = $("#statDate").val();
            var endDate = $("#statDate").val();
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryButton', true);
                $.ajax({
                    url: 'migrateCollectionDisNumQuery',
                    data: {
                        "startDate": startDate,
                        "endDate": endDate,
                        "queryType": "2"
                    },
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list[0].length == 0) {
                                $("#noDataOfUserNum").css('display', 'block');
                                echarts.init(document.getElementById('migrateUserNumPieChart')).clear();
                            } else {
                                $("#noDataOfUserNum").css('display', 'none');
                                pieChart(list[0], "migrateUserNumPieChart", "用户迁入渠道数分布");
                                $("#pieTable").bootstrapTable('load', list[0]);
                            }
                        }
                        setButtonDisabled('queryButton', false);
                    }
                });
            }
        });
    });
    generateDataTable("graphTable", [[{"field": "indexSource"}, {"field": "fromIndexValue"}, {"field": "toIndexValue"}],
        [{"title": "渠道"}, {"title": "迁出用户数"}, {"title": "迁入用户数"}]])
    $("#graphTable").bootstrapTable('load', list[1]);
    generateDataTable("pieTable", [[{"field": "distributeName"}, {"field": "indexValue"}],
        [{"title": "迁入渠道数"}, {"title": "用户数"}]])
    $("#graphTable").bootstrapTable('load', list[1]);
    $("#pieTable").bootstrapTable('load', list[2]);
</script>

</body>
</html>