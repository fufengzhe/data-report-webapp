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
    $(document).ready(function(){
        mergeBootstrapCell();
    });
    function mergeBootstrapCell() {
        $("#table").bootstrapTable('mergeCells', {index: 1, field: 'registerTime', rowspan: 3});
        $("#table").bootstrapTable('mergeCells', {index: 4, field: 'registerTime', rowspan: 3});
    }
    $('#table').bootstrapTable({
        cache: false,
        pagination: true,
        search: true,
        pageList: [5, 10],
        sortable: true,
        sortOrder: 'asc',
        onPageChange: function(number, size) {
            mergeBootstrapCell();
        },
        columns: [[
            {
                field: 'registerTime',
                title: '注册日期',
                halign: 'center',
                valign: "middle",
                rowspan: 2
            }, {
                field: 'registerNum',
                title: '注册量',
                align: 'center',
                valign: "middle",
                rowspan: 2,
            }, {
                field: 'retentionRatio',
                title: '留存率',
                align: 'center',
                colspan: 3,
                rowspan: 1
            }
        ], [
            {
                field: 'oneOffset',
                title: '一天后',
                align: 'center',
            }, {
                field: 'twoOffset',
                title: '两天后',
                align: 'center',
                sortable: true,
            }, {
                field: 'threeOffset',
                title: '三天后',
                align: 'center',
                sortable: true,
            }
        ]],
    });

    $("#table").bootstrapTable('load', [{
        "registerTime": "2018-01-01",
        "registerNum": 55,
        "oneOffset": 55.55,
        "twoOffset": 44.55,
        "threeOffset": 33.55
    },
        {
            "registerTime": "2018-01-02",
            "registerNum": 33,
            "oneOffset": 22.55,
            "twoOffset": 11.55,
            "threeOffset": 23.55
        }, {
            "registerTime": "2018-01-02",
            "registerNum": 33,
            "oneOffset": 22.55,
            "twoOffset": 11.55,
            "threeOffset": 1.55
        }, {
            "registerTime": "2018-01-02",
            "registerNum": 33,
            "oneOffset": 22.55,
            "twoOffset": 11.55,
            "threeOffset": 44.55
        }, {
            "registerTime": "2018-01-02",
            "registerNum": 33,
            "oneOffset": 22.55,
            "twoOffset": 11.55,
            "threeOffset": 66.55
        }, {
            "registerTime": "2018-01-02",
            "registerNum": 33,
            "oneOffset": 22.55,
            "twoOffset": 11.55,
            "threeOffset": null,
        },{
            "registerTime": "2018-01-02",
            "registerNum": 33,
            "oneOffset": 22.55,
            "twoOffset": 11.55,
            "threeOffset": 23.55
        }, {
            "registerTime": "2018-01-02",
            "registerNum": 33,
            "oneOffset": 22.55,
            "twoOffset": 11.55,
            "threeOffset": 1.55
        }, {
            "registerTime": "2018-01-02",
            "registerNum": 33,
            "oneOffset": 22.55,
            "twoOffset": 11.55,
            "threeOffset": 44.55
        }, {
            "registerTime": "2018-01-02",
            "registerNum": 33,
            "oneOffset": 22.55,
            "twoOffset": 11.55,
            "threeOffset": 66.55
        }, {
            "registerTime": "2018-01-02",
            "registerNum": 33,
            "oneOffset": 22.55,
            "twoOffset": 11.55,
            "threeOffset": null,
        }]);
</script>

</body>
</html>