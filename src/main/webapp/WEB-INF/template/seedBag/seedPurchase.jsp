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
    <title>种子购买</title>

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
<div class="mask">
    <div id="seedStoreWindow">
        <table id="seedStoreGrid"></table>
    </div>
</div>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">
<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script>
    var seedStoreGrid;
    var onLoadSuccessObj;
    var landTypeList = {};
    var seedTypeList = {};

    var seedStoreGridView = $.extend({}, $.fn.datagrid.defaults.view, {
        renderRow: function (target, fields, frozen, rowIndex, rowData) {
            let imgUrl = '<%=basePath%>/ext/images/crops/' + rowData["cropId"] + '/5.png';
            let divStr = '<div id="mainBox_' + rowData['cropId'] + '" style="padding: 10px;border: 0;display: inline-block;">' +
                '<div style="display: table;width: 100px;border: gold 1px solid;border-collapse:collapse;">' +
                '<div style="display: table-row;"><div class="scrollbar-wrapper" style="display:table-cell;border: gold 1px solid;"><p style="padding:10px;height: 40px;">"' + rowData['tips'] + '</p></div></div>' +
                '<div id="mainBoxImg_' + rowData['cropId'] + '" style="display: table-row;"><div style="display:table-cell;"><img style="display:block;width: 150px;height: 140px;" src="' + imgUrl + '" alt="种子图片"></div></div>' +
                '<div style="display: table-row;"><div style="display:table-cell;padding: 10px;text-align: center;">' +
                '<input type="button" value="我要购买" onclick="purchaseSeed(' + rowData['cropId'] + ')"></div></div>' +
                '</div></div>';
            return $(divStr).prop('outerHTML');
        }
    });


    $(function () {
        $('#seedStoreWindow').dialog({
            title: '种子商店',
            width: '60%',
            height: '90%',
        });

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

        seedStoreGrid = $('#seedStoreGrid').datagrid({
            view: seedStoreGridView,
            title: '',
            width: '100%',
            height: '100%',
            singleSelect: 'true',
            url: '<%=basePath%>/seedBag/list',
            idField: 'id',
            nowrap: true,
            remoteSort: false,
            fit: true,
            fitColumns: true,
            pagination: true,
            pageSize: 5,
            pageList: [1, 3, 5],
            afterPageText: '页 共{pages}页',
            displayMsg: '当前显示{from}-{to}条记录，共{total}条记录',
            onLoadSuccess: createTipTool,
            columns: [[
                {
                    field: 'id',
                    title: 'id',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'cropId',
                    title: '种子ID',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'cropName',
                    title: '种子名称',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'grade',
                    title: '种子等级',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'growSeason',
                    title: '可收获季',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'salePrice',
                    title: '单个果实可获金币',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'purchasePrice',
                    title: '购买价格',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'harvestNum',
                    title: '单季收获',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'growthTimeOfEachSeason',
                    title: '成熟时间',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'harvestScore',
                    title: '积分收获',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'harvestExp',
                    title: '经验收获',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'tips',
                    title: '提示',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                },
                {
                    field: 'seedTypeCode',
                    title: '种子类型',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    formatter: function (value, row, index) {
                        return seedTypeList[value];
                    }
                },
                {
                    field: 'landTypeCode',
                    title: '土地类型',
                    halign: 'center',
                    align: 'center',
                    sortable: true,
                    width: $(this).width() * 0.1,
                    formatter: function (value, row, index) {
                        return landTypeList[value];
                    }
                },
            ]],
        });
    });

    function createTipTool(o) {
        if (o != null) {
            onLoadSuccessObj = o;
        }
        var obj;
        obj = onLoadSuccessObj;
        //重现渲染tip-tool
        var data = obj['rows'];
        for (let i = 0; i < data.length; i++) {
            let content = $('<div style="color: black;font-weight: bold;">')
                .append('名称：' + data[i]['cropName']).append('<br>')
                .append('级别：' + data[i]['grade']).append('<br>')
                .append('价格：' + data[i]['purchasePrice']).append('<br>')
                .append('类别：' + data[i]['seedTypeCode']).append('<br>')
                .append('土地：' + data[i]['landTypeCode']).append('<br>')
                .append('可收获季：' + data[i]['growSeason']).append('<br>')
                .append('成熟时间：' + data[i]['growthTimeOfEachSeason']).append('<br>')
                .append('单季收获：' + data[i]['harvestNum']).append('<br>')
                .append('经验收获：' + data[i]['harvestExp']).append('<br>')
                .append('单个果实可获金币：' + data[i]['salePrice']).append('<br>')
                .append('积分收获：' + data[i]['harvestScore']).append('<br>')
                .append('</div>');

            $('#mainBoxImg_' + data[i]['cropId']).tooltip({
                title: 'title',
                trackMouse: true,
                position: 'right',
                content: content,
                onShow: function () {
                    $(this).tooltip('tip').css({
                        backgroundColor: 'white',
                        borderColor: 'gray',
                    });
                }
            });

            var getPanel = seedStoreGrid.datagrid('getPanel');
            getPanel.height = 'auto';
            console.log('getPanel: ', getPanel);
        }

        $('.datagrid-body').css({
            'overflow': 'hidden',
            'text-align': 'center'
        });
        // $.parser.parse();//全局刷新
    }

    function purchaseSeed(cropId) {
        $.messager.confirm('确认', '你确定要购买该种子吗', function (r) {
            if (r) {
                request({
                    cropId: cropId,
                    seedNumber: 1
                }, 'post', '<%=basePath%>/user/purchaseSeed', false, function (result) {
                    if (result.code == 10) {
                        //刷新种子收纳袋页面
                        parent.document.querySelector('#main input[name="bottomSpace"]').contentWindow.location.reload();
                        messageBox('消息', '种子购买成功');
                    } else {
                        messageBox('消息', '种子购买失败');
                    }
                });
            }
        });
    }
</script>
</body>
</html>
