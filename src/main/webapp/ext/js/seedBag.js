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
        $.parser.parse();//全局刷新，动态加载表单后，easyui样式失效需要刷新

        const tpl = dom.getElementById('tpl').innerHTML;
        const content = dom.getElementsByClassName('all')[0];
        const left = dom.getElementsByClassName('left')[0];
        const right = dom.getElementsByClassName('right')[0];
        const empty = dom.getElementsByClassName('empty')[0];
        content.style.display = 'none'
        left.style.display = 'none'
        right.style.display = 'none'
        empty.style.display = "block"
        left.addEventListener('click', function () {
            change(true);
        });
        right.addEventListener('click', function () {
            change(false);
        });
        getData('http://localhost:8080/myfarm/seedBag/userCrop').then(function (res) {
            if (res.code === 10) {
                // insertImg(res.data, tpl, content);
                if (!res.data.length) {
                    return
                }
                empty.style.display = "none"
                content.style.display = 'block'
                left.style.display = 'block'
                right.style.display = 'block'

                res.data.forEach(function (item) {
                    item.total = item.seedNumber;
                    item.src = `../images/crops/${item.id}/5.png`
                })
                insertImg(res.data, tpl, content)
            }
        });
    }

    function insertImg(data, tpl, dom) {
        // console.log(data)
        var str = '';
        var width = 0;
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
        // console.log(document.getElementsByClassName('all')[0].offsetWidth);
        if ((Math.abs(width) + 640 >= document.getElementsByClassName('all')[0].offsetWidth && isLeft) || (width >= 0 && !isLeft)) {
            messageBox('提示', '收纳袋中没有种子了');
            return;
        }
        width = isLeft ? width - 160 : width + 160;
        document.getElementsByClassName('all')[0].style.transform = `translateX(${width}px)`;
    }
})(document)

