var basePath = $('#basePath').attr('value');
var wsBasePath = $('#wsBasePath').attr('value');
var websocket = null;

$(function () {
    initiateFarmView();
});

function initWebSocket() {
    if ('WebSocket' in window) {
        //Websocket的连接
        websocket = new WebSocket(wsBasePath+'/game/action');//WebSocket对应的地址
    } else if ('MozWebSocket' in window) {
        //Websocket的连接
        websocket = new MozWebSocket(wsBasePath+'/game/action');//SockJS对应的地址
    } else {
        //SockJS的连接
        websocket = new SockJS(wsBasePath+'/game/action');//SockJS对应的地址
    }

    websocket.onopen = function onOpen(evt) {
        console.log('连接打开：', evt);
    };
    websocket.onmessage = function onMessage(evt) {

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
 * 初始化农场视图
 */
function initiateFarmView() {
    request({}, 'post', basePath + '/game/initiateFarmView', true, function (result) {
        console.log('result: ', result);
        if (result.code == 10) {
            var data = result.data;


            var root = $('.main');
            for (let i = 0; i < data.length; i++) {
                //土地信息和生长阶段信息
                var land = data[i].land;
                var cropGrow = data[i].cropGrowView;
                //初始化土地
                var imgBackGround = $('<img src="' + basePath + '/ext/images/land/' + (land.landTypeCode) + '.png" style="width: 150px">');


                //土地有作物
                if (land.hasCrop == 1) {
                    //初始化作物
                    var itemDiv = $('<div class="item" id="land_' + i + '" onclick="plantSeed(' + i + ')"></div>');
                    var left = 300 - 100 * (i%6) + land.landTypeCode * 100;
                    var top = -420 * (i%6) - 20 * land.landTypeCode;
                    itemDiv.css({
                        'left': left + 'px',
                        'top': top + 'px',
                    });
                }

                // if () {
                //     var imgSeed = $('<img class="crop" id="crop_' + land.landTypeCode + '" src="' + basePath + '/ext/images/crops/1/1.png">');
                // }


                var imgInsect = $('<img class="insect" id="seed_' + i + '" src="' + basePath + '/ext/images/other/insect.png">');


                itemDiv.append(imgSeed);
                itemDiv.append(imgInsect);
                itemDiv.append(imgBackGround);
                root.append(itemDiv[0])
            }
        }else{
            messageBox('消息', '游戏初始化失败');
        }

    });
}

function plantSeed(landId) {
    console.log('landId', landId);
}

function purchaseSeed(cropId, seedNumber, user){

}

function userActionPlantSeed(landId, cropId) {

}

function userActionKillWorm(landId) {

}

function userActionHarvest(landId) {

}

function userActionCleanGrass(landId) {

}
