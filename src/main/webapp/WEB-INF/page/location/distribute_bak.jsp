<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>位置分布</title>
    <style type="text/css">
        html{height:100%}
        body{height:100%;margin:0px;padding:0px}
        #container{height:100%}
    </style>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css">
</head>

<body>
<br/>
<div id="container"></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZjFuwSnrpwicUzxIxguxFRQEXbWiwxjO"></script>

<script type="text/javascript">
    var map = new BMap.Map("container");
    map.enableScrollWheelZoom();
    // 创建地图实例
    var point = new BMap.Point(104.114129, 37.550339);
    // 创建点坐标
    map.centerAndZoom(point, 6);
    map.setMaxZoom(7);
    map.setMinZoom(5);
    // 初始化地图，设置中心点坐标和地图级别
    map.setMapStyle({
        styleJson:[
            {
                "featureType": "road",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            },
            {
                "featureType": "poilabel",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            },{
                "featureType": "boundary",
                "elementType": "all",
                "stylers": {
                    "visibility": "on"
                }
            },{
                "featureType": "city",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            },{
                "featureType": "district",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            },{
                "featureType": "town",
                "elementType": "all",
                "stylers": {
                    "visibility": "off"
                }
            },{
                "featureType": "background",
                "elementType": "all",
                "stylers": {
                    "color": "#404a59",
                    "visibility": "on"
                }
            }
        ]
    });
</script>

</body>
</html>