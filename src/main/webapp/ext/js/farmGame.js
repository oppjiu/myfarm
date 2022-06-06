var basePath = $('#basePath').attr('value');
var wsBasePath = $('#wsBasePath').attr('value');
var websocket = null;

$(function () {
    initWebSocket();
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
        console.log('evt.data', evt.data);
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
