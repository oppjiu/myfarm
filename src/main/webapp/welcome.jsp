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
    <title>欢迎首页</title>
    <style>
        body {
            margin: 0;
            background-image: url(ext/images/advancePic/welcome.png);
            background-size: 100% 100%;
            background-repeat: no-repeat;
            background-color: transparent;
            border: none;
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body></body>
</html>