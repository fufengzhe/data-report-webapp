<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>注册手机号分布</title>
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
            渠道天维度注册手机号分布
        </div>
    </div>

    <div class="row">
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfCompanyPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="companyPieChart" style="height:700px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfLocationPie">无数据，请更改查询条件或联系开发人员。</div>
            <div id="locationPieChart" style="height:700px"></div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            渠道注册手机号地理位置分布
        </div>
    </div>
    <div class="alert alert-warning" style="display:none;" id="noDataOfLocationMap">无数据，请更改查询条件或联系开发人员。</div>
    <div class="container-fluid text-center" id="locationMapChart" style="height:800px;">
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
                    <table id="companyTable"></table>
                    <table id="locationTable"></table>
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
    if(list[0].length==0){
        $("#noDataOfCompanyPie").css('display', 'block');
    }else{
        pieChart(list[0], "companyPieChart", "天维度渠道注册手机号运营商分布");
    }
    if(list[1].length==0){
        $("#noDataOfLocationPie").css('display', 'block');
        $("#noDataOfLocationMap").css('display', 'block');
    }else{
        pieChart(list[1], "locationPieChart", "天维度渠道注册手机号地理位置分布");
        mapChart(list[1], "locationMapChart", "天维度渠道注册手机号地理位置分布");
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
    function mapChart(data, divId, title) {
        var minData = 1;
        var maxData = 1;
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            if (maxData < data[i].indexValue) {
                maxData = data[i].indexValue;
            }
            seriesData.push({ name: data[i].distributeName,value: data[i].indexValue});
        }
        var dataLocation='${pageContext.request.contextPath}/static/data/china.json';
        drawMapChart(divId,dataLocation ,title, minData, maxData, seriesData);
    }
    $(function () {
        $("#queryDate").click(function () {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var userSource = $('#userSource').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'registerMobileNumQuery',
                    data: {"startDate": startDate, "endDate": endDate, "userSource": userSource},
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            list = data.detailInfo;
                            if (list[0].length == 0) {
                                $("#noDataOfCompanyPie").css('display', 'block');
                                echarts.init(document.getElementById('companyPieChart')).clear();
                                $("#companyTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfCompanyPie").css('display', 'none');
                                pieChart(list[0], "companyPieChart", "天维度渠道注册手机号运营商分布");
                                $("#companyTable").bootstrapTable('load', list[0]);
                            }
                            if (list[1].length == 0) {
                                $("#noDataOfLocationPie").css('display', 'block');
                                $("#noDataOfLocationMap").css('display', 'block');
                                echarts.init(document.getElementById('locationPieChart')).clear();
                                echarts.init(document.getElementById('locationMapChart')).clear();
                                $("#locationTable").bootstrapTable('load', []);
                            } else {
                                $("#noDataOfLocationPie").css('display', 'none');
                                $("#noDataOfLocationMap").css('display', 'none');
                                pieChart(list[1], "locationPieChart", "天维度渠道注册手机号地理位置分布");
                                mapChart(list[1], "locationMapChart", "天维度渠道注册手机号地理位置分布");
                                $("#locationTable").bootstrapTable('load', list[1]);
                            }
                        }
                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    generateDataTable("companyTable", [[{"field": "distributeName"}, {"field": "indexValue"}], [{"title": "运营商"}, {"title": "用户数"}]])
    $("#companyTable").bootstrapTable('load', list[0]);
    generateDataTable("locationTable", [[{"field": "distributeName"}, {"field": "indexValue"}], [{"title": "省份"}, {"title": "用户数"}]])
    $("#locationTable").bootstrapTable('load', list[1]);
</script>

</body>
</html>