<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>注册用户&活跃用户</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-table.css"
          type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/utils/drawChart.js?ver=${jsVersion}"></script>
</head>

<body>

<div class="container-fluid text-center">

    <div class="panel panel-default">
        <div class="panel-heading">
            日维度注册用户&活跃用户分布
        </div>
    </div>
    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-4'></div>
            <div class='col-sm-2'>
                <div class="form-group">
                    <div class='input-group date text-center'>
                        <input type='text' class="form-control" id="startDate" placeholder="选择时间"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-primary" id="queryDate">开始查询</button>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-2'>
                <button class="btn btn-success" data-toggle="modal" data-target="#dateModal">表格视图
                </button>
            </div>
            <div class='col-sm-4'></div>
        </div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noData">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="dateScatterChart" style="height:700px;">
    </div>
    <br/>
    <div class="panel panel-default">
        <div class="panel-heading">
            月维度注册用户&活跃用户分布
        </div>
    </div>
    <div class="row">
        <div class="row panel-heading">
            <div class='col-sm-4'></div>
            <div class='col-sm-2'>
                <div class="form-group">
                    <div class='input-group date text-center'>
                        <input type='text' class="form-control" id="startMonth" placeholder="选择时间"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-primary" id="queryMonth">开始查询</button>
                        </span>
                    </div>
                </div>
            </div>
            <div class='col-sm-2'>
                <button class="btn btn-success" data-toggle="modal" data-target="#monthModal">表格视图
                </button>
            </div>
            <div class='col-sm-4'></div>
        </div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataOfMonth">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="monthScatterChart" style="height:700px;">
    </div>
    <%--模态框--%>
    <div class="modal fade" id="dateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="dateTable"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <%--模态框--%>
    <div class="modal fade" id="monthModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="monthTable"></table>
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


<script type="text/javascript">
    var list =${analysisIndexList};
    $("#startDate").val(list[0][0].statDate);
    prepareDataAndDraw('dateScatterChart', '日维度注册&活跃用户分布', list[0]);
    $("#startMonth").val(list[1][0].statDate);
    prepareDataAndDraw('monthScatterChart', '月维度注册&活跃用户分布', list[1]);
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


    $(function () {
        $("#queryDate").click(function () {
            var startDate = $("#startDate").val();
            if ("" != startDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'dateRegisterAndActive',
                    data: {"startDate": startDate},
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list.length == 0) {
                                $("#noData").css('display', 'block');
                                echarts.init(document.getElementById('dateScatterChart')).clear();
                                $("#dateTable").bootstrapTable('load', []);
                            } else {
                                $("#noData").css('display', 'none');
                                prepareDataAndDraw('dateScatterChart', '日维度注册&活跃用户分布', list);
                                $("#dateTable").bootstrapTable('load', list);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });

    $(function () {
        $("#queryMonth").click(function () {
            var startMonth = $("#startMonth").val();
            if ("" != startMonth.trim()) {
                setButtonDisabled('queryMonth', true);
                $.ajax({
                    url: 'monthRegisterAndActive',
                    data: {"startDate": startMonth},
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list.length == 0) {
                                $("#noDataOfMonth").css('display', 'block');
                                echarts.init(document.getElementById('monthScatterChart')).clear();
                                $("#monthTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfMonth").css('display', 'none');
                                prepareDataAndDraw('monthScatterChart', '月维度注册&活跃用户分布', list);
                                $("#monthTable").bootstrapTable('load', list);
                            }
                        }
                        setButtonDisabled('queryMonth', false);
                    }
                });
            }
        });
    });
    generateDataTable("dateTable", [[{"field": "indexSource"}, {"field": "registerNum"}, {"field": "activeNum"}], [{"title": "渠道"}, {"title": "注册数"}, {"title": "活跃数"}]])
    $("#dateTable").bootstrapTable('load', list[0]);
    generateDataTable("monthTable", [[{"field": "indexSource"}, {"field": "registerNum"}, {"field": "activeNum"}], [{"title": "渠道"}, {"title": "注册数"}, {"title": "活跃数"}]])
    $("#monthTable").bootstrapTable('load', list[1]);
</script>

</body>
</html>