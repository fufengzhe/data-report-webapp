<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" href="${pageContext.request.contextPath}/static/img/sale_ico.ico" type="img/x-ico"/>
    <title>中国人寿销售公司数据分析平台</title>
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
<div class="row">
    <div class="col-sm-1">
    </div>
    <div class="col-sm-7">
        <img src="${pageContext.request.contextPath}/static/img/sale_logo.png" class="img-rounded" width="754" height="135">
    </div>
    <div class="col-sm-4">
    </div>
</div>
<div class="container" >
    <h2><span class="label label-success glyphicon glyphicon-ok"> 已有功能:</span>
    </h2>
    <br/>
    <ul class="list-group"  style="font-size:18px">
        <li class="list-group-item"><span class="glyphicon glyphicon-usd"></span>&nbsp;&nbsp;&nbsp;保单查询分析 <a href="/ecdata/saleOrder/summary" class="btn btn-success btn-xs"
                                                 target="_blank">点击查看</a>
        </li>
        <li class="list-group-item"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;&nbsp;用户注册查询分析 <a href="/ecdata/saleUser/registerNumSummary" class="btn btn-success btn-xs"
                                                  target="_blank">点击查看</a>
        </li>
        <li class="list-group-item"><span class="glyphicon glyphicon-map-marker"></span>&nbsp;&nbsp;&nbsp;注册手机号归属信息查询分析 <a href="/ecdata/saleUser/registerMobileSummary" class="btn btn-success btn-xs"
                                                  target="_blank">点击查看</a>
        </li>
        <li class="list-group-item"><span class="glyphicon glyphicon-log-in"></span>&nbsp;&nbsp;&nbsp;用户登录查询分析 <a href="/ecdata/saleUser/logNumSummary" class="btn btn-success btn-xs"
                                                   target="_blank">点击查看</a>
        </li>
    </ul>
</div>
<br/>

<div class="container">
    <span type="button" class="btn btn-primary btn-lg" style="text-shadow: black 5px 3px 3px;">
        <span class="glyphicon glyphicon-user"> 如需帮助请联系 运营中心 解翔宇（010-83574706）</span>
    </span>
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
        var username="";
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
                            window.location.href = window.location.origin + "/ecdata/external/sale";
                        } else {
                            $('#toLogin').popover('show');
                        }
                    }
                });
            }
        });
    });
</script>
</body>
</html>
