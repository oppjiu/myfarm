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

        .bar {
            background-image: url(ext/images/advancePic/topbar.png);
            background-size: 25% 60px;
            background-repeat: repeat-x;
            width: 100%;
            height: 60px;
            border-bottom: 3px solid green;

            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-direction: row;
        }

        .leftBar {
            margin-left: 5px;

            flex: 3;
            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: flex-start;
        }

        .leftBar img {
            width: 50px;
            height: 50px;
            -moz-box-shadow: 2px 2px 5px #333333;
            -webkit-box-shadow: 2px 2px 5px #333333;
            box-shadow: 2px 2px 5px #333333;
        }

        .rightBar {
            margin-right: 5px;

            flex: 1;
            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: space-between;
        }

        .rightBar img {
            width: 50px;
            height: 50px;
            -moz-box-shadow: 2px 2px 5px #333333;
            -webkit-box-shadow: 2px 2px 5px #333333;
            box-shadow: 2px 2px 5px #333333;

            display: block;
        }

        .leftBarContent {
            margin-left: 20px;
            text-align: center;

            display: flex;
            flex-direction: column;

        }

        .leftBarContent div {
            flex: 1;
        }

        .leftBarBottom {
            padding: 0 20px 0;
            background-color: #40AFE5;
            -moz-box-shadow: 2px 2px 5px #333333;
            -webkit-box-shadow: 2px 2px 5px #333333;
            box-shadow: 2px 2px 5px #333333;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<div class="bar">
    <div class="leftBar">
        <img id="userinfoHeadImg" src="ext/images/headImages/0.jpg" alt="图片">
        <div class="leftBarContent">
            <div id="userinfoUsername"
                 style="text-shadow: 0 0 10px #5eff79, 0 0 10px #ff5a5a;font-weight: bold;font-size: 20px;color: lawngreen;">
            </div>
            <div class="leftBarBottom">
                <span>经验：</span><span style="font-family: KaiTi;margin-right: 20px;" id="userinfoExp"></span>
                <span>金币：</span><span style="font-family: KaiTi;margin-right: 20px;" id="userinfoMoney"></span>
                <span>积分：</span><span style="font-family: KaiTi;margin-right: 20px;" id="userinfoPoint"></span>
            </div>
        </div>
    </div>
    <div class="rightBar">
        <a href="<%=basePath%>/page/farmGamePage" target="workspace">
            <img onclick="restoreRows()" src="<%=basePath%>/ext/images/advancePic/myFarmIcon.png" alt="我的农场">
        </a>
        <a href="<%=basePath%>/page/seedPurchasePage" target="workspace">
            <img src="<%=basePath%>/ext/images/advancePic/storeIcon.png" alt="种子收纳袋">
        </a>
        <a href="<%=basePath%>/page/userLoginPage" target="workspace">
            <img onclick="restoreRows()" src="<%=basePath%>/ext/images/advancePic/userIcon.png" alt="玩家登录">
        </a>
        <a href="<%=basePath%>/page/userManagePage" target="workspace">
            <img onclick="restoreRows()" src="<%=basePath%>/ext/images/advancePic/farmerIcon.png" alt="玩家管理">
        </a>
        <a href="<%=basePath%>/page/cropPage" target="workspace">
            <img onclick="restoreRows()" src="<%=basePath%>/ext/images/advancePic/barnIcon.png" alt="种子管理">
        </a>
    </div>
</div>


<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script>
    $(function () {
        updateUserBoxView('<%=basePath%>');
    });

    function restoreRows() {
        parent.document.getElementById("main").rows = "60,*,50";
        if (parent.document.querySelector('#bottomSpace').src != 'tools.jsp') {
            parent.document.querySelector('#bottomSpace').src = 'tools.jsp';
        }
    }
</script>
</body>
</html>