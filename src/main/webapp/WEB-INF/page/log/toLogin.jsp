<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=2.0"/>
    <title>欢迎登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <style>
        body {
            margin-left: auto;
            margin-right: auto;
            margin-TOP: 100PX;
            width: 20em;
        }
    </style>
</head>

<body>
<div class="input-group">
    <span class="input-group-addon glyphicon glyphicon-user" style="position: static"></span>
    <input id="username" type="text" class="form-control" placeholder="用户名">
</div>
<br>
<!--下面是密码输入框-->
<div class="input-group">
    <span class="input-group-addon glyphicon glyphicon-lock" style="position: static"></span>
    <input id="password" type="password" class="form-control" placeholder="密码" aria-describedby="basic-addon1">
</div>
<br/>
<!--下面是登陆按钮,包括颜色控制-->
<button type="button" style="width:280px;" class="btn btn-primary" id="toLogin" data-loading-text="登录中..."
        data-toggle="popover" data-trigger="manual"
        data-container="body" data-toggle="popover" data-placement="bottom"
        title="用户名或密码错误!" data-content="请输入正确的用户名及密码，如无账号，查看账号创建">登 录
</button>
<br/><br/>
<button type="button" style="width:280px;" class="btn btn-info" data-toggle="popover" data-trigger="hover"
        data-container="body" data-toggle="popover" data-placement="bottom"
        title="账号帮助" data-html="true"
        data-content="联系人：销售公司运营中心-解翔宇<br/>电话：010-83574706">账号创建
</button>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/utils/commonUtils.js?ver=${jsVersion}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script>
    $(function () {
        $("[data-toggle='popover']").popover();
    });
</script>

<script>
    $('#toLogin').popover('hide');
    $(function () {
        $("#toLogin").click(function () {
            setButtonDisabled('toLogin', true);
            var username = $("#username").val();
            var password = $("#password").val();
            $.ajax({
                url: 'login',
                data: {"username": username, "password": password},
                dataType: "json",
                scriptCharset: 'utf-8',
                success: function (data) {
                    var respCode = data.respCode;
                    if (respCode == 0) {
//                        window.history.go(-1);
                        window.location.href = document.referrer;
                    } else {
                        $('#toLogin').popover('show');
                    }
                    setButtonDisabled('toLogin', false);
                }
            });
        });
    });
</script>
</body>
</html>