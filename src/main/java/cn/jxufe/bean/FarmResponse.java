package cn.jxufe.bean;

import cn.jxufe.entity.view.CropGrowView;

/**
 * @create: 2022-05-29 22:37
 * @author: lwz
 * @description:
 **/
public class FarmResponse {
    private int landId;
    private CropGrowView cropGrowView;

    public FarmResponse() {
    }

    public FarmResponse(int landId, CropGrowView cropGrowView) {
        this.landId = landId;
        this.cropGrowView = cropGrowView;
    }

    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
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
                "landId=" + landId +
                ", cropGrowView=" + cropGrowView +
                '}';
    }
}
