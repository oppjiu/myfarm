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

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/imgPosition.css?t=0901">

    <script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/js/imgPosition.js"></script>
</head>
<body>


<div id="formUserListContainer" class="easyui-dialog" style="width: 1000px; height:600px; padding: 10px 10px">
    <div id="controlBox">
        <span>用户名:</span> <input id="nameSearch">
        <a href="#" class="easyui-linkbutton c1" iconCls="icon-search" onclick="doSearch()">查询</a>
        <a href="#" class="easyui-linkbutton c2" iconCls="icon-add"
           onclick="javascript:seedGrid.edatagrid('addRow')">添加</a>
        <a href="#" class="easyui-linkbutton c3" iconCls="icon-remove"
           onclick="javascript:seedGrid.edatagrid('cancelRow')">取消</a>
        <a href="#" class="easyui-linkbutton c5" iconCls="icon-cancel"
           onclick="javascript:seedGrid.edatagrid('destroyRow')">删除</a>
    </div>
    <table id="grid"></table>
    <div id="msgBox"></div>


    <div id="formPicContainer" class="easyui-dialog" style="width: auto; height: auto; padding: 10px 10px" closed="true"
         buttons="#formEditSeedButtons">
        <form id="formUploadPic" method="POST" enctype="multipart/form-data">
            <table class='tbledit'>
                <tr>
                    <td>头像文件：</td>
                    <td><input id="file" name="headPic" required="true" class="w200" value="请选择头像" disabled="disabled">
                    </td>
                    <td><input type="file" name="upload" multiple="multiple" onchange="changeFile(this.value)"></td>
                </tr>
            </table>
        </form>
    </div>

    <!--编辑视图的按钮-->
    <div id="formEditSeedButtons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePicForm()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
           onclick="javascript:$('#formPicContainer').dialog('close')">取消</a>
    </div>

</div>

<script type="text/javascript">
    var params = {
        id: '',
        mode: 'insert'
    };
    var cropGrowGrid;
    $(document).ready(function () {
        seedGrid = $("#cropGrowGrid").edatagrid({
            title: '用户清单',
            width: 900,
            height: 500,
            /* data: someData,*/
            url: '<%=basePath%>/user/list',
            //保存 更新 删除
            saveUrl: '<%=basePath%>/user/save',
            updateUrl: '<%=basePath%>/user/save',
            destroyUrl: '<%=basePath%>/user/delete',
            method: "post",
            striped: true,
            idField: "id",
            rownumbers: true,
            remoteSort: false,
            pagination: true,
            pageSize: 6,
            pageList: [2, 4, 6],
            afterPageText: '页 共{pages}页',
            displayMsg: '当前显示{from}-{to}条记录，共{total}条记录',
            columns: [[
                {
                    field: 'id',
                    title: 'ID',
                    halign: 'center',
                    align: 'center',
                    width: 60
                },
                {
                    field: 'headImg',
                    title: '头像',
                    width: 60,
                    align: 'right',
                    halign: 'center',
                    formatter: function (value, row) {
                        if (value == null) value = "0.jpg";
                        return '<img src="<%=basePath%>/ext/images/headImages/' + value + '" width="60px">';
                    },
                    editor: {
                        type: 'validatebox',
                        options: {
                            validType: 'length[0,1024]',
                            invalidMessage: '有效长度0-1024',
                            required: true
                        }
                    }
                },
                {
                    field: 'username',
                    title: '用户名',
                    halign: 'center',
                    align: 'center',
                    width: 60,
                    sortable: true,
                    editor: {
                        type: 'validatebox',
                        options: {
                            validType: 'length[1,5]',
                            invalidMessage: '有效长度1-5',
                            required: true
                        }
                    }
                },
                {
                    field: 'nickname',
                    title: '昵称',
                    halign: 'center',
                    width: 60,
                    align: 'center',
                    sortable: true,
                    editor: {
                        type: 'validatebox',
                        options: {
                            validType: 'length[1,5]',
                            invalidMessage: '有效长度1-5',
                            required: true
                        }
                    }
                },
                {
                    field: 'exp',
                    title: '经验',
                    halign: 'center',
                    align: 'center',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row) {
                        return '<div><img src="<%=basePath%>/ext/images/headImages/5.jpg" width="30px">' + value + '</div>';
                    },
                    editor: {
                        type: 'validatebox',
                        options: {
                            validType: 'length[1,5]',
                            invalidMessage: '有效长度1-5',
                            required: true
                        }
                    }
                },
                {
                    field: 'point',
                    title: '积分',
                    halign: 'center',
                    width: 150,
                    align: 'center',
                    sortable: true,
                    formatter: function (value, row) {
                        return '<div><img src="<%=basePath%>/ext/images/headImages/6.jpg" width="30px">' + value + '</div>';
                    },
                    editor: {
                        type: 'validatebox',
                        options: {
                            validType: 'length[1,5]',
                            invalidMessage: '有效长度1-5',
                            required: true
                        }
                    }
                },
                {
                    field: 'money',
                    title: '金币',
                    halign: 'center',
                    align: 'center',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row) {
                        return '<div><img src="<%=basePath%>/ext/images/headImages/7.jpg" width="30px">' + value + '</div>';
                    },
                    editor: {
                        type: 'validatebox',
                        options: {
                            validType: 'length[1,5]',
                            invalidMessage: '有效长度1-5',
                            required: true
                        }
                    }
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    width: $(this).width() * 0.1,
                    formatter: function (value, row, index) {
                        var str = '<input type="button" value="上传头像" onclick="uploadHeadPic(' + row.id + ')">' +
                            '<input type="button" value="保存数据" onclick="javascript:cropGrowGrid.edatagrid(' + "\'saveRow\'" + ')">';
                        return str;
                    }
                }
            ]],
        });
    });


    //上传头像
    function uploadHeadPic(id) {
        params.mode = 'edit';
        $('#formPicContainer').dialog('open').dialog('setTitle', '添加头像');
        console.log(id)
    }

    //保存图片
    function savePicForm() {
        $('#formUploadPic').form('submit', {
            url: '<%=basePath%>/file/upload',
            onSubmit: function (param) {
                //param.id = params.id;
                //param.mode = params.mode;
                return $(this).form('validate');
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.code == 200) {
                    $('#formPicContainer').dialog('close');

                    //获取图片名称
                    var fileNamePic = $('#file').val();


                    // alert(fileNamePic);
                    // cropGrowGrid.datagrid('reload');
                }
                $.messager.show({
                    title: "消息",
                    msg: result.msg
                });
            }
        });
    }

    //查找用户
    function doSearch() {
        seedGrid.datagrid("load", {
            name: $("#nameSearch").val()
        });
    }

    //获取上传图片的名称
    function changeFile(x) {
        var change = document.getElementById("file");
        var name = x.substring(x.lastIndexOf("\\") + 1, x.length);
        change.value = name;
    }
</script>
</body>
</html>
