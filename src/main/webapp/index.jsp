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
    <frame name="topSpace" src="menu.jsp" scrolling="no">
    <frame name="workspace" src="welcome.jsp" scrolling="no">
    <frame name="bottomSpace" src="tools.jsp" scrolling="no">
</frameset>
</html>
