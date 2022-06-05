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

    <style>
        .item {
            width: 150px;
            margin-right: 10px;
            background-color: #ddd;
            height: 200px;
            float: left;
        }

        .total {
            text-align: center;
            margin-top: 5px;
        }

        .content {
            width: 630px;
            height: 200px;
            overflow: hidden;
            position: relative;
        }

        .all {
            height: 100%;
            transition: all .5s;
        }

        .empty {
            position: absolute;
            font-size: 30px;
            color: white;
            width: 500px;
            height: 100px;
            left: 50%;
            right: 50%;
            top: 45%;
            transform: translateX(-50%);
        }


        .total > span {
            font-size: 12px;
            display: inline-block;
            width: 26px;
            height: 26px;
            background-color: red;
            color: #fff;
            line-height: 26px;
            border-radius: 50%;
            font-weight: bold;
        }

        img {
            margin-top: 5px;
            width: 100%;
            height: 200px;
            object-fit: cover;
            display: block;
        }

        #root {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .right, .left {
            width: 140px;
            height: 140px;
            line-height: 140px;
        }
    </style>
</head>
<body>
<div id="root">
    <%--    <div class="left" style="background:url(<%=basePath%>/ext/images/advancePic/other/leftarrow.png)"></div>--%>
    <div class="right" style="background: url(<%=basePath%>/ext/images/other/rightarrow.png)"></div>
    <div class="content">
        <div class="all"></div>
        <div class="empty">
            用户种子袋为空，请购买种子
        </div>
    </div>
    <div class="left" style="background:url(<%=basePath%>/ext/images/other/leftarrow.png)"></div>
    <%--    <div class="right" style="background: url(<%=basePath%>/ext/images/advancePic/other/rightarrow.png)"></div>--%>
</div>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">
<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/js/seedBag.js"></script>
<script type="text/html" id="tpl">
    <div class="item">
        <div class="total">
            <span>{{total}}</span>
        </div>
        <img src="<%=basePath%>/ext/images/crops/{{cropId}}/5.png">
    </div>
</script>
</body>
</html>
