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
    <title>用户管理</title>

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

<div id="userFormWindow" style="padding: 10px;">
    <table id="userManageGrid"></table>
    <div id="userFormWindowToolbar" style="padding: 10px;">
        <input id="usernameSearchBox">
        <a href="javascript:void(0)" class="easyui-linkbutton c2" iconCls="icon-add"
           onclick="userManageGrid.edatagrid('addRow')">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton c3" iconCls="icon-remove"
           onclick="userManageGrid.edatagrid('cancelRow')">取消</a>
        <a href="javascript:void(0)" class="easyui-linkbutton c5" iconCls="icon-cancel"
           onclick="userManageGrid.edatagrid('destroyRow')">删除</a>
    </div>

    <div id="uploadPicDialog" style="padding: 10px;">
        <form id="uploadPic" method="POST" enctype="multipart/form-data">
            <input id="file" name="file" style="width: 100%;">
            <input id="username" name="username" type="hidden" style="width: 100%;">
        </form>
    </div>
    <div id="uploadPicButtons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
           onclick="savePicForm()">开始上传</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
           onclick="$('#uploadPicDialog').dialog('close')">关闭窗口</a>
    </div>
</div>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">

<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script type="text/javascript">
    var rowIndex;
    var userManageGrid;
    $(function () {
        //用户名搜索框
        $('#usernameSearchBox').searchbox({
            prompt: '请输入用户名',
            searcher: function () {
                userManageGrid.datagrid('load', {
                    username: $("#usernameSearchBox").val()
                });
            },
        });
        //用户清单窗口
        $('#userFormWindow').window({
            title: '用户管理',
            width: '80%',
            height: '80%',
            inline: true,
        });
        //上传头像窗口
        $('#uploadPicDialog').dialog({
            title: '',
            width: '50%',
            closed: 'true',
            inline: true,
            buttons: '#uploadPicButtons',
        });
        //上传文件box
        $('#file').filebox({
            required: true,
            buttonText: '选择文件',
            buttonAlign: 'right',
            prompt: '选择文件上传……',
            label: '文件上传：',
            labelPosition: 'left'
        });

        userManageGrid = $("#userManageGrid").edatagrid({
            title: '',
            width: '100%',
            height: '100%',
            url: '<%=basePath%>/user/list',
            saveUrl: '<%=basePath%>/user/register',
            updateUrl: '<%=basePath%>/user/modify',
            destroyUrl: '<%=basePath%>/user/delete',
            method: "post",
            striped: true,
            fitColumns: true,
            idField: "id",
            rownumbers: true,
            remoteSort: false,
            fit: true,
            pagination: true,
            pageSize: 10,
            pageList: [5, 10, 20, 50],
            afterPageText: '页 共{pages}页',
            displayMsg: '当前显示{from}-{to}条记录，共{total}条记录',
            toolbar: '#userFormWindowToolbar',
            destroyMsg: {
                norecord: {
                    title: '警告',
                    msg: '未选择任何记录。'
                },
                confirm: {
                    title: '确认',
                    msg: '是否删除该数据？'
                }
            },
            columns: [[
                {
                    field: 'id',
                    title: 'ID',
                    halign: 'center',
                    align: 'center',
                },
                {
                    field: 'headImgUrl',
                    title: '头像',
                    align: 'center',
                    halign: 'center',
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {readonly: true}},
                    formatter: function (value, row, index) {
                        if (row.headImgUrl != undefined) {
                            var imgUrl = '<%=basePath%>/' + row.headImgUrl;
                            return $('<div><img src="' + imgUrl + '" style="width:60px;" alt="图片"></div>').prop("outerHTML");
                        }
                    }
                },
                {
                    field: 'username',
                    title: '用户名',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'nickname',
                    title: '昵称',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'exp',
                    title: '经验',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}},
                    formatter: function (value, row, index) {
                        var imgUrl = '<%=basePath%>/ext/images/advancePic/userManageExpIcon.png';
                        return $('<div><img src="' + imgUrl + '" style="width:30px;" alt="图片">' + value + '</div>').prop("outerHTML");
                    }
                },
                {
                    field: 'point',
                    title: '积分',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}},
                    formatter: function (value, row, index) {
                        var imgUrl = '<%=basePath%>/ext/images/advancePic/userManagePointIcon.png';
                        return $('<div><img src="' + imgUrl + '" style="width:30px;" alt="图片">' + value + '</div>').prop("outerHTML");
                    }
                },
                {
                    field: 'money',
                    title: '金币',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}},
                    formatter: function (value, row, index) {
                        var imgUrl = '<%=basePath%>/ext/images/advancePic/userManageMoneyIcon.png';
                        return $('<div><img src="' + imgUrl + '" style="width:30px;" alt="图片">' + value + '</div>').prop("outerHTML");
                    }
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    width: $(this).width() * 0.1,
                    formatter: function (value, row, index) {
                        var str = $('<input type="button" class="blueColorButton" value="上传头像" onclick="uploadHeadPic(' + index + ",\'" + row.username + '\')"/>').prop("outerHTML");
                        str += $('<input type="button" class="pinkColorButton" value="保存数据" onclick="userManageGrid.edatagrid(\'saveRow\')"/>').prop("outerHTML");
                        return str;
                    }
                }
            ]],
            onLoadSuccess: function (data) {
                if (data.total == 0) {
                    messageBox('提示', '无记录');
                }
            },
            onAdd: function (index, row) {
                var headImgUrlEditor = userManageGrid.datagrid('getEditor', {
                    index: index,
                    field: 'headImgUrl'
                });
                $(headImgUrlEditor.target).textbox('setValue', '/ext/images/headImages/0.jpg');
            },
            onSuccess: function (index, row) {
                messageBox('消息', '数据保存成功');
            },
            onDestroy: function (index, row) {
                messageBox('消息', '数据删除成功');
            },
            onError: function (index, row) {
                messageBox('消息', '操作失败');
            }
        });
    });

    //上传头像
    function uploadHeadPic(index, username) {
        rowIndex = index;
        $('#uploadPicDialog').dialog('open');
        $('#username').val(username);
    }

    //保存图片
    function savePicForm() {
        //上传头像
        $('#uploadPic').form('submit', {
            url: '<%=basePath%>/file/upload',
            onSubmit: function (param) {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    return isValid;//返回false终止表单提交
                }
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.code == 10) {
                    userManageGrid.datagrid('reload');
                    <%--//如果用户信息头像相同则更换头像--%>
                    <%--sessionStorage.setItem('userinfoHeadImg', result.data);--%>
                    <%--parent.document.querySelector('#topSpace').src = '<%=basePath%>/menu.jsp';--%>
                    messageBox('消息', '上传成功');
                } else {
                    messageBox('消息', '上传失败');
                }
            }
        });
        $('#uploadPicDialog').dialog('close');
    }
</script>
</body>
</html>
