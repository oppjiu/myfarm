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
            background-image: url(ext/images/basicPic/topbar.png);
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
    <a href="<%=basePath%>/page/farmGamePage" target="workspace">
        <img class="menu shadow" src="ext/images/advancePic/myFarmIcon.png" width="50px" alt="我的农场">
    </a>
    <a href="<%=basePath%>/page/seedBagPage" target="workspace">
        <img class="menu shadow" src="ext/images/advancePic/storeIcon.png" width="50px" alt="种子收纳袋">
    </a>
    <a href="<%=basePath%>/page/userLoginPage" target="workspace">
        <img class="menu shadow" src="ext/images/advancePic/userIcon.png" width="50px" alt="玩家登录">
    </a>
    <a href="<%=basePath%>/page/userManagerPage" target="workspace">
        <img class="menu shadow" src="ext/images/advancePic/farmerIcon.png" width="50px" alt="玩家管理">
    </a>
    <a href="<%=basePath%>/page/cropPage" target="workspace">
        <img class="menu shadow" src="ext/images/advancePic/barnIcon.png" width="50px" alt="种子管理">
    </a>
</div>
</body>
</html>