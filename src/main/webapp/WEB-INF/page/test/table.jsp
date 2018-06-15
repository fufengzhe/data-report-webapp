<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>表格测试</title>
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
    <div class="panel panel-default">
        <div class="panel-heading">
            表格测试
        </div>
        <div class='col-sm-6'>
            <%--<table id="table"></table>--%>
        </div>
    </div>
    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">开始演示模态框</button>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="width: 1000px;height: 1000px;">
            <div class="modal-content">
                <div class="modal-header">
                    <%--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
                    <h4 class="modal-title" id="myModalLabel">表格测试</h4>
                </div>
                <div class="modal-body">
                    <%--<div class='col-sm-6'>--%>
                        <table id="table"></table>
                    <%--</div>--%>
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
    $('#table').bootstrapTable({
        cache: false,
        pagination: true,
        search: true,
        pageList: [5, 10],
        columns: [[
            {
                field: 'label',
                title: '注册日期',
                halign : 'center',
                valign:"middle",
                rowspan: 2
            }, {
                field: 'label',
                title: '注册量',
                align: 'center',
                valign:"middle",
                rowspan: 2,
            }, {
                field: 'label',
                title: '留存率',
                align: 'center',
                colspan: 3,
                rowspan: 1
            }
        ],[
            {
                field: 'label',
                title: '一天后',
                align: 'center',
            }, {
                field: 'orderNum',
                title: '两天后',
                align: 'center',
            }, {
                field: 'orderNum',
                title: '三天后',
                align: 'center',
            }
        ]]
    });
        $("#table").bootstrapTable('load', [{"label": "a", "orderNum": 55}, {"label": "b", "orderNum": 66}, {
        "label": "b",
        "orderNum": 66
    }, {"label": "b", "orderNum": 66}
        , {"label": "b", "orderNum": 66}, {"label": "b", "orderNum": 66}, {"label": "b", "orderNum": 66}]);
</script>

</body>
</html>