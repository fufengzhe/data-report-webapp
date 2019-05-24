<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0"/>
    <link rel="icon" href="${pageContext.request.contextPath}/static/img/sale_ico.ico" type="img/x-ico"/>
    <title>保单被保人属性分析查询</title>
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

    <div class="row panel-heading">
        <div class='col-sm-4'>
            <div class="form-group">
                <div class='input-group date text-center'>
                    <span class="input-group-addon">投保时间</span>
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
                    <select id="orderProduct" class="selectpicker" data-live-search="true" multiple>
                    </select>
                </div>
            </div>
        </div>
        <div class='col-sm-2'>
            <div class="form-group">
                <div class='select-group text-center'>
                    <select id="orderSource" class="selectpicker" data-live-search="true" multiple>
                    </select>
                </div>
            </div>
        </div>
        <div class='col-sm-2'>
            <div class="form-group">
                <div class='select-group text-center'>
                    <select id="orderStatus" class="selectpicker" data-live-search="true" multiple>
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
            <button class="btn btn-success" data-toggle="modal" data-target="#pieModal">表格视图
            </button>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            保单被保人属性(性别,年龄)分布
        </div>
    </div>

    <div class="row">
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfSexPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="sexPieChart" style="height:600px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfAgePie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="agePieChart" style="height:600px"></div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            保单被保人与投保人关系分布
        </div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataOfRelationPie">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="relationPieChart" style="height:700px;">
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
                    <table id="sexPieTable"></table>
                    <table id="agePieTable"></table>
                    <table id="relationPieTable"></table>
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
    var productList =${productList};
    var sourceList =${sourceList};
    var statusList =${statusList};
    var list =${applicantAttributeList};
    var dateRange = '${startDate}' + " ~ " + '${endDate}';
    $("#dateRange").val(dateRange);
    //初始化产品多选框
    var saleProductDom = $("#orderProduct");
    saleProductDom.selectpicker({
        noneSelectedText: '全部产品'
    });
    for (var i = 0; i < productList.length; i++) {
        saleProductDom.append("<option value=" + productList[i].productId + ">" + productList[i].productName + "</option>");
    }
    saleProductDom.selectpicker('refresh');
    //初始化渠道选择框
    var saleSourceDom = $("#orderSource");
    saleSourceDom.selectpicker({
        noneSelectedText: '全部渠道'
    });
    for (var i = 0; i < sourceList.length; i++) {
        saleSourceDom.append("<option value=" + sourceList[i].sourceId + ">" + sourceList[i].source + "</option>");
    }
    saleSourceDom.selectpicker('refresh');

    //初始化状态选择框
    var saleStatusDom = $("#orderStatus");
    saleStatusDom.selectpicker({
        noneSelectedText: '全部状态'
    });
    for (var i = 0; i < statusList.length; i++) {
        saleStatusDom.append("<option value=" + statusList[i].orderStatusId + ">" + statusList[i].orderStatus + "</option>");
    }
    saleStatusDom.selectpicker('refresh');

    if (list[0].length == 0) {
        $("#noDataOfSexPie").css('display', 'block');
    } else {
        pieChart(list[0], "sexPieChart", "被保险人性别分布", 1);
    }
    if (list[1].length == 0) {
        $("#noDataOfAgePie").css('display', 'block');
    } else {
        pieChart(list[1], "agePieChart", "被保险人年龄分布", 2);
    }
    if (list[2].length == 0) {
        $("#noDataOfRelationPie").css('display', 'block');
    } else {
        pieChart(list[2], "relationPieChart", "被保险人与投保人关系分布", 3);
    }
    function pieChart(data, divId, chartName, legendIndex) {
        var legendData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            var str = legendIndex == 1 ? data[i].sex : (legendIndex == 2 ? data[i].ageSegment : data[i].relation);
            legendData.push(str);
            seriesData.push({value: data[i].orderNum, name: str});
        }
        drawPieChart(divId, chartName, legendData, seriesData, "");
    }

    $(function () {
        $("#queryDate").click(function () {
            var dateRange = $("#dateRange").val();
            var startDate = dateRange.substr(0, 10);
            var endDate = dateRange.substr(13);
            var whereCondition = $('#orderProduct').selectpicker('val');
            var whereCondition1 = $('#orderSource').selectpicker('val');
            var whereCondition2 = $('#orderStatus').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'applicantAttributeNumQuery',
                    data: {
                        "startDate": startDate,
                        "endDate": endDate,
                        "whereCondition": whereCondition,
                        "whereCondition1": whereCondition1,
                        "whereCondition2": whereCondition2
                    },
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list[0].length == 0 || list[1].length == 0 || list[2].length == 0) {
                                $("#noDataOfSexPie").css('display', 'block');
                                $("#noDataOfAgePie").css('display', 'block');
                                $("#noDataOfRelationPie").css('display', 'block');
                                echarts.init(document.getElementById('sexPieChart')).clear();
                                echarts.init(document.getElementById('agePieChart')).clear();
                                echarts.init(document.getElementById('relationPieChart')).clear();
                                $("#sexPieTable").bootstrapTable('load', []);
                                $("#agePieTable").bootstrapTable('load', []);
                                $("#relationPieTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfSexPie").css('display', 'none');
                                $("#noDataOfAgePie").css('display', 'none');
                                $("#noDataOfRelationPie").css('display', 'none');
                                pieChart(list[0], "sexPieChart", "被保险人性别分布", 1);
                                pieChart(list[1], "agePieChart", "被保险人年龄分布", 2);
                                pieChart(list[2], "relationPieChart", "被保险人与投保人关系分布", 3);
                                $("#sexPieTable").bootstrapTable('load', list[0]);
                                $("#agePieTable").bootstrapTable('load', list[1]);
                                $("#relationPieTable").bootstrapTable('load', list[2]);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    generateDataTable("sexPieTable", [[{"field": "sex"}, {"field": "orderNum"}],
        [{"title": "性别"}, {"title": "数量"}]])
    generateDataTable("agePieTable", [[{"field": "ageSegment"}, {"field": "orderNum"}],
        [{"title": "年龄段"}, {"title": "数量"}]])
    generateDataTable("relationPieTable", [[{"field": "relation"}, {"field": "orderNum"}],
        [{"title": "与投保人关系"}, {"title": "数量"}]])
    $("#sexPieTable").bootstrapTable('load', list[0]);
    $("#agePieTable").bootstrapTable('load', list[1]);
    $("#relationPieTable").bootstrapTable('load', list[2]);
</script>
</body>
</html>