<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户属性分析</title>
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
    <div class="row panel-heading">
        <div class='col-sm-2'></div>
        <div class='col-sm-4'>
            <div class="form-group">
                <div class='input-group date text-center'>
                    <span class="input-group-addon">注册开始日期</span>
                    <input type='text' class="form-control" id="startDate" placeholder="选择时间"/>
                    <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    <span class="input-group-addon">注册截止日期</span>
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
            <button class="btn btn-success" data-toggle="modal" data-target="#sexAndRankModal">表格视图
            </button>
        </div>
        <div class='col-sm-2'></div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            用户性别及等级分布(性别统计基于身份证，故只统计有身份证的记录)
        </div>
    </div>
    <div class="row">
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfSex">无数据，请更改查询条件或联系开发人员。</div>
            <div id="sexPieChart" style="height:700px"></div>
        </div>
        <div class="text-center col-sm-6">
            <div class="alert alert-warning" style="display:none;" id="noDataOfRank">无数据，请更改查询条件或联系开发人员。</div>
            <div id="rankPieChart" style="height:700px"></div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            用户年龄段分布(年龄段统计基于身份证，故只统计有身份证的记录)&nbsp;&nbsp;&nbsp;<button class="btn btn-success" data-toggle="modal" data-target="#ageModal">表格视图
        </button>
        </div>
    </div>
    <div class="row">
        <div class="alert alert-warning" style="display:none;" id="noDataOfAge">
            无数据，请更改查询条件或联系开发人员。
        </div>
        <div class="container-fluid text-center" id="agePieChart" style="height:700px;">
        </div>
    </div>

    <div class="modal fade" id="sexAndRankModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel"
         aria-hidden="t rue">
        <div class="modal-dialog" style="width: 1000px;height: 1000px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="sexTable"></table>
                    <table id="rankTable"></table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <%--模态框--%>
    <div class="modal fade" id="ageModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel"
         aria-hidden="t rue">
        <div class="modal-dialog" style="width: 1000px;height: 1000px;">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">数据表格</h4>
                </div>
                <div class="modal-body">
                    <table id="ageTable"></table>
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
<script>
    var list =${userAttributeList};
    var userSourceList =${userSourceList};
    $("#startDate").val('${startDate}');
    $("#endDate").val('${endDate}');
    var userSourceDom = $("#userSource");
    userSourceDom.selectpicker({
        noneSelectedText: '全部渠道'
    });
    for (var i = 0; i < userSourceList.length; i++) {
        userSourceDom.append("<option value=" + userSourceList[i].userSourceName + ">" + userSourceList[i].userSourceName + "</option>");
    }
    userSourceDom.selectpicker('refresh');
    function pieChart(data, divId, chartName) {
        var legendData = [];
        var seriesData = [];
        for (var i = 0; i < data.length; i++) {
            legendData.push(data[i].category);
            seriesData.push({value: data[i].categoryNum, name: data[i].category});
        }
        drawPieChart(divId, chartName, legendData, seriesData);
    }

    if (list[0].length == 0) {
        $("#noDataOfSex").css('display', 'block');
    } else {
        pieChart(list[0], "sexPieChart", "用户性别分布");
    }
    if (list[1].length == 0) {
        $("#noDataOfRank").css('display', 'block');
    } else {
        pieChart(list[1], "rankPieChart", "用户等级分布(T:注册用户;C:在线绑定过保单的用户;E:在线核保过的用户)");
    }
    if (list[2].length == 0) {
        $("#noDataOfAge").css('display', 'block');
    } else {
        pieChart(list[2], "agePieChart", "用户年龄段分布");
    }

    $(function () {
        $("#queryDate").click(function () {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var userSource = $('#userSource').selectpicker('val');
            if ("" != startDate.trim() && "" != endDate.trim()) {
                setButtonDisabled('queryDate', true);
                $.ajax({
                    url: 'numQuery',
                    data: {"startDate": startDate, "endDate": endDate, "userSource": userSource},
                    traditional: true,
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        list = data.detailInfo;
                        if (list[0].length == 0) {
                            $("#noDataOfSex").css('display', 'block');
                            $("#sexTable").bootstrapTable('load', []);
                            echarts.init(document.getElementById('sexPieChart')).clear();
                        } else {
                            $("#noDataOfSex").css('display', 'none');
                            $("#sexTable").bootstrapTable('load', list[0]);
                            pieChart(list[0], "sexPieChart", "用户性别分布");
                        }
                        if (list[1].length == 0) {
                            $("#noDataOfRank").css('display', 'block');
                            $("#rankTable").bootstrapTable('load', []);
                            echarts.init(document.getElementById('rankPieChart')).clear();
                        } else {
                            $("#noDataOfRank").css('display', 'none');
                            $("#rankTable").bootstrapTable('load', list[1]);
                            pieChart(list[1], "rankPieChart", "用户等级分布(T:注册用户;C:在线绑定过保单的用户;E:在线核保过的用户)");
                        }
                        if (list[2].length == 0) {
                            $("#noDataOfAge").css('display', 'block');
                            $("#ageTable").bootstrapTable('load', []);
                            echarts.init(document.getElementById('agePieChart')).clear();
                        } else {
                            $("#noDataOfAge").css('display', 'none');
                            $("#ageTable").bootstrapTable('load', list[2]);
                            pieChart(list[2], "agePieChart", "用户年龄段分布");
                        }

                        setButtonDisabled('queryDate', false);
                    }
                });
            }
        });
    });
    generateDataTable("sexTable", [[{"field": "category"}, {"field": "categoryNum"}], [{"title": "性别"}, {"title": "注册数"}]])
    $("#sexTable").bootstrapTable('load', list[0]);
    generateDataTable("rankTable", [[{"field": "category"}, {"field": "categoryNum"}], [{"title": "等级"}, {"title": "注册数"}]])
    $("#rankTable").bootstrapTable('load', list[1]);
    generateDataTable("ageTable", [[{"field": "category"}, {"field": "categoryNum"}], [{"title": "年龄段"}, {"title": "注册数"}]])
    $("#ageTable").bootstrapTable('load', list[2]);
</script>

</body>
</html>