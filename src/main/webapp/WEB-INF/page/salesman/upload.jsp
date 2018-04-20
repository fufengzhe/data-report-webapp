<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>业务员银行手机号提取</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
</head>

<body>
<br/><br/><br/><br/><br/><br/><br/><br/>
<div class="container">
    <form role="form" enctype="multipart/form-data" action="getBankAndMobileInfo" method="post">
        <div class="form-group">
            <label>邮箱地址</label>
            <input type="text" class="form-control" name="mail"
                   placeholder="输入邮箱地址 xxxx@xx.xx,xxxx@xx.xx">
            <p class="help-block">该邮箱用于接收生成的结果,请认真填写，后台不做邮箱地址合法性判断,多个邮箱用逗号(,)分隔</p>
        </div>
        <div class="form-group">
            <label>文件选择</label>
            <input type="file" name="inputfile">
            <p class="help-block">上传仅包含业务员工号的txt文件,可参考示例文件<a href="downSampleFile"
                                                             class="btn btn-success btn-xs">示例文件下载</a>
            </p>
        </div>
        <button type="submit" class="btn btn-default">提交</button>
    </form>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>

</body>
</html>