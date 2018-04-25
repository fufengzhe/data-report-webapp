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
<br/>
<h1 align="center">Hello!Welcome To Data Server!</h1>
<h4 align="center">May Your Work Be Driven By Data!</h4>
<h6 align="center">Powered By Information Technology Department Of CLEC!</h6>

<div class="container">

    <h2><span class="label label-success glyphicon glyphicon-ok"> 已有功能（所有指标秒级响应）:</span></h2>
    <br/>
    <ul class="list-group">
        <li class="list-group-item">1、注册用户数查询</li>
        <li class="list-group-item">2、活跃用户数及明细查询<a href="/ecdata/activeUser/summary" class="btn btn-success btn-xs" >活跃用户数概览</a>
        </li>
        <li class="list-group-item">3、寿险保费查询</li>
        <li class="list-group-item">4、财险保费查询</li>
        <li class="list-group-item">5、平台交易规模查询</li>
        <li class="list-group-item">6、成交客户数查询</li>
        <li class="list-group-item">7、官网活跃邮件定时发送</li>
        <li class="list-group-item">8、业务员工号获取对应一账通绑定信息<a href="/ecdata/salesman/bankAndMobile" class="btn btn-success btn-xs" >点击使用</a>
        </li>
    </ul>
</div>

<div class="container">

    <h2><span class="label label-success glyphicon glyphicon-list-alt"> 数据展示:</span></h2>
    <br/>
    <ul class="list-group">
        <li class="list-group-item">目前提供安卓APP客户端查询，下载地址：<a target="_blank" href="https://www.pgyer.com/8Vkb"> 安卓APP下载</a></li>
    </ul>
</div>

<div class="container">

    <h2><span class="label label-success glyphicon glyphicon-flag"> 我们可以提供:</span></h2>
    <br/>
    <ul class="list-group">
        <li class="list-group-item">任何业务线生产库数据统计及展示</li>
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

</body>
</html>
