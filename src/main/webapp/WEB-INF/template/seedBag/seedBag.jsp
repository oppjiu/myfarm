<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>种子收纳袋</title>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/green/easyui.css?t564">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/color.css">

    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.draggable.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/locale/easyui-lang-zh_CN.js"></script>

    <basePath id="basePath" value="<%=basePath%>"></basePath>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        html{
            border-top: white solid 5px;
        }
    </style>
</head>
<body>
<div id="seedBagBox">
    <div id="scrollToLeft" style="display: flex;align-items: center;">
        <img src="<%=basePath%>/ext/images/other/leftArrow.png" style="width: 50px;height: 50px;" alt="箭头">
    </div>
    <div id="seedBagContainer"></div>
    <div id="scrollToRight" style="display: flex;align-items: center;">
        <img src="<%=basePath%>/ext/images/other/rightArrow.png" style="width: 50px;height: 50px;" alt="箭头">
    </div>
</div>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">
<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/js/seedBag.js"></script>
</body>
</html>