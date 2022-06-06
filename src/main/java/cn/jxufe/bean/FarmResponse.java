package cn.jxufe.bean;

import cn.jxufe.entity.Land;
import cn.jxufe.entity.view.CropGrowView;
import lombok.Data;

/**
 * @create: 2022-05-29 22:37
 * @author: lwz
 * @description:
 **/
@Data
public class FarmResponse {
    private int code;
    private Land land;
    private CropGrowView cropGrowView;

    public FarmResponse() {
    }

    public FarmResponse(int code ,Land land, CropGrowView cropGrowView) {
        this.code = code;
        this.land = land;
        this.cropGrowView = cropGrowView;
    }
}
