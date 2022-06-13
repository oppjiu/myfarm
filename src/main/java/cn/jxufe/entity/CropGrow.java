package cn.jxufe.entity;

import cn.jxufe.bean.EntityID;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-05 08:46
 * @author: lwz
 * @description:
 **/
@Entity
@Table(name = "T_CropGrow")
public class CropGrow extends EntityID {
    private int stageId;  //生长阶段id
    private String stageName;  //生长阶段标题
    private int growTime;  //生长时间
    private double insectChance;    //生虫概率
    private int picWidth;    //图片宽度
    private int picHeight;    //图片高度
    private int picOffsetX;    //图片offsetX
    private int picOffsetY;    //图片offsetY

    //多对一外键
    private int cropId;    //作物ID
    //一对一外键
    private int cropStateCode;    //作物生长状态code

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public int getGrowTime() {
        return growTime;
    }

    public void setGrowTime(int growTime) {
        this.growTime = growTime;
    }

    public double getInsectChance() {
        return insectChance;
    }

    public void setInsectChance(double insectChance) {
        this.insectChance = insectChance;
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public int getPicOffsetX() {
        return picOffsetX;
    }

    public void setPicOffsetX(int picOffsetX) {
        this.picOffsetX = picOffsetX;
    }

    public int getPicOffsetY() {
        return picOffsetY;
    }

    public void setPicOffsetY(int picOffsetY) {
        this.picOffsetY = picOffsetY;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public int getCropStateCode() {
        return cropStateCode;
    }

    public void setCropStateCode(int cropStateCode) {
        this.cropStateCode = cropStateCode;
    }

    @Override
    public String toString() {
        return "CropGrow{" +
                "stageId=" + stageId +
                ", stageName='" + stageName + '\'' +
                ", growTime=" + growTime +
                ", insectChance=" + insectChance +
                ", picWidth=" + picWidth +
                ", picHeight=" + picHeight +
                ", picOffsetX=" + picOffsetX +
                ", picOffsetY=" + picOffsetY +
                ", cropId=" + cropId +
                ", cropStateCode=" + cropStateCode +
                '}';
    }
}
