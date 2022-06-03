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
    return "";
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