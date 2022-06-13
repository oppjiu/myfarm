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

    <basePath id="basePath" value="<%=basePath%>"></basePath>
    <basePath id="wsBasePath" value="<%=wsBasePath%>"></basePath>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/green/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/color.css">

    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.draggable.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/locale/easyui-lang-zh_CN.js"></script>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /*自定义滚动条*/
        .scrollbar-wrapper {
            overflow-y: auto;
        }

        ::-webkit-scrollbar {
            width: 6px;
            height: 6px;
        }

        ::-webkit-scrollbar-track {
            background: rgb(239, 239, 239);
            border-radius: 3px;
        }

        ::-webkit-scrollbar-thumb {
            background: #bfbfbf;
            border-radius: 3px;
        }

        ::-webkit-scrollbar-thumb:hover {
            background: #333;
        }

        ::-webkit-scrollbar-corner {
            background: #179a16;
        }

        /*底板*/
        #farmBackgroundBox {
            position: absolute;
            left: 0;
            right: 0;
            bottom: 0;
            top: 0;
            display: flex;
            flex-direction: column;
            background: url(<%=basePath%>/ext/images/advancePic/farm.png);
            background-size: cover;
        }

        /*土地样式*/
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
<div id="farmBackgroundBox">
    <div id="landBox"></div>
    <div id="seedBagDialog"></div>
</div>

<audio id="soundWorm" src="<%=basePath%>/ext/sounds/worm.mp3"></audio>
<audio id="soundKillWorm" src="<%=basePath%>/ext/sounds/killWorm.mp3"></audio>
<audio id="soundCleanGrass" src="<%=basePath%>/ext/sounds/cleanGrass.mp3"></audio>
<audio id="soundHarvest" src="<%=basePath%>/ext/sounds/harvest.mp3"></audio>
<audio id="soundNegative" src="<%=basePath%>/ext/sounds/negative.mp3"></audio>
<audio id="soundPlantCrop" src="<%=basePath%>/ext/sounds/plantCrop.mp3"></audio>
<audio id="soundPlantCropSuccess" src="<%=basePath%>/ext/sounds/plantCropSuccess.mp3"></audio>
<audio id="soundHarvestEasterEgg" src="<%=basePath%>/ext/sounds/harvestEasterEgg.mp3"></audio>
<audio id="soundPlantCropSuccessEasterEgg" src="<%=basePath%>/ext/sounds/plantCropSuccessEasterEgg.mp3"></audio>
<audio id="soundCleanGrassEasterEgg" src="<%=basePath%>/ext/sounds/cleanGrassEasterEgg.mp3"></audio>

<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/js/farmGame.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/js/seedBag.js"></script>
</body>
</html>