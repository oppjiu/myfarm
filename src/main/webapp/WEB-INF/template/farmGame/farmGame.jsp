<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    String wsBasePath = "ws://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>我的农场</title>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/green/easyui.css?t564">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/easyui/themes/color.css">

    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/plugins/jquery.draggable.js"></script>
    <script type="text/javascript" src="<%=basePath%>/ext/easyui/locale/easyui-lang-zh_CN.js"></script>

    <basePath id="basePath" value="<%=basePath%>"></basePath>
    <basePath id="wsBasePath" value="<%=wsBasePath%>"></basePath>

    <style>
        #landBox {
            position: absolute;
        }

        .land {
            position: absolute;
        }

        .insect {
            position: absolute;
        }

        .crop {
            position: absolute;
        }

        .clickBox {
            position: absolute;
            /*background-color: rgba(255, 127, 80, 0.5);*/
            clip-path: polygon(50% 0, 100% 50%, 50% 100%, 0 50%);
        }
    </style>
</head>
<body>
<div id="landBox"></div>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext/css/farm.css">
<script type="text/javascript" src="<%=basePath%>/ext/js/helper.js"></script>
<script>
    /**
     * 土地类
     */
    class Land {
        i;//前端土地标识
        j;//前端土地标识

        landId;
        hasCrop;
        hasInsect;
        isWithered;
        isMature;
        output;
        nowCropGrowStage;
        nextCropGrowStage;
        growingSeason;
        growthTimeOfEachState;
        stateEndTime;

        cropId;
        landTypeCode;
        cropState;
        cropName;

        constructor(landId, hasCrop, hasInsect, isWithered, isMature,
                    output, nowCropGrowStage, nextCropGrowStage,
                    growingSeason, growthTimeOfEachState, stateEndTime,) {
            this.landId = landId;
            this.hasCrop = hasCrop;
            this.hasInsect = hasInsect;
            this.isWithered = isWithered;
            this.isMature = isMature;
            this.output = output;
            this.nowCropGrowStage = nowCropGrowStage;
            this.nextCropGrowStage = nextCropGrowStage;
            this.growingSeason = growingSeason;
            this.growthTimeOfEachState = growthTimeOfEachState;
            this.stateEndTime = stateEndTime;
        }
    }

    var landMap = new Map();//土地数据
    /*为标准的3/4相对应的作物的宽高就需设置为相等的比例*/
    /*土地宽和土地高成比例1/2*/
    var landWidth = 150;//土地宽
    var landHeight = 75;//土地高
    /*paddingX和paddingY成比例1/2*/
    var paddingX = 50;
    var paddingY = 25;
    /*土地的绝对位置*/
    var positionX = 550;
    var positionY = 120;
    /*害虫的大小*/
    var insectWidth = 100;
    var insectHeight = 100;

    $(function () {
        let $landBox = $('#landBox');
        $landBox.css({
            left: positionX + 'px',
            top: positionY + 'px'
        });
        /**
         * 获取数据
         */
        request({}, 'post', 'data/farm.json', false, function (result) {
            console.log('result.data: ', result.data);
            //初始化游戏
            if (result.code == 10) {
                let data = result.data,
                    count = 0;//第几块土地
                for (let i = 0; i < 4; i++) {
                    for (let j = 0; j < 6; j++) {
                        let land = data[count]['land'],//土地数据
                            cropGrow = data[count]['cropGrowView'];//cropGrow数据
                        count++;
                        $landBox.append(initClickBox(i, j, land, cropGrow));
                        $landBox.append(initLandImg(i, j, land));
                        saveGameData(i, j, land, cropGrow);
                        //土地有作物
                        if (land.hasCrop == 1) {
                            $landBox.append(initCropImg(i, j, land));
                            //作物有害虫
                            if (true/*land.hasInsect == 1*/) {
                                $landBox.append(initInsectImg(i, j, land));
                            }
                        }
                    }
                }
            } else {
                messageBox('消息', '游戏初始化失败');
            }
        });

        /*绑定鼠标进入事件*/
        $(document).on('mouseenter', '.clickBox', function () {
            let land = landMap.get(parseInt(this.dataset.landid));
            //土地没有作物
            // if (land.hasCrop == 0) {
            //     $('.clickBox').css({
            //         cursor: 'url(ext/cursor/可播种.cur), wait',
            //     });
            // } else {
            //     //作物成熟
            //     if (land.isMature == 1) {
            //         $('.clickBox').css({
            //             cursor: 'url(ext/cursor/可播种.cur), wait',
            //         });
            //     } else if (land.isWithered == 1) { //作物枯萎
            //         $('.clickBox').css({
            //             cursor: 'url(ext/cursor/可播种.cur), wait',
            //         });
            //     } else { //作物成长中
            //         $('.clickBox').css({
            //             cursor: 'url(ext/cursor/可播种.cur), wait',
            //         });
            //     }
            //     //作物生虫
            //     if (land.hasInsect == 1) {
            //         $('.clickBox').css({
            //             cursor: 'url(ext/cursor/可播种.cur), wait',
            //         });
            //     }
            // }

            $('#clickBox_1').css({
                cursor: 'url(ext/cursor/可收获.cur), wait',
            });
            $('#clickBox_2').css({
                cursor: 'url(ext/cursor/可除虫.cur), wait',
            });
            $('#clickBox_3').css({
                cursor: 'url(ext/cursor/杀虫剂.cur), wait',
            });
            $('#clickBox_4').css({
                cursor: 'url(ext/cursor/生长等待.cur), wait',
            });
            $('#clickBox_5').css({
                cursor: 'url(ext/cursor/除枯叶.cur), wait',
            });
            $('#clickBox_6').css({
                cursor: 'url(ext/cursor/魔法棒.cur), wait',
            });
        });
        /*绑定鼠标划出事件*/
        $(document).on('mouseleave', '.clickBox', function () {
            $('.clickBox').css({
                cursor: 'pointer',
            });
        });

        /*绑定点击事件*/
        $(document).on('click', '.clickBox', function () {
            let land = landMap.get(parseInt(this.dataset.landid));
            //土地没有作物
            if (land.hasCrop == 0) {
                plantCrop(land.landId);
            } else {
                //作物成熟
                if (land.isMature == 1) {
                    harvest(land.landId);
                }
                //作物枯萎
                if (land.isWithered == 1) {
                    cleanGrass(land.landId);
                }
                //作物生虫
                if (land.hasInsect == 1) {
                    killWorm(land.landId);
                }
            }
        });
        initTipTool();
    });

    function saveGameData(i, j, landData, cropGrowData) {
        let land = new Land();
        land.i = i;
        land.j = j;

        land.landId = landData['landId'];
        land.stateEndTime = landData['stateEndTime'];
        land.hasInsect = landData['hasInsect'];
        land.output = landData['output'];
        land.isMature = landData['isMature'];
        land.growingSeason = landData['growingSeason'];
        land.nowCropGrowStage = landData['nowCropGrowStage'];
        land.hasCrop = landData['hasCrop'];
        land.isWithered = landData['isWithered'];
        land.nextCropGrowStage = landData['nextCropGrowStage'];
        land.growthTimeOfEachState = landData['growthTimeOfEachState'];

        land.cropId = landData['cropId'];
        land.landTypeCode = landData['landTypeCode'];

        land.cropState = cropGrowData['cropState'];
        land.cropName = cropGrowData['cropName'];

        landMap.set(land.landId, land);
    }

    function plantCrop(landId) {
        console.log('landId', landId);
        // request({}, 'post', '')
    }

    function killWorm(insectId) {
        console.log('landId', insectId);
        // request({}, 'post', '')
    }

    function harvest(landId) {
        console.log('landId', landId);
        // request({}, 'post', '')
    }

    function cleanGrass(landId) {
        console.log('landId', landId);
        request({}, 'post', '<%=basePath%>/user/killWorm')
    }

    function initTipTool() {
        $('.clickBox').tooltip({
            trackMouse: true,
            position: 'right',
            showDelay: 1000,
            onShow: function () {
                let land = landMap.get(parseInt(this.dataset.landid));
                let content = $('<div style="color: black;font-weight: bold;"></div>')
                    .append('名称：' + land['cropName']).append('<br>')
                    .append('状态：' + land['cropState']).append('<br>')
                    .append('产量：' + land['output']).append('<br>')
                    .append('时间：' + getLocalTime(land['stateEndTime'])).append('<br>');
                $(this).tooltip({
                    content: content
                });
                $(this).tooltip('tip').css({
                    backgroundColor: 'white',
                    borderColor: 'gray',
                });
            }
        });
    }

    function initClickBox(i, j, land, cropGrow) {
        let $clickBox = $('<div data-landTypeCode="' + land.landTypeCode + '" data-landId="' + land.landId + '" class="clickBox" id="clickBox_' + land.landId + '">');
        $clickBox.css({
            width: landWidth + 'px',
            height: landHeight + 'px',
            left: fixPositionX(i, j) + 'px',
            top: fixPositionY(i, j) + 'px',
            zIndex: 1000
        });

        return $clickBox;
    }

    function initLandImg(i, j, land) {
        //根据land的landTypeCode生成相应的土地类型
        let $land = $('<img data-landTypeCode="' + land.landTypeCode + '" class="land" id="land_' + land.landId + '" src="ext/images/land/' + land.landTypeCode + '.png" alt="土地">');
        $land.css({
            width: landWidth + 'px',
            height: landHeight + 'px',
            left: fixPositionX(i, j) + 'px',
            top: fixPositionY(i, j) + 'px',
            zIndex: 1
        });
        return $land;

    }

    function initCropImg(i, j, land) {
        // let $crop = $('<img data-cropId="' + land.cropId + '" data-nowCropGrowStage="' + land.nowCropGrowStage + '" class="crop" id="crop_' + landId + '" onclick="plantCrop(\'' + landId + '\')" src="ext/images/crops/' + land.cropId + '/' + land.nowCropGrowStage + '.png" alt="害虫">');
// let $crop = $('<img data-cropId="' + land.cropId + '" data-nowCropGrowStage="' + land.nowCropGrowStage + '" class="crop" id="crop_' + landId + '" onclick="plantCrop(\'' + landId + '\')" src="ext/images/crops/1/5.png" alt="作物">');
// let $crop = $('<img data-cropId="' + land.cropId + '" data-nowCropGrowStage="' + land.nowCropGrowStage + '" class="crop" id="crop_' + landId + '" onclick="plantCrop(\'' + landId + '\')" src="ext/images/crops/basic/9.png" alt="作物">');
// let $crop = $('<img data-cropId="' + land.cropId + '" data-nowCropGrowStage="' + land.nowCropGrowStage + '" class="crop" id="crop_' + landId + '" onclick="plantCrop(\'' + landId + '\')" src="ext/images/crops/basic/0.png" alt="作物">');
        let $crop = $('<img data-cropId="' + land.cropId + '" data-nowCropGrowStage="' + land.nowCropGrowStage + '" class="crop" id="crop_' + land.landId + '" src="ext/images/crops/418/5.png" alt="作物">');
        let picOffsetX = 40; //cropGrow['offsetX']
        let picOffsetY = 63; //cropGrow['offsetY']
        let picWidth = 141; //cropGrow['width']
        let picHeight = 200; //cropGrow['height']
        $crop.css({
            width: picWidth * 3 / 4 + 'px',
            height: picHeight * 3 / 4 + 'px',
            left: fixPositionX(i, j) + (picOffsetX - 10) * 3 / 4 + 'px', // (picOffsetX - 10) * 3 / 4修正
            top: fixPositionY(i, j) + (picOffsetY - 200 + 15) * 3 / 4 + 'px', // (picOffsetY - 200 + 15) * 3 / 4修正
            zIndex: 10
        })
        return $crop;
    }

    function initInsectImg(i, j, land) {
        let $insect = $('<img class="insect" id="insect_' + land.landId + '" src="ext/images/other/insect.png" alt="害虫">');
        $insect.css({
            width: insectWidth + 'px',
            height: insectHeight + 'px',
            left: fixPositionX(i, j) - 20 + 'px', //-10 修正
            top: fixPositionY(i, j) - 30 + 'px', //-30 修正
            zIndex: 100
        });
        return $insect;
    }

    function fixPositionX(i, j) {
        return (j * (landWidth + paddingX) / 2 - i * (landWidth + paddingX) / 2);
    }

    function fixPositionY(i, j) {
        return ((i + j) * (landHeight + paddingY) / 2 + 30);
    }

    function getLocalTime(time) {
        var date = new Date(time + 8 * 3600 * 1000);
        return date.toJSON().substr(0, 19).replace('T', ' ');
    }
</script>
</body>
</html>

