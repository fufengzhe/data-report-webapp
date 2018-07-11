<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>国寿电商数据后台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper"
     role="navigation">
    <ul class="nav sidebar-nav">
        <li class="sidebar-brand"> <a href="#"> Bootstrap 3 </a> </li>
        <li> <a href="#"> <i class="fa fa-fw fa-home"> </i> Home </a> </li>
        <li> <a href="#"> <i class="fa fa-fw fa-folder"> </i> Page one </a> </li>
        <li> <a href="#"> <i class="fa fa-fw fa-file-o"> </i> Second page </a> </li>
        <li> <a href="#"> <i class="fa fa-fw fa-cog"> </i> Third page </a> </li>
        <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <i class="fa fa-fw fa-plus"> </i> Dropdown <span class="caret"> </span> </a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header"> Dropdown heading </li>
                <li> <a href="#"> Action </a> </li>
                <li> <a href="#"> Another action </a> </li>
                <li> <a href="#"> Something else here </a> </li>
                <li> <a href="#"> Separated link </a> </li>
                <li> <a href="#"> One more separated link </a> </li>
            </ul>
        </li>
        <li> <a href="#"> <i class="fa fa-fw fa-bank"> </i> Page four </a> </li>
        <li> <a href="#"> <i class="fa fa-fw fa-dropbox"> </i> Page 5 </a> </li>
        <li> <a href="#"> <i class="fa fa-fw fa-twitter"> </i> Last page </a> </li>
    </ul>
</nav>

<script>

    $(document).ready(function() {
        var trigger = $('.hamburger'),
            overlay = $('.overlay'),
            isClosed = false;
        trigger.click(function() {
            hamburger_cross();
        });
        function hamburger_cross() {
            if (isClosed == true) {
                overlay.hide();
                trigger.removeClass('is-open');
                trigger.addClass('is-closed');
                isClosed = false;
            } else {
                overlay.show();
                trigger.removeClass('is-closed');
                trigger.addClass('is-open');
                isClosed = true;
            }
        }
        $('[data-toggle="offcanvas"]').click(function() {
            $('#wrapper').toggleClass('toggled');
        });
    });
//    $(document).ready(function () {
//        var username = document.cookie.substr(("username".length + 1));
//        if (username.length == 0) {
//            document.getElementById('log').innerHTML = "登录"
//            document.getElementById('log').href = "/ecdata/user/toLogin";
//        } else {
//            $("#logDisplay").text('欢迎你，' + username);
//            document.getElementById('log').innerHTML = "登出"
//            $("#log").removeAttr("href");
//        }
//    });

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
