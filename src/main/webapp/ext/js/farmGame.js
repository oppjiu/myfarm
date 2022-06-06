var basePath = $('#basePath').attr('value');
var wsBasePath = $('#wsBasePath').attr('value');
var websocket = null;

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
}

/*音乐*/
var soundWorm = $('#soundWorm')[0];
var soundKillWorm = $('#soundKillWorm')[0];
var soundCleanGrass = $('#soundCleanGrass')[0];
var soundHarvest = $('#soundHarvest')[0];
var soundNegative = $('#soundNegative')[0];
var soundPlantCrop = $('#soundPlantCrop')[0];
var soundPlantCropSuccess = $('#soundPlantCropSuccess')[0];

var landMap = new Map();//土地数据
/*为标准的3/4相对应的作物的宽高就需设置为相等的比例*/
/*土地宽和土地高成比例1/2*/
var landWidth = 150;//土地宽
var landHeight = 75;//土地高
/*paddingX和paddingY成比例1/2*/
var paddingX = 50;
var paddingY = 25;
/*土地的绝对位置*/
var positionX = 580;
var positionY = 80;
/*害虫的大小*/
var insectWidth = 100;
var insectHeight = 100;

$(function () {
    initWebSocket();//初始化视图
    /*种子收纳袋窗口*/
    $('#seedBagWindow').window({
        title: '种子收纳袋',
        width: '80%',
        height: '80%',
        inline: true,
        modal: true,
        closed: 'true',
    });

    /*设置土地*/
    let $landBox = $('#landBox');
    $landBox.css({
        left: positionX + 'px',
        top: positionY + 'px'
    });
    request({}, 'post', basePath + '/game/initiateFarmView', false, function (result) {
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
                    $landBox.append(initClickBox(i, j, land));
                    $landBox.append(initLandImg(i, j, land));
                    saveGameData(i, j, land, cropGrow);
                    //土地有作物
                    if (land.hasCrop == 1) {
                        $landBox.append(initCropImg(i, j, land, cropGrow));
                        //作物有害虫
                        if (land.hasInsect == 1) {
                            $landBox.append(initInsectImg(i, j, land));
                        }
                    }
                }
            }
        } else {
            messageBox('消息', '游戏初始化失败');
        }
    });

    /*动态绑定鼠标进入事件*/
    $(document).on('mouseenter', '.clickBox', function () {
        let land = landMap.get(parseInt(this.dataset.landid));
        // 土地没有作物
        if (land.hasCrop == 0) {
            $('.clickBox').css({
                cursor: 'url(ext/cursor/plantCrop.cur), wait',
            });
        } else {
            //作物成熟
            if (land.isMature == 1) {
                // $('.clickBox').css({
                //     cursor: 'url(ext/cursor/harvest.cur), wait',
                // });
                $('.clickBox').css({
                    cursor: 'progress',
                });
            } else if (land.isWithered == 1) { //作物枯萎
                // $('.clickBox').css({
                //     cursor: 'url(ext/cursor/cleanGrass.cur), wait',
                // });
                $('.clickBox').css({
                    cursor: 'cell',
                });
            } else { //作物成长中
                // $('.clickBox').css({
                //     cursor: 'url(ext/cursor/waitGrow.cur), wait',
                // });
                $('.clickBox').css({
                    cursor: 'copy',
                });
            }
            //作物生虫
            if (land.hasInsect == 1) {
                // $('.clickBox').css({
                //     cursor: 'url(ext/cursor/killWorm.cur), wait',
                // });
                $('.clickBox').css({
                    cursor: 'move',
                });
            }
        }
    });

    /*动态绑定鼠标划出事件*/
    $(document).on('mouseleave', '.clickBox', function () {
        $('.clickBox').css({
            cursor: 'pointer',
        });
    });

    /*动态绑定点击事件*/
    $(document).on('click', '.clickBox', function () {
        let land = landMap.get(parseInt(this.dataset.landid));
        //土地没有作物
        if (land.hasCrop == 0) {
            plantCrop(land.landId);
        } else {
            if (land.isMature == 1) {
                //作物成熟
                harvest(land.landId);
            } else if (land.isWithered == 1) {
                //作物枯萎
                cleanGrass(land.landId);
            }
            //作物生虫
            if (land.hasInsect == 1) {
                killWorm(land.landId);
            }
        }
    });
    initTipTool();//初始化提示框
});

function initWebSocket() {
    if ('WebSocket' in window) {
        //Websocket的连接
        websocket = new WebSocket(wsBasePath + '/game/action');//WebSocket对应的地址
    } else if ('MozWebSocket' in window) {
        //Websocket的连接
        websocket = new MozWebSocket(wsBasePath + '/game/action');//SockJS对应的地址
    } else {
        //SockJS的连接
        websocket = new SockJS(wsBasePath + '/game/action');//SockJS对应的地址
    }

    websocket.onopen = function onOpen(evt) {
        console.log('连接打开：', evt);
    };
    websocket.onmessage = function onMessage(evt) {
        let data = evt.data;
        //是游戏更新数据
        if (data['code'] != null && data['code'] == 100) {
            console.log('evt.data', evt.data);
            let land = data['land'];
            let cropGrowView = data['cropGrowView'];
            updateData(land, cropGrowView);
        }
    };
    websocket.onerror = function onError(evt) {
        console.log('出现错误：', evt);
    }
    ;
    websocket.onclose = function onClose(evt) {
        console.log('连接关闭：', evt);
    };
}

/*页面关闭websocket断开连接*/
window.close = function () {
    websocket.onclose();
}


function updateData(landData, cropGrowData) {
    let land = landMap.get(landData['landId']);
    let i = land.get('i');
    let j = land.get('j');

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

    landMap.set(land.landId, land);//存储数据

    //更新视图
    updateCropImg(i, j, landData, cropGrowData);
    updateInsectImg(i, j, landData);
}


function openSeedBagWindow() {
    let $seedBagWindow = $('#seedBagWindow');
    $seedBagWindow.window('open');
    $seedBagWindow.window('refresh', basePath + '/page/seedBagPage');
}

function playSound(id) {
    switch (id) {
        case 1:
            soundWorm.play();
            break;
        case 2:
            soundKillWorm.play();
            break;
        case 3:
            soundCleanGrass.play();
            break;
        case 4:
            soundHarvest.play();
            break;
        case 5:
            soundNegative.play();
            break;
        case 6:
            soundPlantCrop.play();
            break;
        case 7:
            soundPlantCropSuccess.play();
            break;
    }
}

/**
 * 存储土地动态信息
 * @param i
 * @param j
 * @param landData
 * @param cropGrowData
 */
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

    if (cropGrowData != null) {
        land.cropState = cropGrowData['cropState'];
        land.cropName = cropGrowData['cropName'];
    }

    landMap.set(land.landId, land);
}

function plantCrop(landId) {
    console.log('landId', landId);
    let $seedBagWindow = $('#seedBagWindow');
    $seedBagWindow.window('open');
    $seedBagWindow.window('refresh', basePath + '/page/seedBagPage');
    request({}, 'post', '');
}

function killWorm(insectId) {
    console.log('landId', insectId);
    request({}, 'post', '');
}

function harvest(landId) {
    console.log('landId', landId);
    request({}, 'post', '');
}

function cleanGrass(landId) {
    console.log('landId', landId);
    request({}, 'post', basePath + '/user/killWorm');
}

/**
 * 初始化提示框
 */
function initTipTool() {
    $('.clickBox').tooltip({
        trackMouse: true,
        position: 'right',
        showDelay: 1000,
        onShow: function () {
            //根据land类动态刷新提示信息
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

function initClickBox(i, j, land) {
    let $clickBox = $('<div data-landTypeCode="' + land.landTypeCode + '" data-landId="' + land.landId + '" class="clickBox" id="clickBox_' + land.landId + '">');
    $clickBox.css({
        width: landWidth + 'px',
        height: landHeight + 'px',
        left: fixPositionX(i, j) + 'px',
        top: fixPositionY(i, j) + 'px',
        zIndex: 30
    });

    return $clickBox;
}

function initLandImg(i, j, land) {
    let url = basePath + '/ext/images/land/' + land.landTypeCode + '.png'
    //根据land的landTypeCode生成相应的土地类型
    let $land = $('<img data-landTypeCode="' + land.landTypeCode + '" class="land" id="land_' + land.landId + '" src="' + url + '" alt="土地">');
    $land.css({
        width: landWidth + 'px',
        height: landHeight + 'px',
        left: fixPositionX(i, j) + 'px',
        top: fixPositionY(i, j) + 'px',
        zIndex: 1
    });
    return $land;

}

function initCropImg(i, j, land, cropGrow) {
    let url;
    if (land.nowCropGrowStage == 6 || land.nowCropGrowStage == 0) {
        //枯草期或者种子阶段
        url = basePath + '/ext/images/crops/basic/' + land.nowCropGrowStage + '.png';
    } else {
        //正常生长阶段
        url = basePath + '/ext/images/crops/' + land.cropId + '/' + land.nowCropGrowStage + '.png';
    }
    let $crop = $('<img data-cropId="' + land.cropId + '" data-nowCropGrowStage="' + land.nowCropGrowStage + '" class="crop" id="crop_' + land.landId + '" src="' + url + '" alt="作物">');
    let picOffsetX;
    let picOffsetY;
    let picWidth;
    let picHeight;
    if (cropGrow != null) {
        picOffsetX = cropGrow['picOffsetX'];
        picOffsetY = cropGrow['picOffsetY'];
        picWidth = cropGrow['picWidth'];
        picHeight = cropGrow['picHeight'];
    }

    $crop.css({
        width: picWidth * 3 / 4 + 'px',
        height: picHeight * 3 / 4 + 'px',
        left: fixPositionX(i, j) + (picOffsetX - 10) * 3 / 4 + 'px', // (picOffsetX - 10) * 3 / 4修正
        top: fixPositionY(i, j) + (picOffsetY - 200 + 15) * 3 / 4 + 'px', // (picOffsetY - 200 + 15) * 3 / 4修正
        zIndex: 10
    });
    return $crop;
}

function initInsectImg(i, j, land) {
    let url = basePath + '/ext/images/other/insect.png';
    let $insect = $('<img class="insect" id="insect_' + land.landId + '" src="' + url + '" alt="害虫">');
    $insect.css({
        width: insectWidth + 'px',
        height: insectHeight + 'px',
        left: fixPositionX(i, j) - 20 + 'px', //-10 修正
        top: fixPositionY(i, j) - 30 + 'px', //-30 修正
        zIndex: 20
    });
    return $insect;
}

/**
 * 更新作物视图
 * @param i
 * @param j
 * @param land
 * @param cropGrow
 */
function updateCropImg(i, j, land, cropGrow) {
    //没有作物删除
    if (land['hasCrop'] == 0) {
        let $crop = $('#crop_' + land.landId);
        if ($crop != null) {
            $crop.remove();
        }
    } else {
        let $crop = $('#crop_' + land.landId);
        let url;
        //更新作物图片
        if (land.nowCropGrowStage == 6 || land.nowCropGrowStage == 0) {
            //枯草期或者种子阶段
            url = basePath + '/ext/images/crops/basic/' + land.nowCropGrowStage + '.png';
        } else {
            //正常生长阶段
            url = basePath + '/ext/images/crops/' + land.cropId + '/' + land.nowCropGrowStage + '.png';
        }
        //如果没有视图创建视图
        if ($crop == null) {
            $crop = $('<img data-cropId="' + land.cropId + '" data-nowCropGrowStage="' + land.nowCropGrowStage + '" class="crop" id="crop_' + land.landId + '" src="' + url + '" alt="作物">');
        } else {
            $crop.src = url;
        }

        let picOffsetX;
        let picOffsetY;
        let picWidth;
        let picHeight;
        if (cropGrow != null) {
            picOffsetX = cropGrow['picOffsetX'];
            picOffsetY = cropGrow['picOffsetY'];
            picWidth = cropGrow['picWidth'];
            picHeight = cropGrow['picHeight'];
        }
        $crop.css({
            width: picWidth * 3 / 4 + 'px',
            height: picHeight * 3 / 4 + 'px',
            left: fixPositionX(i, j) + (picOffsetX - 10) * 3 / 4 + 'px', // (picOffsetX - 10) * 3 / 4修正
            top: fixPositionY(i, j) + (picOffsetY - 200 + 15) * 3 / 4 + 'px', // (picOffsetY - 200 + 15) * 3 / 4修正
            zIndex: 10
        });
        $('#landBox').append($crop);
    }
}

/**
 * 更新害虫视图
 * @param i
 * @param j
 * @param land
 */
function updateInsectImg(i, j, land) {
    //没有虫害删除
    if (land['hasInsect'] == 0) {
        let $insect = $('#insect_' + land.landId);
        if ($insect != null) {
            $insect.remove();
        }
    } else {
        let $insect = $('#insect_' + land.landId);
        //判断是否有图片
        if ($insect == null) {
            let url = basePath + '/ext/images/other/insect.png';
            $insect = $('<img class="insect" id="insect_' + land.landId + '" src="' + url + '" alt="害虫">');//创建图片
        }
        let hasInsect;
        $insect.css({
            width: insectWidth + 'px',
            height: insectHeight + 'px',
            left: fixPositionX(i, j) - 20 + 'px', //-10 修正
            top: fixPositionY(i, j) - 30 + 'px', //-30 修正
            zIndex: 20
        });
        $('#landBox').append($insect);
    }
}

function fixPositionX(i, j) {
    return (j * (landWidth + paddingX) / 2 - i * (landWidth + paddingX) / 2);
}

function fixPositionY(i, j) {
    return ((i + j) * (landHeight + paddingY) / 2 + 30);
}

/**
 * 时间戳转换日期
 * @param time
 * @returns {string}
 */
function getLocalTime(time) {
    var date = new Date(time + 8 * 3600 * 1000);
    return date.toJSON().substr(0, 19).replace('T', ' ');
}