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
    <title>作物生长管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
<div id="positionDialog" class="easyui-dialog" style="width:240px;height:420px;padding:10px 10px" closed="true"
     buttons="#positionDialogButtons">
    <div id="tools-imagePositioner-display" class="tools-imagePositioner-display">
        <img class="easyui-draggable easyui-resizable" data-options="onDrag:imagePositioneronDrag" src="">
    </div>
</div>
<div id="positionDialogButtons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="gainPostion()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#positionDialog').dialog('close')">取消</a>
</div>
<script type="text/javascript">

</script>
</body>
</html>
