package cn.jxufe.bean;

import cn.jxufe.entity.Land;
import cn.jxufe.entity.view.CropGrowView;

/**
 * @create: 2022-05-29 22:37
 * @author: lwz
 * @description:
 **/
public class FarmResponse {
    private Land land;
    private CropGrowView cropGrowView;

    public FarmResponse() {
    }

    public FarmResponse(Land land, CropGrowView cropGrowView) {
        this.land = land;
        this.cropGrowView = cropGrowView;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public CropGrowView getCropGrowView() {
        return cropGrowView;
    }

    public void setCropGrowView(CropGrowView cropGrowView) {
        this.cropGrowView = cropGrowView;
    }

    @Override
    public String toString() {
        return "FarmResponse{" +
                "land=" + land +
                ", cropGrowView=" + cropGrowView +
                '}';
    }
}
