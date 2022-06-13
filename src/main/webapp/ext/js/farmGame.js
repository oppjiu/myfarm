var basePath = $('#basePath').attr('value');
var wsBasePath = $('#wsBasePath').attr('value');
var websocket = null;
var farmGameLandId;

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
    harvestNum
}

/*音乐*/
var soundWorm = $('#soundWorm')[0];
var soundKillWorm = $('#soundKillWorm')[0];
var soundCleanGrass = $('#soundCleanGrass')[0];
var soundHarvest = $('#soundHarvest')[0];
var soundNegative = $('#soundNegative')[0];
var soundPlantCrop = $('#soundPlantCrop')[0];
var soundPlantCropSuccess = $('#soundPlantCropSuccess')[0];
var soundPlantCropSuccessEasterEgg = $('#plantCropSuccessEasterEgg')[0];
var soundHarvestEasterEgg = $('#soundHarvestEasterEgg')[0];
var soundCleanGrassEasterEgg = $('#soundCleanGrassEasterEgg')[0];

var landMap = new Map();//土地数据
/*为标准的3/4相对应的作物的宽高就需设置为相等的比例*/
/*土地宽和土地高成比例1/2*/
var landWidth = 100;//土地宽
var landHeight = 50;//土地高
/*paddingX和paddingY成比例1/2*/
var paddingX = 30;
var paddingY = 15;
/*土地的绝对位置*/
var positionX = 780;
var positionY = 200;
/*害虫的大小*/
var insectWidth = 60;
var insectHeight = 60;

$(function () {
    parent.document.getElementById("main").rows = "60,*";//设置框架

    initWebSocket();//初始化视图
    /*种子收纳袋窗口*/
    $('#seedBagDialog').dialog({
        title: '种子收纳袋',
        width: '900',
        height: 240,
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
        console.log('初始化游戏数据: ', result.data);
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
            $(this).css("cursor", "url(" + basePath + "/ext/cursor/plantCrop.cur) 16 16,default");
        } else {
            //作物成熟
            if (land.isMature == 1) {
                $(this).css("cursor", "url(" + basePath + "/ext/cursor/harvest.cur) 16 16,default");
            } else if (land.isWithered == 1) { //作物枯萎
                $(this).css("cursor", "url(" + basePath + "/ext/cursor/cleanGrass.cur) 16 16,default");
            } else { //作物成长中
                $(this).css("cursor", "url(" + basePath + "/ext/cursor/waitGrow.cur) 16 16,default");
            }
            //作物生虫
            if (land.hasInsect == 1) {
                $(this).css("cursor", "url(" + basePath + "/ext/cursor/killWorm.cur) 16 16,default");
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
            //作物生虫
            if (land.hasInsect == 1) {
                killWorm(land.landId);
            } else {
                if (land.isMature == 1) {
                    //作物成熟
                    harvest(land.landId);
                } else if (land.isWithered == 1) {
                    //作物枯萎
                    cleanGrass(land.landId);
                }
            }
        }
    });
    /*动态绑定种子收纳袋点击事件*/
    $(document).on('click', '.cropImg', function () {
        request({
            landId: parseInt(farmGameLandId),
            cropId: parseInt(this.dataset.cropid)
        }, 'post', basePath + '/user/plantSeed', true, function (result) {
            if (result.code == 10) {
                let data = result.data;
                let cropGrow = data['cropGrowView'];
                updateData(data['land'], data['cropGrowView']);
                messageBox('提示', '种植成功');
                /*彩蛋*/
                if (cropGrow['cropId'] == 110) {
                    soundPlantCropSuccessEasterEgg.play();
                } else {
                    soundPlantCropSuccess.play();
                }
                $('#seedBagDialog').dialog('close');//关闭窗口
            } else {
                soundNegative.play();//播放声音
                messageBox('提示', '土地类型不匹配');
            }
        });
    });

    initTipTool();//初始化土地提示框
    console.log('landMap: ', landMap);
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
        let data = JSON.parse(evt.data);
        //游戏更新数据
        if (data['code'] != null && data['code'] == 100) {
            let land = data['land'];
            let cropGrowView = data['cropGrowView'];
            let user = data['user'];
            updateData(land, cropGrowView, user);
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

/**
 * 更新视图并保存数据
 * @param landData 土地数据
 * @param cropGrowData 生长阶段数据
 */
function updateData(landData, cropGrowData, user) {
    let land = landMap.get(landData['landId']);
    let i = land['i'];
    let j = land['j'];

    //更新视图
    updateCropImg(i, j, landData, cropGrowData);
    updateInsectImg(i, j, landData);

    land.landId = landData['landId'];
    land.stateEndTime = landData['stateEndTime'];
    land.hasInsect = landData['hasInsect'];
    land.hasCrop = landData['hasCrop'];
    land.isWithered = landData['isWithered'];
    land.isMature = landData['isMature'];
    land.output = landData['output'];
    land.growingSeason = landData['growingSeason'];
    land.nowCropGrowStage = landData['nowCropGrowStage'];
    land.nextCropGrowStage = landData['nextCropGrowStage'];
    land.growthTimeOfEachState = landData['growthTimeOfEachState'];

    land.cropId = landData['cropId'];
    land.landTypeCode = landData['landTypeCode'];

    if (cropGrowData != null) {
        land.cropState = cropGrowData['cropState'];
        land.cropName = cropGrowData['cropName'];
        land.harvestNum = cropGrowData['harvestNum'];
    }

    if (user != null) {
        //更新用户信息显示数据
        updateUserBoxData({
            userinfoExp: user['exp'],
            userinfoMoney: user['money'],
            userinfoPoint: user['point'],
        }, basePath)
        updateUserBoxView(basePath);
    }
    landMap.set(land.landId, land);//存储数据
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
    farmGameLandId = landId;
    soundPlantCrop.play();
    let $seedBagWindow = $('#seedBagDialog');
    $seedBagWindow.dialog('open');
    $seedBagWindow.dialog('refresh', basePath + '/page/seedBagPage');
}

function killWorm(landId) {
    console.log('killWorm.landId: ', landId);
    request({landId: landId}, 'post', basePath + '/user/killWorm', true, function (result) {
        if (result.code == 10) {
            let data = result.data;
            let user = data['user'];
            let $msg = $('<div>' + '杀虫成功' + '</div>')
                .append('<p>' + ' 获得经验：' + user['exp'] + '</p>')
                .append('<p>' + ' 获得积分：' + user['point'] + '</p>')
                .append('<p>' + ' 获得金钱：' + user['money'] + '</p>');
            messageBox('提示', $msg);
            soundKillWorm.play();
            updateData(data['land'], data['cropGrowView']);
        } else {
            soundNegative.play();
            messageBox('提示', '杀虫失败');
        }
    });
}

function harvest(landId) {
    console.log('harvest.landId: ', landId);
    request({landId: landId}, 'post', basePath + '/user/harvest', true, function (result) {
        if (result.code == 10) {
            let data = result.data;
            let user = data['user'];
            let cropGrow = data['cropGrowView'];
            updateData(data['land'], data['cropGrowView']);
            let $msg = $('<div>' + '收获成功' + '</div>')
                .append('<p>' + ' 获得经验：' + user['exp'] + '</p>')
                .append('<p>' + ' 获得积分：' + user['point'] + '</p>')
                .append('<p>' + ' 获得金钱：' + user['money'] + '</p>');
            messageBox('提示', $msg);
            /*彩蛋*/
            if (cropGrow['cropId'] == 110) {
                soundHarvestEasterEgg.play();
            } else {
                soundHarvest.play();
            }
        } else {
            soundNegative.play();
            messageBox('提示', '收获失败');
        }
    });
}

function cleanGrass(landId) {
    console.log('cleanGrass.landId: ', landId);
    request({landId: landId}, 'post', basePath + '/user/cleanGrass', true, function (result) {
        //重置前端数据
        landId = new Land();
        if (result.code == 10) {
            let data = result.data;
            let user = data['user'];
            let cropGrow = data['cropGrowView'];
            updateData(data['land'], data['cropGrowView']);
            let $msg = $('<div>' + '除草成功' + '</div>')
                .append('<p>' + ' 获得经验：' + user['exp'] + '</p>')
                .append('<p>' + ' 获得积分：' + user['point'] + '</p>')
                .append('<p>' + ' 获得金钱：' + user['money'] + '</p>');
            messageBox('提示', $msg);
            /*彩蛋*/
            if (cropGrow['cropId'] == 110) {
                soundCleanGrassEasterEgg.play();
            } else {
                soundCleanGrass.play();
            }
        } else {
            soundNegative.play();
            messageBox('提示', '除草失败');
        }
    });
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
            let cropName = land['cropName'];
            let cropState = land['cropState'];
            let output = land['output'];
            let stateEndTime = land['stateEndTime'];
            let $content = $('<div style="color: black;font-weight: bold;"></div>')
            if (cropName != null && cropName != undefined) {
                $content.append('名称：' + cropName);
                $content.append('<br>');
            } else {
                $content.append('名称：未知');
                $content.append('<br>');
            }
            if (cropState != null && cropState != undefined) {
                $content.append('状态：' + cropState);
                $content.append('<br>');
            } else {
                $content.append('名称：未知');
                $content.append('<br>');
            }
            if (output != null && output != undefined) {
                $content.append('产量：' + output);
                $content.append('<br>');
            } else {
                $content.append('名称：未知');
                $content.append('<br>');
            }
            if (stateEndTime != null && stateEndTime != undefined) {
                if (!isNaN(stateEndTime)) {
                    $content.append('时间：' + formatDate(stateEndTime));
                    $content.append('<br>');
                } else {
                    $content.append('时间：' + formatDate(stateEndTime.time));
                    $content.append('<br>');
                }
            } else {
                $content.append('名称：未知');
                $content.append('<br>');
            }
            $(this).tooltip({
                content: $content
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
        zIndex: 5
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
        width: picWidth * 1 / 2 + 'px',
        height: picHeight * 1 / 2 + 'px',
        left: fixPositionX(i, j) + (picOffsetX - 10) * 1 / 2 + 'px', // (picOffsetX - 10) * 3 / 4修正
        top: fixPositionY(i, j) + (picOffsetY - 200 + 15) * 1 / 2 + 'px', // (picOffsetY - 200 + 15) * 3 / 4修正
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
        left: fixPositionX(i, j) - 10 + 'px', //-10 修正
        top: fixPositionY(i, j) - 10 + 'px', //-30 修正
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
    let $crop = $('#crop_' + land.landId);
    if (land['hasCrop'] == 0) {
        if ($crop[0]) {
            $crop.remove();
        }
    } else {
        let url;
        //更新作物图片
        if (land.nowCropGrowStage == 6 || land.nowCropGrowStage == 0) {
            //枯草期或者种子阶段
            url = basePath + '/ext/images/crops/basic/' + land.nowCropGrowStage + '.png';
        } else {
            //正常生长阶段
            url = basePath + '/ext/images/crops/' + land.cropId + '/' + land.nowCropGrowStage + '.png';
        }
        //调整样式
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
        if (!$crop[0]) {
            $crop = $('<img data-cropId="' + land.cropId + '" data-nowCropGrowStage="' + land.nowCropGrowStage + '" class="crop" id="crop_' + land.landId + '" src="' + url + '" alt="作物">');
            $crop.css({
                width: picWidth * 1 / 2 + 'px',
                height: picHeight * 1 / 2 + 'px',
                left: fixPositionX(i, j) + (picOffsetX - 10) * 1 / 2 + 'px', // (picOffsetX - 10) * 3 / 4修正
                top: fixPositionY(i, j) + (picOffsetY - 200 + 15) * 1 / 2 + 'px', // (picOffsetY - 200 + 15) * 3 / 4修正
                zIndex: 10
            });
            $('#landBox').append($crop);
        } else {
            $crop.css({
                width: picWidth * 1 / 2 + 'px',
                height: picHeight * 1 / 2 + 'px',
                left: fixPositionX(i, j) + (picOffsetX - 10) * 1 / 2 + 'px', // (picOffsetX - 10) * 3 / 4修正
                top: fixPositionY(i, j) + (picOffsetY - 200 + 15) * 1 / 2 + 'px', // (picOffsetY - 200 + 15) * 3 / 4修正
                zIndex: 10
            });
            $crop[0].src = url;
        }
    }
}

/**
 * 更新害虫视图
 * @param i
 * @param j
 * @param land
 */
function updateInsectImg(i, j, land) {
    let $insect = $('#insect_' + land.landId);
    //没有虫害删除有虫害添加
    if (land['hasInsect'] == 0) {
        if ($insect[0]) {
            $insect.remove();
        }
    } else {
        if (!$insect[0]) {
            let url = basePath + '/ext/images/other/insect.png';
            $insect = $('<img class="insect" id="insect_' + land.landId + '" src="' + url + '" alt="害虫">');//创建图片
            $insect.css({
                width: insectWidth + 'px',
                height: insectHeight + 'px',
                left: fixPositionX(i, j) - 10 + 'px', //-10 修正
                top: fixPositionY(i, j) - 10 + 'px', //-30 修正
                zIndex: 20
            });
            soundWorm.play();//播放生虫音效
            $('#landBox').append($insect);
        }
    }
}

function fixPositionX(i, j) {
    return (j * (landWidth + paddingX) / 2 - i * (landWidth + paddingX) / 2);
}

function fixPositionY(i, j) {
    return ((i + j) * (landHeight + paddingY) / 2 + 30);
}

/**
 * 时间戳转换日期 yyyy-MM-dd HH:mm:ss(时间戳)
 * @param time
 * @returns {string}
 */
function formatDate(time) {
    let date = new Date(time);
    let YY = date.getFullYear();
    let MM = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    let DD = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    let hh = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
    let mm = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    let ss = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    // 这里修改返回时间的格式
    return YY + "-" + MM + "-" + DD + " " + hh + ":" + mm + ":" + ss;
}
