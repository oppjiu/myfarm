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
    <base href="<%=basePath%>/">
    <title>我的农场</title>
    <style>
        body {
            margin: 0;
        }
    </style>
</head>
<frameset id="main" rows="60,*,50" border="0">
    <frame id="topSpace" name="topSpace" src="menu.jsp" scrolling="no">
    <frame id="workspace" name="workspace" src="welcome.jsp" scrolling="no">
    <frame id="bottomSpace" name="bottomSpace" src="tools.jsp" scrolling="no">
</frameset>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/green/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/color.css">

<script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.edatagrid.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.draggable.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/easyui/locale/easyui-lang-zh_CN.js"></script>

<script>
    function messageBox(title, msg) {
        $.messager.show({
            title: title,
            msg: msg
        });
    }
</script>
</html>
