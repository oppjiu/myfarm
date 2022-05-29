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
    <title>Demo</title>

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
    <script type="text/javascript" src="<%=basePath%>/ext/js/jquery.ajaxFileUpload.js"></script>
</head>
<body>
<div><input type="file" id="file" name="upload"></div>
<div><input type="button" value="上传" onclick="upload()"></div>

<form action="<%=basePath%>/file/download">
    <div><input id="filename" name="filename" type="text" value=""></div>
    <div><input type="submit" value="下载" onclick="download()"></div>
</form>

<script>
    function upload() {
        var fileInput = $('#file').get(0).files[0];
        if (fileInput) {
            ajaxFileUpload(fileInput, 'file');
            console.info(fileInput);
        } else {
            alert('请选择上传文件！');
        }
    }

    function ajaxFileUpload(fileElement, fileElementId) {
        $.ajaxFileUpload({
            url: '<%=basePath%>/file/upload',
            fileElementId: fileElementId,
            secureuri: false,
            dataType: 'json',
            success: function (result) {
                console.info(result);
                if (result.code == 10) {
                    alert('上传成功');
                    $('#filename').val(result.data.substring(result['data'].lastIndexOf('\\') + 1));
                } else if (result.code == 0) {
                    console.info('上传出错');
                }
            },
            error: function (data, status, e) {
                console.info(e);
            }
        });
    }
</script>
</body>
</html>
