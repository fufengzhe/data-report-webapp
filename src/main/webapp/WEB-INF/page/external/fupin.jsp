<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" href="${pageContext.request.contextPath}/static/img/logo.ico" type="img/x-ico"/>
    <title>国寿电商扶贫数据后台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-table.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css"
          type="text/css">
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div>
            <form class="navbar-form navbar-right" role="search" id="logForm">
                <a class="btn btn-default" href="/ecdata/user/toLogin" role="button" id="log">登录</a>
            </form>
            <p class="navbar-text navbar-right" id="logDisplay"></p>
        </div>
    </div>
</nav>
<h1 align="center">Hello!Welcome To Data Server!</h1>
<h4 align="center">May Your Work Be Driven By Data!</h4>
<h6 align="center">Powered By Poverty Alleviation Team Of CLEC!</h6>
<div class="row">
    <div class="col-sm-8">
    </div>
    <div class="col-sm-4">
        <iframe width="700" scrolling="no" height="70" frameborder="0" allowtransparency="true"
                src="http://i.tianqi.com/index.php?c=code&id=38&icon=1&num=2"></iframe>
    </div>
</div>
<div class="container">
    <h2><span class="label label-success glyphicon glyphicon-ok"> 已有功能:</span>
        &nbsp;<span id="dataStatus" class="label label-default glyphicon glyphicon-search"> 数据准备状态</span>
    </h2>
    <br/>
    <ul class="list-group">
        <li class="list-group-item">1、扶贫相关页面点击数查询 <a href="/ecdata/pageClick/summary" class="btn btn-success btn-xs"
                                                     target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">2、扶贫相关页面活跃用户分析查询（活跃IP运营商及归属地） <a href="/ecdata/pageClick/distributeAnalysis"
                                                                     class="btn btn-success btn-xs"
                                                                     target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">3、扶贫商品成单用户分析查询（下单IP运营商及归属地） <a href="/ecdata/orderStat/IPSummary"
                                                                   class="btn btn-success btn-xs"
                                                                   target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">4、扶贫商品订单数查询 <a href="/ecdata/orderStat/summary" class="btn btn-success btn-xs"
                                                   target="_blank">点击查看</a>
        </li>
    </ul>
</div>

<br/>

<div class="container">
    <span type="button" class="btn btn-primary btn-lg" style="text-shadow: black 5px 3px 3px;">
        <span class="glyphicon glyphicon-user"> 如需帮助请联系 扶贫项目组 解翔宇（010-86412705）</span>
    </span>
</div>

<%--模态框--%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">数据准备状态</h4>
            </div>
            <div class="modal-body">
                <table id="dataStatusTable"></table>
            </div>
            <div class="modal-footer">
                <span class="text-warning"> 请联系 扶贫项目组 解翔宇&nbsp;&nbsp;&nbsp;</span>
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
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
<script>
    $(document).ready(function () {
        var cookieStr = document.cookie;
        var start = cookieStr.indexOf("username");
        var username = "";
        if (start >= 0) {
            username = cookieStr.substring((start + "username".length + 1));
            var end = username.indexOf(";");
            if (end > 0) {
                username = username.substring(0, end);
            }
        }
        if (username.length == 0) {
            document.getElementById('log').innerHTML = "登录"
            document.getElementById('log').href = "/ecdata/user/toLogin";
        } else {
            $("#logDisplay").text('欢迎你，' + username);
            document.getElementById('log').innerHTML = "登出"
            $("#log").removeAttr("href");
        }
    });

    $(function () {
        $("#log").click(function () {
            var text = document.getElementById('log').innerHTML;
            if ("登出" == text) {
                $.ajax({
                    url: '../user/logout',
                    dataType: "json",
                    success: function (data) {
                        var respCode = data.respCode;
                        if (respCode == 0) {
                            window.location.href = window.location.origin + "/ecdata/external/fupin";
                        } else {
                            $('#toLogin').popover('show');
                        }
                    }
                });
            }
        });
    });

    $.ajax({
        url: '../init/getFupinDataStatus',
        dataType: "json",
        success: function (data) {
            var respCode = data.respCode;
            if (respCode == 0) {
                list = data.detailInfo;
                if (list.length == 4) {
                    $("#dataStatus").html(" 数据准备完成");
                    $("#dataStatus").attr("class", "label label-success glyphicon glyphicon-ok");
                } else {
                    $("#dataStatus").html(" 数据准备失败, <a data-toggle='modal' data-target='#myModal' style='cursor:pointer'>点击查看详情。 </a>");
                    $("#dataStatus").attr("class", "label label-warning glyphicon glyphicon-remove");
                    generateDataStatusTable(list);
                }
                console.log(list);
            } else {
                alert("数据准备状态查询失败!");
            }
        }
    });

    function generateDataStatusTable(list) {
        var itemArray = ['扶贫相关页面点击数查询', '扶贫相关页面活跃用户分析查询（IP运营商及归属地）', '扶贫商品成单用户分析查询（下单IP运营商及归属地）', '扶贫商品订单数查询'];
        var checkIndex = [['fupinPageClick'], ['fupinPageClickIPInfo'], ['fupinOrderIPInfo'],['fupinOrderStat']];
        generateDataTable("dataStatusTable", [[{"field": "itemChn"}, {"field": "dataStatus"}], [{"title": "功能项"}, {"title": "是否完成"}]]);
        var dataStatusList = new Array();
        for (var i = 0; i < itemArray.length; i++) {
            var status = new Object();
            status.itemChn = itemArray[i];
            status.dataStatus = checkDataStatus(checkIndex[i], JSON.stringify(list));
            dataStatusList.push(status);
        }
        $("#dataStatusTable").bootstrapTable('load', dataStatusList);
    }

    function checkDataStatus(indexArray, preparedArrayStr) {
        for (var i = 0; i < indexArray.length; i++) {
            if (preparedArrayStr.indexOf(indexArray[i]) < 0) {
                return "否";
            }
        }
        return "是";
    }
</script>
</body>
</html>
