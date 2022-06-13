var draggableImg;
var imgExtData = {
    offsetX: '',
    offsetY: '',
    width: '',
    height: ''
}

function initDraggable() {
    draggableImg = $('#tools-imagePositioner-display img');
    draggableImg.draggable({
        onStopDrag: function () {
            imgExtData.offsetX = draggableImg.position().left;
            imgExtData.offsetY = draggableImg.position().top;
            imgExtData.width = draggableImg.width();
            imgExtData.height = draggableImg.height();
            console.log('imgExtData: ', imgExtData);
        }
    });
    draggableImg.resizable();
}

function positionerLoadImage() {
    draggableImg.css("position", "absolute");
    draggableImg.css("left", imgExtData.offsetX + "px");
    draggableImg.css("top", imgExtData.offsetY + "px");
    draggableImg.css("width", imgExtData.width);
    draggableImg.css("height", imgExtData.height);
}

function imagePositionerOnDrag(e) {
    var d = e.data;
    if (d.left < -$(d.parent).width()) {
        d.left = -$(d.target).width()
    }
    if (d.top < 0) {
        d.top = 0
    }
    if (d.left > $(d.parent).width()) {
        d.left = $(d.parent).width();
    }
    if (d.top > $(d.parent).height()) {
        d.top = $(d.parent).height();
    }
}