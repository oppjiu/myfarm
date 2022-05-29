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

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">
    <script type="text/javascript" src="<%=basePath%>/ext/js/helper.js?346t"></script>
</head>
<body>

<div id="controlBox">
    <span style="color: white">种子名称:</span><input id="genderSearch"/>
    <a href="#" class="easyui-linkbutton c1" iconCls="icon-search" onclick="doSearch()">查询</a>
    <a href="#" class="easyui-linkbutton c2" iconCls="icon-add" onclick="loadSaveForm()">添加</a>
    <a href="#" class="easyui-linkbutton c4" iconCls="icon-edit" onclick="loadForm()">编辑</a>
    <a href="#" class="easyui-linkbutton c3" iconCls="icon-remove"
       onclick="javascript:grid.edatagrid('cancelRow')">取消</a>
    <a href="#" class="easyui-linkbutton c5" iconCls="icon-cancel"
       onclick="javascript:grid.edatagrid('destroyRow')">删除</a>
</div>
<table id="grid"></table>
<div id="msgBox"></div>
<script type="text/javascript">
    var params = {
        id: '',
        mode: 'insert'
    };
    var grid;
    $(document).ready(function () {
        grid = $("#grid").edatagrid({
            title: '种子清单',
            /* data: someData,*/
            method: 'post',
            url: '<%=basePath%>/crop/list',
            striped: true,
            idField: 'id',
            fit: true,
            rownumbers: true,
            remoteSort: false,
            pagination: true,
            pageSize: 2,
            pageList: [1, 2, 3],
            afterPageText: '页 共{pages}页',
            displayMsg: '当前显示{from}-{to}条记录，共{total}条记录',
            columns: [[
                {
                    field: 'id',
                    title: 'ID',
                    halign: 'center',
                    align: 'center'
                },
                {
                    field: 'seedId',
                    title: '种子id',
                    halign: 'center',
                    align: 'center',
                    sortable: true
                },
                {
                    field: 'seedName',
                    title: '种子名称',
                    halign: 'center',
                    align: 'center',
                    sortable: true
                },
                {

                    field: 'growSeason',
                    title: 'X季作物',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                },
                {
                    field: 'level',
                    title: '种子等级',
                    halign: 'center',
                    align: 'center',
                    sortable: true
                },
                {
                    field: 'kind',
                    title: '种子类型',
                    halign: 'center',
                    align: 'center',
                    editor: {
                        type: 'combobox',
                        options: {
                            valueField: 'key',
                            textField: 'value',
                            data: [{key: '0', value: '普通'}, {key: '1', value: '传说'}, {key: '2', value: '神话'}],
                            panelHeight: 'auto',
                            required: true
                        }
                    },
                    sortable: true
                },
                {
                    field: 'gainExperience',
                    title: '可获经验',
                    halign: 'center',
                    align: 'center',
                    sortable: true
                },
                {
                    field: 'seasonTime',
                    title: '每季成熟所需时间',
                    halign: 'center',
                    align: 'center',
                    sortable: true
                },
                {
                    field: 'gainHarvestInEachRipeSeason',
                    title: '每季成熟可获得收成',
                    halign: 'center',
                    align: 'center',
                    sortable: true
                },
                {
                    field: 'seedPurchasePrice',
                    title: '种子采购价',
                    halign: 'center',
                    align: 'center',
                    sortable: true
                },
                {
                    field: 'unitPriceOfEachHarvest',
                    title: '每个收获的果实单价',
                    halign: 'center',
                    align: 'center',
                    sortable: true
                },
                {
                    field: 'landDemand',
                    title: '土地需求',
                    halign: 'center',
                    align: 'center',
                    editor: {
                        type: 'combobox',
                        options: {
                            valueField: 'key',
                            textField: 'value',
                            data: [{key: '0', value: '黄土地'}, {key: '1', value: '红土地'}, {key: '2', value: '黑土地'}],
                            panelHeight: 'auto',
                            required: true
                        }
                    },
                    sortable: true
                },
                {
                    field: 'gainPointsInEachRipeSeason',
                    title: '每季成熟可获得积分',
                    halign: 'center',
                    align: 'center',
                    sortable: true
                },
                {
                    field: 'tips',
                    title: '提示信息',
                    halign: 'center',
                    align: 'center',
                    sortable: true
                },
                {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    width: $(this).width() * 0.1,
                    formatter: function (value, row, index) {
                        return '<input type="button" value="成长阶段" onClick="livePeriod(' + row.id + ')">';
                    }
                }
            ]],
        });
    });

    function livePeriod(id) {
        alert(id);
    }

    function loadForm() {
        var row = grid.datagrid('getSelected');
        if (row) {
            params.id = row.id;
            params.mode = 'edit';
            $('#formEditContainer').dialog('open').dialog('setTitle', '编辑数据');
            $('#formEditSeed').form('load', row);
        } else {
            alert('请先选择一行数据，然后再尝试点击操作按钮！');
        }
    }

    function editSeedForm() {
        $('#formEditSeed').form('submit', {
            url: 'http://localhost:8080/student/save',
            onSubmit: function (param) {
                //param.id = params.id;
                //param.mode = params.mode;
                return $(this).form('validate');
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.code == 0) {
                    $('#formEditContainer').dialog('close');
                    grid.datagrid('reload');
                }
                $.messager.show({
                    title: '消息',
                    msg: result.msg
                });
            }
        });
    }

    function loadSaveForm() {
        params.mode = 'edit';
        $('#formSaveContainer').dialog('open').dialog('setTitle', '添加数据');
    }

    function saveSeedForm() {
        $('#formSaveSeed').form('submit', {
            url: '<%=basePath%>/student/save',
            onSubmit: function (param) {
                //param.id = params.id;
                //param.mode = params.mode;
                return $(this).form('validate');
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.code == 0) {
                    $('#formSaveContainer').dialog('close');
                    grid.datagrid('reload');
                }
                $.messager.show({
                    title: '消息',
                    msg: result.msg
                });
            }
        });
    }
</script>

<%-----------------------------------------------------------------------------------------------%>
<script type="text/javascript">
    var grid;

    function saveRecord() {
        $('#formEditor').form('submit', {
            url: '<%=basePath%>/seed/save',
            onSubmit: function (param) {
                return $(this).form('validate');
            },
            success: function (result) {
                var result = eval('(' + result + ')');
                if (result.code == 0) {
                    $('#formContainer').dialog('close');
                    grid.datagrid('reload');
                }
                $.messager.show({
                    title: "消息",
                    msg: result.msg
                });
            }
        });
    }

    function editRecord() {
        var row = grid.datagrid('getSelected');
        if (row) {
            $('#formContainer').dialog('open').dialog('center')

                .dialog('setTitle', '编辑数据');

            $('#formEditor').form('load', row);
        } else {
            $.messager.show({
                title: "消息",
                msg: "请先选择一行数据，然后再尝试点击操作按钮！"
            });
        }
    }

    function newRecord() {
        $('#formContainer').dialog('open').dialog('center').dialog('setTitle', '添加数据');
        $('#formEditor').form('clear');
        $('#formEditor').find("input[name='id']").val(0);
    }

</script>
</body>
</html>