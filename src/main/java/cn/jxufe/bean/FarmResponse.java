package cn.jxufe.bean;

import cn.jxufe.entity.Land;
import cn.jxufe.entity.User;
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
    private User user;

    public FarmResponse() {
    }

    public FarmResponse(int code, Land land) {
        this.code = code;
        this.land = land;
    }

    public FarmResponse(int code, Land land, CropGrowView cropGrowView) {
        this.code = code;
        this.land = land;
        this.cropGrowView = cropGrowView;
    }

    public FarmResponse(int code, Land land, CropGrowView cropGrowView, User user) {
        this.code = code;
        this.land = land;
        this.cropGrowView = cropGrowView;
        this.user = user;
    }
}
