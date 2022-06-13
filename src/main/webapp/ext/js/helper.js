function getRemoteData(url, async, callBack) {
    return request({}, 'post', url, async, callBack);
}


function fromCode2Caption(code, arrayList) {
    var items = $(arrayList);
    for (var index = 0; index < items.length; index++) {
        if (items[index].code == code) {
            return items[index].caption;
        }
    }
    return '';
}

function request(object, method, methodURL, async, successFunction) {
    $.ajax({
        cache: true,
        type: method,
        datatype: 'json',
        contentType: 'application/json',
        url: methodURL,
        data: JSON.stringify(object),
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status + "\r\n" + XMLHttpRequest.readyState + "\r\n" + textStatus);
        },
        async: async,
        success: successFunction
    });
}

function messageBox(title, msg) {
    $.messager.show({
        title: title,
        msg: msg
    });
}


function updateUserBoxView(basePath) {
    //初始化用户栏信息
    let userinfoHeadImg = sessionStorage.getItem('userinfoHeadImg');
    let userinfoUsername = sessionStorage.getItem('userinfoUsername');
    let userinfoExp = sessionStorage.getItem('userinfoExp');
    let userinfoMoney = sessionStorage.getItem('userinfoMoney');
    let userinfoPoint = sessionStorage.getItem('userinfoPoint');
    if (userinfoHeadImg != null) {
        parent.frames['topSpace'].document.getElementById('userinfoHeadImg').src = basePath + '/' + userinfoHeadImg;
    } else {
        parent.frames['topSpace'].document.getElementById('userinfoHeadImg').src = basePath + '/ext/images/headImages/0.jpg';
    }
    if (userinfoPoint != null) {
        parent.frames['topSpace'].document.getElementById('userinfoUsername').innerText = '用户名：' + userinfoUsername;
    } else {
        parent.frames['topSpace'].document.getElementById('userinfoUsername').innerText = '未知用户';
    }
    if (userinfoUsername != null) {
        parent.frames['topSpace'].document.getElementById('userinfoExp').innerText = userinfoExp;
    } else {
        parent.frames['topSpace'].document.getElementById('userinfoExp').innerText = '0';
    }
    if (userinfoExp != null) {
        parent.frames['topSpace'].document.getElementById('userinfoMoney').innerText = userinfoMoney;
    } else {
        parent.frames['topSpace'].document.getElementById('userinfoMoney').innerText = '0';
    }
    if (userinfoMoney != null) {
        parent.frames['topSpace'].document.getElementById('userinfoPoint').innerText = userinfoPoint;
    } else {
        parent.frames['topSpace'].document.getElementById('userinfoPoint').innerText = '0';
    }
}

/**
 *
 * @param data 须有userinfoHeadImg, userinfoUsername, userinfoExp, userinfoMoney, userinfoPoint属性
 * @param basePath
 */
function updateUserBoxData(data, basePath) {
    //设置session保存到本地
    if (data['userinfoHeadImg'] != null) {
        sessionStorage.setItem('userinfoHeadImg', data['userinfoHeadImg']);
    }
    if (data['userinfoExp'] != null) {
        sessionStorage.setItem('userinfoExp', data['userinfoExp']);
    }
    if (data['userinfoMoney'] != null) {
        sessionStorage.setItem('userinfoMoney', data['userinfoMoney']);
    }
    if (data['userinfoPoint'] != null) {
        sessionStorage.setItem('userinfoPoint', data['userinfoPoint']);
    }
    if (data['userinfoUsername'] != null) {
        sessionStorage.setItem('userinfoUsername', data['userinfoUsername']);
    }
}