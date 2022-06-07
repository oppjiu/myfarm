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

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/green/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/color.css">

    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.draggable.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/locale/easyui-lang-zh_CN.js"></script>

    <style>
        #cropGrowForm td input {
            width: 150px;
        }
    </style>
</head>
<body>
<table id="cropGrowGrid"></table>
<div id="cropGrowFormDialogToolbar" style="padding: 10px;">
    <a href="javascript:void(0)" class="easyui-linkbutton c2" iconCls="icon-add" plain="true"
       onclick="openCropGrowFormDialog()">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton c4" iconCls="icon-edit" plain="true"
       onclick="loadCropGrowFormDialog()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton c3" iconCls="icon-remove" plain="true"
       onclick="cropGrowGrid.edatagrid('cancelRow')">取消</a>
    <a href="javascript:void(0)" class="easyui-linkbutton c5" iconCls="icon-cancel" plain="true"
       onclick="cropGrowGrid.edatagrid('destroyRow')">删除</a>
</div>
<div id="cropGrowFormDialog" style="padding: 10px;">
    <form id="cropGrowForm" method="POST">
        <table class='tbledit'>
            <tr>
                <td>ID：</td>
                <td><input id="cropGrowId" name="id" required="true" class="easyui-textbox" value=""></td>
                <td>种子ID：</td>
                <td><input id="cropGrowCropId" name="cropId" required="true" class="easyui-textbox" value=""></td>
            </tr>
            <tr>
                <td>生长阶段：</td>
                <td><input id="cropGrowStageId" name="stageId" required="true" class="easyui-textbox" value=""></td>
                <td>生长标题：</td>
                <td><input name="stageName" required="true" class="easyui-textbox" value=""></td>
            </tr>
            <tr>
                <td>阶段生长时间：</td>
                <td><input name="growTime" required="true" class="easyui-textbox" value=""></td>
                <td>生虫概率：</td>
                <td><input name="insectChance" required="true" class="easyui-textbox" value=""></td>
            </tr>
            <tr>
                <td>作物状态：</td>
                <td><input name="cropStateCode"></td>
                <td></td>
                <td><a href="javascript:void(0)" class="easyui-linkbutton c2" iconCls="icon-blank" style="width: 150px;"
                       onclick="loadPositionDialog()">编辑图片位置</a></td>
            </tr>
            <tr>
                <td>图片宽度：</td>
                <td><input id="cropGrowPicWidth" name="picWidth" required="true" class="easyui-textbox" value=""
                           readonly></td>
                <td>图片高度：</td>
                <td><input id="cropGrowPicHeight" name="picHeight" required="true" class="easyui-textbox" value=""
                           readonly>
                </td>
            </tr>
            <tr>
                <td style="white-space:nowrap">图片offsetX：</td>
                <td><input id="cropGrowPicOffsetX" name="picOffsetX" required="true" class="easyui-textbox" value=""
                           readonly>
                </td>
                <td style="white-space:nowrap">图片offsetY：</td>
                <td><input id="cropGrowPicOffsetY" name="picOffsetY" required="true" class="easyui-textbox" value=""
                           readonly>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="cropGrowFormButtons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="saveCropGrowForm()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="closeCropGrowForm()">取消</a>
</div>

<div id="positionDialog" class="easyui-dialog" style="width:240px;height:420px;padding:10px 10px" closed="true"
     buttons="#positionDialogButtons">
    <div id="tools-imagePositioner-display" class="tools-imagePositioner-display">
        <img class="easyui-draggable easyui-resizable" data-options="onDrag:imagePositionerOnDrag"
             src="<%=basePath%>/ext/images/crops/940/2.png">
    </div>
</div>
<div id="positionDialogButtons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
       onclick="gainPosition()">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="$('#positionDialog').dialog('close')">取消</a>
</div>


<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/imgPosition.css">
<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/js/imgPosition.js"></script>
<script type="text/javascript">
    var cropGrowGrid;
    var cropStateList = {};

    $(function () {
        $.parser.parse();//全局刷新，动态加载表单后，easyui样式失效需要刷新
        /**
         * 获取caption值
         */
        getRemoteData('<%=basePath%>/common/cropState', false, function (data) {
            for (var i = 0; i < data.length; i++) {
                cropStateList[data[i]['cropStateCode']] = data[i]['cropState'];
            }
        });

        //作物状态下拉框
        $('input[name="cropStateCode"]').combobox({
            required: true,
            panelHeight: 'auto',
            editable: false,
            valueField: 'cropStateCode',
            textField: 'cropState',
            onSelect: function (rec) {
            },
            url: '<%=basePath%>/common/cropState'
        });

        //设置生长阶段dialog
        $('#cropGrowFormDialog').dialog({
            closed: 'true',
            buttons: '#cropGrowFormButtons',
            onClose: clearCropGrowForm
        });

        //设置种子图片编辑dialog
        $('#positionDialog').dialog({
            closed: 'true',
            buttons: '#positionDialogButtons',
            modal: true,
        });

        //生长阶段edatagrid
        cropGrowGrid = $("#cropGrowGrid").edatagrid({
            title: '',
            width: '100%',
            height: '100%',
            url: '<%=basePath%>/cropGrow/list/' + cropGrowId,
            saveUrl: '<%=basePath%>/cropGrow/save',
            updateUrl: '<%=basePath%>/cropGrow/save',
            destroyUrl: '<%=basePath%>/cropGrow/delete',
            method: 'post',
            fitColumns: true,
            striped: true,
            idField: 'id',
            fit: true,
            rownumbers: true,
            remoteSort: false,
            toolbar: '#cropGrowFormDialogToolbar',
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
                    align: 'center'
                },
                {
                    field: 'cropId',
                    title: '种子id',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'stageId',
                    title: '生长阶段',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {

                    field: 'stageName',
                    title: '生长标题',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'growTime',
                    title: '阶段生长时间',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'insectChance',
                    title: '生虫概率',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'picWidth',
                    title: '图片宽度',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'picHeight',
                    title: '图片高度',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'picOffsetX',
                    title: '图片offsetX',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'picOffsetY',
                    title: '图片offsetY',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'cropStateCode',
                    title: '作物状态',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    editor: {
                        type: 'combobox',
                        options: {
                            valueField: 'cropStateCode',
                            textField: 'cropState',
                            url: '<%=basePath%>/common/cropState',
                            panelHeight: 'auto',
                            required: true
                        }
                    },
                    formatter: function (value, row, index) {
                        return cropStateList[value];
                    },
                }
            ]],
            onLoadSuccess: function (data) {
                if (data.total == 0) {
                    messageBox('提示', '无记录');
                }
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

    //打开表单编辑窗口
    function openCropGrowFormDialog() {
        var $cropGrowId = $('#cropGrowId');
        var $cropGrowCropId = $('#cropGrowCropId');
        $('#cropGrowFormDialog').dialog('open').dialog('setTitle', '新建生长阶段数据');//打开种子生长阶段编辑页面
        $cropGrowId.textbox("setValue", 0);//设置id为0
        $cropGrowCropId.textbox("setValue", cropGrowId);//设置cropId
        //$cropGrowId和$cropGrowCropId设置只读
        $cropGrowId.textbox('readonly', true);
        $cropGrowCropId.textbox('readonly', true);
    }

    //关闭表单编辑窗口窗口
    function closeCropGrowForm() {
        $('#cropGrowFormDialog').dialog('close');
        clearCropGrowForm();
    }

    //清空表单
    function clearCropGrowForm() {
        $('#cropGrowForm').form('clear');//清空表单数据
        //$cropGrowId和$cropGrowCropId解除只读
        $('#cropGrowId').textbox('readonly', false);
        $('#cropGrowCropId').textbox('readonly', false);
    }

    //加载添加生长阶段表单
    function loadCropGrowFormDialog() {
        var row = cropGrowGrid.datagrid('getSelected');
        if (row) {
            $('#cropGrowForm').form('load', row);//加载表单
            $('#cropGrowFormDialog').dialog('open').dialog('setTitle', '更改生长阶段数据');//打开种子生长阶段编辑页面
            //$cropGrowId和$cropGrowCropId设置只读
            $('#cropGrowId').textbox('readonly', true);
            $('#cropGrowCropId').textbox('readonly', true);
        } else {
            messageBox('提示', '请先选择一行数据，然后再尝试点击操作按钮！');
        }
    }

    //保存种子生长阶段表单
    function saveCropGrowForm() {
        $('#cropGrowForm').form('submit', {
            url: '<%=basePath%>/cropGrow/save',
            onSubmit: function (param) {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    return isValid;//返回false终止表单提交
                }
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.code == 10) {
                    $('#cropGrowFormDialog').dialog('close');
                    cropGrowGrid.datagrid('reload');
                    messageBox('消息', result.message);
                } else {
                    messageBox('错误', '操作失败');
                }
            }
        });
    }

    //加载保存种子位置页面
    function loadPositionDialog() {
        var stageId = $('#cropGrowStageId').textbox('getValue');
        var cropId = $('#cropGrowCropId').textbox('getValue');
        var picWidth = $('#cropGrowPicWidth').textbox('getValue');
        var picHeight = $('#cropGrowPicHeight').textbox('getValue');
        var picOffsetX = $('#cropGrowPicOffsetX').textbox('getValue');
        var picOffsetY = $('#cropGrowPicOffsetY').textbox('getValue');
        if (stageId == '' || (stageId > 5 && stageId < 1)) {
            //表格信息不全弹窗提示
            messageBox('提示', '请填写相关数据后操作！');
        } else {
            var path;
            //更改图片路径
            if (stageId == 6 || stageId == 0) {
                path = '<%=basePath%>/ext/images/crops/basic/' + stageId + '.png';
            } else {
                path = '<%=basePath%>/ext/images/crops/' + cropId + '/' + stageId + '.png';
            }
            //初始化插件
            initDraggable();
            $('#positionDialog img').attr('src', path);
            //定位图片
            if (picWidth != 0 && picHeight != 0 && picOffsetX != 0 && picOffsetY != 0) {
                imgExtData.width = picWidth;
                imgExtData.height = picHeight;
                imgExtData.offsetX = picOffsetX;
                imgExtData.offsetY = picOffsetY;
            }
            positionerLoadImage();
            $('#positionDialog').dialog('open').dialog('setTitle', '图片位置编辑');//打开编辑窗口
        }
    }

    /**
     * 获取图像的坐标
     */
    function gainPosition() {
        $('#positionDialog').dialog('close');
        $('#cropGrowPicWidth').textbox('setValue', imgExtData.width);
        $('#cropGrowPicHeight').textbox('setValue', imgExtData.height);
        $('#cropGrowPicOffsetX').textbox('setValue', imgExtData.offsetX);
        $('#cropGrowPicOffsetY').textbox('setValue', imgExtData.offsetY);
    }
</script>
</body>
</html>
