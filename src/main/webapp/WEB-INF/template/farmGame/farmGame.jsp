<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    String wsBasePath = "ws://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>我的农场</title>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/green/easyui.css?t564">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/color.css">

    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.draggable.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/locale/easyui-lang-zh_CN.js"></script>

    <basePath id="basePath" value="<%=basePath%>"></basePath>
    <basePath id="wsBasePath" value="<%=wsBasePath%>"></basePath>

    <style>
        #landBox {
            position: absolute;
        }

        .land {
            position: absolute;
        }

        .insect {
            position: absolute;
        }

        .crop {
            position: absolute;
        }

        .clickBox {
            position: absolute;
            clip-path: polygon(50% 0, 100% 50%, 50% 100%, 0 50%);

        }
    </style>
</head>
<body>
<div id="landBox"></div>
<div id="seedBagDialog"></div>

<audio id="soundWorm" src="<%=basePath%>/ext/sounds/worm.mp3"></audio>
<audio id="soundKillWorm" src="<%=basePath%>/ext/sounds/killWorm.mp3"></audio>
<audio id="soundCleanGrass" src="<%=basePath%>/ext/sounds/cleanGrass.mp3"></audio>
<audio id="soundHarvest" src="<%=basePath%>/ext/sounds/harvest.mp3"></audio>
<audio id="soundNegative" src="<%=basePath%>/ext/sounds/negative.mp3"></audio>
<audio id="soundPlantCrop" src="<%=basePath%>/ext/sounds/plantCrop.mp3"></audio>
<audio id="soundPlantCropSuccess" src="<%=basePath%>/ext/sounds/plantCropSuccess.mp3"></audio>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">
<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/js/farmGame.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/js/seedBag.js"></script>
</body>
</html>