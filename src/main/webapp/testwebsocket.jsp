<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    String wsBasePath = "ws://" + request.getServerName() + ":" + request.getServerPort() + path;
    session.setAttribute("farmUsername", "userA");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Java API for WebSocket Demo</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/green/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/color.css">

    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript" src="<%=basePath%>/ext/farm/helper.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/farm/sockjs.js"></script>
</head>
<body>
用户选择：
<select id="userList" class="easyui-combobox" name="state" style="width:80px;" data-options="panelHeight: 'auto'">
    <option value="张三">张三</option>
    <option value="李四">李四</option>
    <option value="翠花">翠花</option>
</select>
<a href="#" class="easyui-linkbutton" iconCls="icon-ok" style="width:80px;height:32px" onclick="changeUser()">登录</a>
<br>
聊天对象选择：
<select id="targetList" class="easyui-combobox" name="state" style="width:100px;" data-options="panelHeight: 'auto'">
    <option value="张三">张三</option>
    <option value="李四">李四</option>
    <option value="翠花">翠花</option>
</select><br>
聊天内容：
<input id="myTalk" class="easyui-textbox" data-options="prompt:'请输入聊天内容'" style="width:300px;height:32px">
<a href="#" class="easyui-linkbutton" iconCls="icon-ok" style="width:100px;height:32px" onclick="doSend()">发送</a><br>
测试内容：
<input id="testSendHandler" class="easyui-textbox" data-options="prompt:'请输入测试内容'" style="width:300px;height:32px">
<a href="#" class="easyui-linkbutton" iconCls="icon-ok" style="width:100px;height:32px" onclick="testSendHandler()">发送</a>
<div id="talks" class="easyui-panel" title="聊天记录" style="width:500px;height:600px;padding:10px;"></div>
<script type="text/javascript">
    var websocket = null;

    function initWebSocket() {
        if ('WebSocket' in window) {
            //Websocket的连接
            websocket = new WebSocket("<%=wsBasePath%>/chat/action");//WebSocket对应的地址
        } else if ('MozWebSocket' in window) {
            //Websocket的连接
            websocket = new MozWebSocket("<%=wsBasePath%>/chat/action");//SockJS对应的地址
        } else {
            //SockJS的连接
            websocket = new SockJS("<%=wsBasePath%>/chat/action");//SockJS对应的地址
        }
        websocket.onopen = onOpen;
        websocket.onmessage = onMessage;
        websocket.onerror = onError;
        websocket.onclose = onClose;
    }

    function onOpen(evt) {
        console.log("连接打开：", evt);
    }

    function onMessage(evt) {
        $("#talks").append("<p>" + evt.data) + "</p>";
    }

    function onError(evt) {
        console.log("出现错误：", evt);
    }

    function onClose(evt) {
        console.log("连接关闭：", evt);
    }

    function doSend() {
        if (websocket.readyState == websocket.OPEN) {
            request({
                to: $("#userList").val(),
                message: $('#myTalk').val()
            }, "post", "<%=basePath%>/action/talk", callBack);
            console.log("发送成功!");
        } else {
            console.log("连接失败!");
        }
    }

    function testSendHandler() {
        if (websocket.readyState == websocket.OPEN) {
            websocket.send(JSON.stringify({
                to: $("#targetList").val(),
                message: $('#testSendHandler').val()
            }));
            console.log("发送成功!");

        } else {
            console.log("连接失败!");
        }
    }

    window.close = function () {
        websocket.onclose();
    }

    function changeUser() {
        request({username: $("#userList").val()}, "post", "<%=basePath%>/action/setCurUser", changeUserCallBack);
    }

    function changeUserCallBack(result) {
        callBack(result);
        initWebSocket();
    }

    function callBack(result) {
        console.log('result', result);
        $.messager.show({
            title: "消息",
            msg: "<center>" + result.msg + "</center>"
        });
    }

</script>
</body>
</html>