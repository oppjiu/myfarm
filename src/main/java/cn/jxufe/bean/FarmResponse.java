package cn.jxufe.bean;

import cn.jxufe.entity.Land;
import cn.jxufe.entity.User;
import cn.jxufe.entity.view.CropGrowView;

/**
 * @create: 2022-05-29 22:37
 * @author: lwz
 * @description:
 **/
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FarmResponse{" +
                "code=" + code +
                ", land=" + land +
                ", cropGrowView=" + cropGrowView +
                ", user=" + user +
                '}';
    }
}
