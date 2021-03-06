<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>权限错误</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
</head>

<body>
<br/><br/><br/><br/><br/><br/><br/><br/>
<div class="container text-center">
    <h2><span class="label label-warning glyphicon glyphicon-remove">无权限查看，请联系管理员开通相关权限!</span></h2><br/>
    <button type="button" style="width:280px;" class="btn btn-info" data-toggle="popover" data-trigger="hover"
            data-container="body" data-toggle="popover" data-placement="bottom"
            title="账号帮助" data-html="true"
            data-content="联系人：销售公司运营中心-解翔宇<br/>电话：010-83574706">账号帮助
    </button><br/><br/>
    <button type="button" style="width:280px;" class="btn btn-danger" id="close">关闭
    </button>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script>
    $(function () {
        $("[data-toggle='popover']").popover();
    });
    $(function () {
        $("#close").click(function () {
            window.close();
        });
    });
</script>
</body>
</html>