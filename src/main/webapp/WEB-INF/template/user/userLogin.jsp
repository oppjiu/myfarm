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
</head>
<body>
<div id="userSelectionDialog" style=" padding: 10px;">
    <form id="formUserSelect" method="POST" novalidate>
        <input id="userSelection" name="userSelection" style="width: 100%;">
        <hr>
        <div>请在下拉框中选择用户昵称，并点击“确认”按钮设定当前用户信息。</div>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="login()"
           style="float:  right;">确认</a>
    </form>
</div>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">

<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script type="text/javascript">
    $(function () {
        //TODO title需要增加用户头像
        //用户选择窗口
        $('#userSelectionDialog').dialog({
            title: '用户登录',
            width: '400px',
            height: 'auto'
        });

        //自定义样式用户下拉框
        $('#userSelection').combobox({
            url: '<%=basePath%>/user/listAll',
            label: '当前用户：',
            labelPosition: 'top',
            method: 'post',
            panelWidth: '366px',
            panelHeight: 'auto',
            panelMaxHeight: '200px',
            valueField: 'username',
            textField: 'nickname',
            groupField: 'username',
            formatter: function (group) {
                var imgUrl = '<%=basePath%>/' + group.headImgUrl;
                var content = '  ' + group.nickname + ' | 经验：' + group.exp + ' | 金币：' + group.money + ' | 积分：' + group.point;
                return $('<div style="color:white;">')
                    .append('<img src="' + imgUrl + '" style="width:40px;height: 40px;vertical-align:middle;margin-left: 20px;font-weight: bold" alt="头像">' + content)
                    .append('</div>').prop("outerHTML");
            }
        });
    });

    /**
     * 用户登录
     */
    function login() {
        var username = $('#userSelection').combobox('getValue');
        if (username != '') {
            request({'username': username}, 'post', '<%=basePath%>/user/setCurUser', false, function (result) {
                if (result.code == 10) {
                    var data = result.data;
                    console.log('data: ', data);
                    messageBox('消息','用户已经设定为' + data.nickname);
                } else {
                    messageBox('错误','操作失败');
                }
            });
        }else{
            messageBox('提示','请选择用户');
        }
    }
</script>
</body>
</html>
