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
            margin: 0px;
        }
    </style>
</head>
<frameset rows="60,*,50" border="0">
    <frame src="menu.jsp" scrolling="no">
    <frame name="workspace" src="welcome.jsp" scrolling="no">
    <frame src="tools.jsp" scrolling="no">
</frameset>

</html>
