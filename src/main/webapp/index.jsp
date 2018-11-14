<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="icon" href="${pageContext.request.contextPath}/static/img/logo.ico" type="img/x-ico"/>
    <title>国寿电商数据后台</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-table.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-datetimepicker.min.css"
          type="text/css">
    <script type='text/javascript'>
        !function (e, t, n, g, i) {
            e[i] = e[i] || function () {
                    (e[i].q = e[i].q || []).push(arguments)
                }, n = t.createElement("script"), tag = t.getElementsByTagName("script")[0], n.async = 1, n.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + g, tag.parentNode.insertBefore(n, tag)
        }(window, document, "script", "assets.growingio.com/op/2.0/gio.js", "gio");
        gio('init', '0abf49df562142e58cc6a347d844e436',
            {
                'setImp': false,
                'setTrackerHost': 'w1.chinalife.com.cn', // vds域名
                'setTrackerScheme': 'https',
                'setOrigin': 'http://chinalife.com.cn/ecdata/' // 前端主域名
            });

        //put your custom page code here
        gio('send');
    </script>
    <!-- End GrowingIO Analytics code version: 2.1 -->

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
<h6 align="center">Powered By Information Technology Department Of CLEC!</h6>
<div class="row">
    <div class="col-sm-8">
    </div>
    <div class="col-sm-4">
        <iframe width="700" scrolling="no" height="70" frameborder="0" allowtransparency="true"
                src="http://i.tianqi.com/index.php?c=code&id=38&icon=1&num=2"></iframe>
    </div>
</div>
<div class="container">
    <h2><span class="label label-success glyphicon glyphicon-ok"> 已有功能（所有指标秒级响应）:</span>
        &nbsp;<span id="dataStatus" class="label label-default glyphicon glyphicon-search"> 数据准备状态</span>
    </h2>
    <br/>
    <ul class="list-group">
        <li class="list-group-item">1、注册用户数查询 <a href="/ecdata/registerUser/summary" class="btn btn-success btn-xs"
                                                 target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">2、注册用户手机号归属地及运营商分布 <a href="/ecdata/locationAnalysis/registerMobile"
                                                          class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">3、活跃用户数及明细查询 <a href="/ecdata/activeUser/summary" class="btn btn-success btn-xs" a
                                                    target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">4、活跃用户IP归属地分布 <a href="/ecdata/locationAnalysis/activeIP"
                                                     class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">5、活跃用户时间段及用户中心请求分布 <a href="/ecdata/locationAnalysis/activeHourAndUserCollDis"
                                                          class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">6、用户迁徙分布 <a href="/ecdata/locationAnalysis/migrateCollectionDis"
                                                class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">7、注册&活跃综合分布 <a href="/ecdata/combinationAnalysis/registerAndActive"
                                                   class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">8、共享条款签署分布 <a href="/ecdata/userShare/distribute" class="btn btn-success btn-xs"
                                                  target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">9、用户留存分析 <a href="/ecdata/userRetention/summary" class="btn btn-success btn-xs"
                                                target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">10、用户属性分析（年龄，性别及等级） <a href="/ecdata/userAttribute/summary"
                                                           class="btn btn-success btn-xs" target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">11、寿险保费查询 <a href="/ecdata/life/premiumSummary" class="btn btn-success btn-xs"
                                                 target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">12、财险保费查询 <a href="/ecdata/property/premiumSummary" class="btn btn-success btn-xs"
                                                 target="_blank">点击查看</a>
        </li>
        <li class="list-group-item">13、平台交易规模查询</li>
        <li class="list-group-item">14、成交客户数查询</li>
        <li class="list-group-item">15、官网活跃邮件定时发送</li>
        <li class="list-group-item">16、业务员工号获取对应一账通绑定信息 <a href="/ecdata/salesman/bankAndMobile"
                                                           class="btn btn-success btn-xs" target="_blank">点击使用</a>
        </li>
    </ul>
</div>

<div class="container">

    <h2><span class="label label-success glyphicon glyphicon-list-alt"> 数据展示:</span></h2>
    <br/>
    <ul class="list-group">
        <li class="list-group-item">目前提供安卓APP客户端查询，下载地址：<a target="_blank" href="https://www.pgyer.com/8Vkb">
            安卓APP下载</a></li>
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
                <span class="text-warning"> 请联系信息技术部 解翔宇&nbsp;&nbsp;&nbsp;</span>
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

    $.ajax({
        url: 'init/getDataStatus',
        dataType: "json",
        success: function (data) {
            var respCode = data.respCode;
            if (respCode == 0) {
                list = data.detailInfo;
                if (list.length == 14) {
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
        var itemArray = ['注册用户数查询', '注册用户手机号归属地及运营商分布', '活跃用户数及明细查询', '活跃用户IP归属地分布', '活跃用户时间段及用户中心请求分布',
            '用户迁徙分布', '注册&活跃综合分布', '共享条款签署分布', '用户留存分析', '用户属性分析（年龄，性别及等级）', '寿险保费查询', '财险保费查询'];
        var checkIndex = [['registerNum'], ['registerMobile'], ['activeNum'], ['activeIP'], ['activeHour'], ['migrateCollection', 'migrateCollectionUserNum'],
            ['registerNum', 'activeNum'], ['userShare'], ['userRetention'], ['userSex', 'userAge', 'userRank'], ['lifePremium'], ['propertyPremium']];
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
