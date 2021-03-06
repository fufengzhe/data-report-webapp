<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>数据服务系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
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
<h1 align="center">Hello!Welcome To Data Server For User Center!</h1>
<h4 align="center">May Your Work Be Driven By Data!</h4>
<h6 align="center">Powered By Information Technology Department Of CLEC!</h6>
<div class="row">
    <div class="col-sm-9">
    </div>
    <div class="col-sm-3">
        <iframe width="700" scrolling="no" height="70" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=2&icon=1&num=2&site=12"></iframe>
    </div>
</div>
<div class="container">
    <h2><span class="label label-success glyphicon glyphicon-ok"> 已有功能（所有指标秒级响应）:</span></h2>
    <br/>
    <ul class="list-group">
        <li class="list-group-item">1、注册用户数查询 <a href="/ecdata/registerUser/summary" class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">2、注册用户手机号归属地及运营商分布 <a href="/ecdata/locationAnalysis/registerMobile" class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">3、活跃用户数及明细查询 <a href="/ecdata/activeUser/summary" class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">4、活跃用户IP归属地分布 <a href="/ecdata/locationAnalysis/activeIP" class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">5、活跃用户时间段及用户中心请求分布 <a href="/ecdata/locationAnalysis/activeHourAndUserCollDis" class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">6、用户迁徙分布 <a href="/ecdata/locationAnalysis/migrateCollectionDis" class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">7、注册&活跃综合分布 <a href="/ecdata/combinationAnalysis/registerAndActive"
                                                  class="btn btn-success btn-xs" target="_blank">点击查看</a></li>
        <li class="list-group-item">8、共享条款签署分布 <a href="/ecdata/userShare/distribute" class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">9、用户留存分析 <a href="/ecdata/userRetention/summary" class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">10、用户属性分析（年龄，性别及等级） <a href="/ecdata/userAttribute/summary" class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
    </ul>
</div>


<br/>

<div class="container">
    <span type="button" class="btn btn-primary btn-lg" style="text-shadow: black 5px 3px 3px;">
        <span class="glyphicon glyphicon-user"> 如需帮助请联系 信息技术部 解翔宇（010-83574831）</span>
    </span>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
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
                            window.history.go(0);
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
