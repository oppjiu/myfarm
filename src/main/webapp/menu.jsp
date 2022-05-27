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
    <title>菜单</title>
    <style>
        body {
            margin: 0px;
        }

        .bar {
            background-image: url(images/basicPic/topbar.png);
            background-size: 25% 60px;
            background-repeat: repeat-x;
        }

        .shadow {
            -moz-box-shadow: 2px 2px 5px #333333;
            -webkit-box-shadow: 2px 2px 5px #333333;
            box-shadow: 2px 2px 5px #333333;
        }

        .menu {
            margin: 3px 5px 5px 5px;
        }
    </style>
</head>
<body class="bar">
<div align="right" width="100%">
    <a href="<%=basePath%>/page/cropList" target="workspace"><img class="menu shadow" src="images/advancePic/农民.png" width="50px" alt=""></a>
    <a href="<%=basePath%>/page/cropList" target="workspace"><img class="menu shadow" src="images/advancePic/土壤icon.png" width="50px" alt=""></a>
    <a href="<%=basePath%>/page/cropList" target="workspace"><img class="menu shadow" src="images/advancePic/种子仓库.png" width="50px" alt=""></a>
    <a href="<%=basePath%>/page/cropList" target="workspace"><img class="menu shadow" src="images/basicPic/seedManager.png" width="50px"></a>
</div>
</body>
</html>