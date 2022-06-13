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
    <title>种子管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/green/easyui.css?t=34355">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/color.css">

    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/locale/easyui-lang-zh_CN.js"></script>

</head>
<body>
<table id="seedGrid"></table>
<div id="cropGrowWindow" style="padding:10px;"></div>
<div id="seedFormDialogToolbar" style="padding:10px;">
    <a href="javascript:void(0);" class="easyui-linkbutton c2" iconCls="icon-add" plain="true"
       onclick="openSeedFormDialog()">添加</a>
    <a href="javascript:void(0);" class="easyui-linkbutton c4" iconCls="icon-edit" plain="true"
       onclick="loadSeedFormDialog()">编辑</a>
    <a href="javascript:void(0);" class="easyui-linkbutton c3" iconCls="icon-remove" plain="true"
       onclick="$('#seedGrid').edatagrid('cancelRow');">取消</a>
    <a href="javascript:void(0);" class="easyui-linkbutton c5" iconCls="icon-cancel" plain="true"
       onclick="$('#seedGrid').edatagrid('destroyRow');">删除</a>
    <input id="seedNameSearchBox">
</div>
<div id="seedFormDialog" style="padding: 10px;">
    <form id="seedForm" method="POST">
        <table class='tbledit'>
            <tr>
                <td>ID：</td>
                <td><input id="seedId" name="id" required="true" class="easyui-textbox" value=""></td>
                <td>种子ID：</td>
                <td><input id="seedCropId" name="cropId" required="true" class="easyui-textbox" value=""></td>
            </tr>
            <tr>
                <td>种子名称：</td>
                <td><input name="cropName" required="true" class="easyui-textbox" value=""></td>
                <td>X季作物：</td>
                <td><input name="growSeason" required="true" class="easyui-textbox" value=""></td>
            </tr>
            <tr>
                <td>种子等级：</td>
                <td><input name="grade" required="true" class="easyui-textbox" value=""></td>
                <td>种子类型：</td>
                <td><input id="seedSeedTypeCode" name="seedTypeCode" value=""></td>
            </tr>
            <tr>
                <td>可获经验：</td>
                <td><input name="harvestExp" required="true" class="easyui-textbox" value=""></td>
                <td>每季成熟所需时间：</td>
                <td><input name="growthTimeOfEachSeason" required="true" class="easyui-textbox" value=""></td>
            </tr>
            <tr>
                <td>每季成熟可获得收成：</td>
                <td><input name="harvestNum" required="true" class="easyui-textbox" value=""></td>
                <td>种子采购价：</td>
                <td><input name="purchasePrice" required="true" class="easyui-textbox" value=""></td>
            </tr>
            <tr>
                <td>每个收获的果实单价：</td>
                <td><input name="salePrice" required="true" class="easyui-textbox" value=""></td>
                <td>土地需求：</td>
                <td><input id="seedLandTypeCode" name="landTypeCode" value=""></td>
            </tr>
            <tr>
                <td>每季成熟可获得积分：</td>
                <td><input name="harvestScore" required="true" class="easyui-textbox" value=""></td>
                <td>提示信息：</td>
                <td><input name="tips" required="true" class="easyui-textbox" value="" data-options="multiline:true"
                           style="height: 100px;"></td>
            </tr>
        </table>
    </form>
</div>
<div id="seedFormButtons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
       onclick="saveSeedForm()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeSeedForm()">取消</a>
</div>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/imgPosition.css">
<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script type="text/javascript" src="<%=basePath%>/ext/js/imgPosition.js"></script>
<script type="text/javascript">
    var seedGrid;
    var landTypeList = {};
    var seedTypeList = {};
    var cropGrowId;//打开cropGrowWindow的id
    $(function () {
        /**
         * 获取caption值
         */
        getRemoteData('<%=basePath%>/common/landType', false, function (data) {
            for (var i = 0; i < data.length; i++) {
                landTypeList[data[i]['landTypeCode']] = data[i]['landType'];
            }
        });
        getRemoteData('<%=basePath%>/common/seedType', false, function (data) {
            for (var i = 0; i < data.length; i++) {
                seedTypeList[data[i]['seedTypeCode']] = data[i]['seedType'];
            }
        });

        //土地类型下拉框
        $('#seedLandTypeCode').combobox({
            panelHeight: 'auto',
            editable: false,
            valueField: 'landTypeCode',
            textField: 'landType',
            required: true,
            onSelect: function (rec) {
            },
            url: '<%=basePath%>/common/landType'
        });

        //种子类型下拉框
        $('#seedSeedTypeCode').combobox({
            panelHeight: 'auto',
            editable: false,
            valueField: 'seedTypeCode',
            textField: 'seedType',
            required: true,
            onSelect: function (rec) {
            },
            url: '<%=basePath%>/common/seedType'
        });

        //种子编辑窗口
        $('#seedFormDialog').dialog({
            title: '种子编辑窗口',
            closed: 'true',
            buttons: '#seedFormButtons',
            onClose: clearSeedForm
        });

        //生长阶段管理窗口
        $('#cropGrowWindow').window({
            width: '80%',
            height: '80%',
            title: '生长阶段管理',
            iconCls: 'icon-edit',
            inline: true,
            modal: true,
            closed: 'true',
            onClose: clearImgExtData
        });

        $('#seedNameSearchBox').searchbox({
            prompt: '请输入种子名称',
            searcher: function () {
                seedGrid.datagrid('load', {
                    cropName: $("#seedNameSearchBox").val()
                });
            },
        });

        //设置edatagrid
        seedGrid = $('#seedGrid').edatagrid({
            title: '种子管理',
            method: 'post',
            width: '100%',
            height: '100%',
            url: '<%=basePath%>/crop/list',
            saveUrl: '<%=basePath%>/crop/save',
            updateUrl: '<%=basePath%>/crop/save',
            destroyUrl: '<%=basePath%>/crop/delete',
            fitColumns: true,
            striped: true,
            idField: 'id',
            nowrap: false,
            rownumbers: true,
            remoteSort: false,
            pagination: true,
            pageSize: 10,
            pageList: [5, 10, 20, 50],
            afterPageText: '页 共{pages}页',
            displayMsg: '当前显示{from}-{to}条记录，共{total}条记录',
            toolbar: '#seedFormDialogToolbar',
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
                    sortable: true
                },
                {
                    field: 'cropName',
                    title: '种子名称',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'growSeason',
                    title: 'X季作物',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'grade',
                    title: '种子等级',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'seedTypeCode',
                    title: '种子类型',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {
                        type: 'combobox',
                        options: {
                            valueField: 'seedTypeCode',
                            textField: 'seedType',
                            url: '<%=basePath%>/common/seedType',
                            panelHeight: 'auto',
                            required: true
                        }
                    },
                    formatter: function (value, row, index) {
                        return seedTypeList[value];
                    }
                },
                {
                    field: 'harvestExp',
                    title: '可获经验',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'growthTimeOfEachSeason',
                    title: '每季成熟所需时间',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'harvestNum',
                    title: '每季成熟可获得收成',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'purchasePrice',
                    title: '种子采购价',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'salePrice',
                    title: '每个收获的果实单价',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'landTypeCode',
                    title: '土地需求',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {
                        type: 'combobox',
                        options: {
                            valueField: 'landTypeCode',
                            textField: 'landType',
                            url: '<%=basePath%>/common/landType',
                            panelHeight: 'auto',
                            required: true
                        }
                    },
                    formatter: function (value, row, index) {
                        return landTypeList[value];
                    },
                },
                {
                    field: 'harvestScore',
                    title: '每季成熟可获得积分',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'tips',
                    title: '提示信息',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    editor: {type: 'textbox', options: {required: true}}
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    width: $(this).width() * 0.1,
                    formatter: function (value, row, index) {
                        return $('<input type="button" class="greenColorButton" value="成长阶段" onclick="openCropGrowWindow(\'' + row.cropId + '\')"/>').prop("outerHTML");
                    }
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
        });
    });

    //打开种子成长阶段窗口
    function openCropGrowWindow(cropId) {
        cropGrowId = cropId;//传递cropId给cropGrowWindow
        let $cropGrowWindow = $('#cropGrowWindow');
        $cropGrowWindow.window('open');
        $cropGrowWindow.window('refresh', '<%=basePath%>/page/cropGrowPage');
    }

    function openSeedFormDialog() {
        var $seedId = $('#seedId');
        $('#seedFormDialog').dialog('open');//打开种子编辑页面
        $seedId.textbox("setValue", 0);//id为0
        //$seedId设置只读
        $seedId.textbox('readonly', true);
    }

    function clearSeedForm() {
        $('#seedForm').form('clear');//清空表单数据
        //$seedId和$seedCropId解除只读
        $('#seedId').textbox('readonly', false);
        $('#seedCropId').textbox('readonly', false);
    }

    //编辑种子编辑页面
    function closeSeedForm() {
        $('#seedFormDialog').dialog('close');//关闭表单窗口
        clearSeedForm();
    }


    //加载种子编辑页面，并加载数据
    function loadSeedFormDialog() {
        var row = seedGrid.datagrid('getSelected');
        if (row) {
            $('#seedForm').form('load', row);//加载表单
            $('#seedFormDialog').dialog('open');//打开窗口
            //$seedId和$seedCropId设置只读
            $('#seedId').textbox('readonly', true);
            $('#seedCropId').textbox('readonly', true);
        } else {
            messageBox('提示', '请先选择一行数据，然后再尝试点击操作按钮！');
        }
    }

    //保存种子表单
    function saveSeedForm() {
        $('#seedForm').form('submit', {
            url: '<%=basePath%>/crop/save',
            onSubmit: function (param) {
                var isValid = $(this).form('validate');
                if (!isValid) {
                    return isValid;//返回false终止表单提交
                }
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.code == 10) {
                    $('#seedFormDialog').dialog('close');
                    seedGrid.datagrid('reload');
                    messageBox('消息', result.message);
                } else {
                    messageBox('错误', '操作失败');
                }
            }
        });
    }

    function clearImgExtData() {
        imgExtData.width = 0;
        imgExtData.height = 0;
        imgExtData.offsetX = 0;
        imgExtData.offsetY = 0;
    }
</script>
</body>
</html>