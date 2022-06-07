var basePath = $('#basePath').attr('value');
$(function () {
    var $seedBagBox = $('#seedBagBox');
    var $scrollToLeft = $('#scrollToLeft');
    var $scrollToRight = $('#scrollToRight');
    var $seedBagContainer = $('#seedBagContainer');
    var imgWidth = 120;
    var buttonWidth = 50;
    var count = 5;

    //初始化数据
    request({}, 'post', basePath + '/seedBag/userCrop', true, function (result) {
        if (result.code == 10) {
            var data = result.data;
            for (let i = 0; i < data.length; i++) {
                let cropId = data[i]['cropId'];
                let url = basePath + '/ext/images/crops/' + cropId + '/5.png';
                let $seedNumber = $('<div class="seedNumber">' + data[i]['seedNumber'] + '</div>');
                let $img = $('<img id="cropImg_' + cropId + '" class="cropImg" data-cropId="' + cropId + '" src="' + url + '" alt="图片">');
                let $cropImg = $('<div class="cropImgBox"></div>');
                $seedNumber.css({
                    'flex': 1,
                    'text-align': 'center',
                    'color': 'red',
                    '-webkit-text-stroke': '1px black'
                })
                $cropImg.css({
                    'display': 'flex',
                    'flex-direction': 'column'
                })
                $img.css({
                    'width': imgWidth + 'px',
                    'padding': '0 20px',
                    'display': 'block',
                    'flex': 1,
                });
                $cropImg.append($seedNumber);
                $cropImg.append($img);
                $seedBagContainer.append($cropImg);

                //生成提示框
                let content = $('<div style="color: black;font-weight: bold;">')
                    .append('名称：' + data[i]['cropName']).append('<br>')
                    .append('级别：' + data[i]['grade']).append('<br>')
                    .append('价格：' + data[i]['purchasePrice']).append('<br>')
                    .append('类别：' + data[i]['seedType']).append('<br>')
                    .append('土地：' + data[i]['landType']).append('<br>')
                    .append('可收获季：' + data[i]['growSeason']).append('<br>')
                    .append('成熟时间：' + data[i]['growthTimeOfEachSeason']).append('<br>')
                    .append('单季收获：' + data[i]['harvestNum']).append('<br>')
                    .append('经验收获：' + data[i]['harvestExp']).append('<br>')
                    .append('单个果实可获金币：' + data[i]['salePrice']).append('<br>')
                    .append('积分收获：' + data[i]['harvestScore']).append('<br>')
                    .append('</div>');

                $('#cropImg_' + data[i]['cropId']).tooltip({
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
            }
        }
    });

    $seedBagBox.css({
        'display': 'flex',
        'flex': 1,
        'margin': '0 auto',
        width: imgWidth * count + buttonWidth * 2 + 'px',
    });
    $seedBagContainer.css({
        'width': imgWidth * count + 'px',
        'height': '200px',
        'overflow-x': 'hidden',
        'overflow-y': 'hidden',

        'display': 'flex',
        'flex-direction': 'row',
        'flex': 1,
        'align-items': 'stretch',
    });
    $scrollToLeft.css({
        width: buttonWidth + 'px'
    });
    $scrollToRight.css({
        width: buttonWidth + 'px'
    });

    $scrollToLeft.click(function () {
        //判断是否存在滚动条
        if ($seedBagContainer[0].scrollWidth > $seedBagContainer[0].clientWidth || $seedBagContainer[0].offsetWidth > $seedBagContainer[0].clientWidth) {
            //滚动条左移
            let width = $('.cropImg').outerWidth() * count;
            $seedBagContainer.animate({scrollLeft: '-=' + width});
        }

    });
    $scrollToRight.click(function () {
        // 判断是否存在滚动条
        if ($seedBagContainer[0].scrollWidth > $seedBagContainer[0].clientWidth || $seedBagContainer[0].offsetWidth > $seedBagContainer[0].clientWidth) {
            //滚动条右移
            let width = $('.cropImg').outerWidth() * count;
            $seedBagContainer.animate({scrollLeft: '+=' + width});
        }
    });
});
