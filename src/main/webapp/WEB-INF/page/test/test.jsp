<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>国寿电商数据后台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <ul id="main-nav" class="nav nav-tabs nav-stacked" style="">
                <li class="active">
                    <a href="#">
                        <i class="glyphicon glyphicon-th-large"></i>
                        首页
                    </a>
                </li>
                <li>
                    <a href="#systemSetting" class="nav-header collapsed" data-toggle="collapse">
                        <i class="glyphicon glyphicon-cog"></i>
                        系统管理
                        <span class="pull-right glyphicon glyphicon-chevron-down"></span>
                    </a>
                    <ul id="systemSetting" class="nav nav-list collapse secondmenu" style="height: 0px;">
                        <li><a href="/ecdata/registerUser/summary" target="_blank"><i class="glyphicon glyphicon-user"></i>用户管理</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-th-list"></i>菜单管理</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-asterisk"></i>角色管理</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-edit"></i>修改密码</a></li>
                        <li><a href="#"><i class="glyphicon glyphicon-eye-open"></i>日志查看</a></li>
                    </ul>
                </li>
                <li>
                    <a href="./plans.html">
                        <i class="glyphicon glyphicon-credit-card"></i>
                        物料管理
                    </a>
                </li>
                <li>
                    <a href="./grid.html">
                        <i class="glyphicon glyphicon-globe"></i>
                        分发配置
                        <span class="label label-warning pull-right">5</span>
                    </a>
                </li>
                <li>
                    <a href="./charts.html">
                        <i class="glyphicon glyphicon-calendar"></i>
                        图表统计
                    </a>
                </li>
                <li>
                    <a href="#">
                        <i class="glyphicon glyphicon-fire"></i>
                        关于系统
                    </a>
                </li>
            </ul>
        </div>
        @*<div class="col-md-10">
        主窗口
    </div>*@
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script>
    $(document).ready(function () {
        var username = document.cookie.substr(("username".length + 1));
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
                    url: 'user/logout',
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
