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
    <title>用户登录</title>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/green/easyui.css?t564">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/color.css">

    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.draggable.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/locale/easyui-lang-zh_CN.js"></script>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/imgPosition.css?t=0901">

    <script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/js/imgPosition.js"></script>
</head>
<body>
<div id="formUserSelectContainer" class="easyui-dialog" style="width: auto; height: auto; padding: 10px 10px">
    <form id="formUserSelect" method="POST" novalidate>
        <span>当前用户：</span>
        <span><input class="easyui-combobox" style="width: 188px" name="language" data-options="
				url: '<%=basePath%>/user/listAll',
				method: 'get',
				valueField: 'id',
				textField: 'text',
				panelWidth: 240,
				panelHeight: 'auto',
				formatter: formatItem
			"></span>
        <p><span>请在下拉框中选择用户昵称，并点击<br/>
               “确认”按钮设定当前用户信息。<input type="submit" value="确认"></span></p>
    </form>
</div>
<script type="text/javascript">
    function formatItem(row) {
        var s = '<span style="color:black"><img src="' + row.headImgUrl + '"style="height: 20px;height: 20px">' + row.nickname + "|" + "经验：" + row.exp + "|" + "金币：" + row.money + "|" + "积分：" + row.point + '</span>';
        return s;
        alert("hello")
    }
</script>
</body>
</html>
