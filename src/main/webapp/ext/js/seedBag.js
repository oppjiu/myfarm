function getData(url) {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url,
            success: function (result) {
                resolve(result);
            },
            error: function (err) {
                reject(err);
            }
        });
    });
}

(function (dom) {
    var width = 0;
    window.onload = function () {
        const tpl = dom.getElementById('tpl').innerHTML;
        const content = dom.getElementsByClassName('all')[0];
        const left = dom.getElementsByClassName('left')[0];
        const right = dom.getElementsByClassName('right')[0];
        left.addEventListener('click', function () {
            change(true);
        });
        right.addEventListener('click', function () {
            change(false);
        });
        getData('http://localhost:8080/myfarm/seedBag/userList').then(function (res) {
            if (res.code === 10) {
                insertImg(res.data.list, tpl, content);
            }
        });
    }

    function insertImg(data, tpl, dom) {
        var str = '';
        var width = 0;
        console.log(data);
        data.forEach(function (item, i) {
            width += 160;
            str += tpl.replace(/{{([^%>]+)?}}/g, function (a, b) {
                if (b === 'total') {
                    const total = Number(item.total) > 99 ? '99+' : item.total;
                    return total
                }
                return item[b]
            });
        });
        document.getElementsByClassName('all')[0].style.width = width + 'px';
        dom.innerHTML = str;
    }

    function change(isLeft) {
        console.log(document.getElementsByClassName('all')[0].offsetWidth);
        if ((Math.abs(width) + 640 >= document.getElementsByClassName('all')[0].offsetWidth && isLeft) || (width >= 0 && !isLeft)) {
            alert('已经到头了')
            return;
        }
        width = isLeft ? width - 160 : width + 160;
        document.getElementsByClassName('all')[0].style.transform = `translateX(${width}px)`;
    }
})(document)

