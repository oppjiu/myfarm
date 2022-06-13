package cn.jxufe.entity;

import cn.jxufe.bean.EntityID;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @create: 2022-05-26 22:00
 * @author: lwz
 * @description:
 **/

@Entity
@Table(name = "T_Land")
public class Land extends EntityID {
    private int landId;    //土地ID
    private int hasCrop;    //是否已种植
    private int hasInsect;    //是否生虫
    private int isWithered;    //是否枯萎
    private int isMature;    //是否成熟
    private int output;    //作物产量
    private int nowCropGrowStage;    //当前生长阶段
    private int nextCropGrowStage;    //下一生长阶段/*暂无作用*/
    private int growingSeason;    //第几季度
    private int growthTimeOfEachState;    //作物在每种状态下生长的时间
    private Date stateEndTime = null;    //作物在每种状态已经生长的时间
    //一对一外键
    private int cropId;    //种子ID
    private int landTypeCode;    //土地类型code
    //多对一外键
    private String username;    //用户ID


    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
    }

    public int getHasCrop() {
        return hasCrop;
    }

    public void setHasCrop(int hasCrop) {
        this.hasCrop = hasCrop;
    }

    public int getHasInsect() {
        return hasInsect;
    }

    public void setHasInsect(int hasInsect) {
        this.hasInsect = hasInsect;
    }

    public int getIsWithered() {
        return isWithered;
    }

    public void setIsWithered(int isWithered) {
        this.isWithered = isWithered;
    }

    public int getIsMature() {
        return isMature;
    }

    public void setIsMature(int isMature) {
        this.isMature = isMature;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public int getNowCropGrowStage() {
        return nowCropGrowStage;
    }

    public void setNowCropGrowStage(int nowCropGrowStage) {
        this.nowCropGrowStage = nowCropGrowStage;
    }

    public int getNextCropGrowStage() {
        return nextCropGrowStage;
    }

    public void setNextCropGrowStage(int nextCropGrowStage) {
        this.nextCropGrowStage = nextCropGrowStage;
    }

    public int getGrowingSeason() {
        return growingSeason;
    }

    public void setGrowingSeason(int growingSeason) {
        this.growingSeason = growingSeason;
    }

    public int getGrowthTimeOfEachState() {
        return growthTimeOfEachState;
    }

    public void setGrowthTimeOfEachState(int growthTimeOfEachState) {
        this.growthTimeOfEachState = growthTimeOfEachState;
    }

    public Date getStateEndTime() {
        return stateEndTime;
    }

    public void setStateEndTime(Date stateEndTime) {
        this.stateEndTime = stateEndTime;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public int getLandTypeCode() {
        return landTypeCode;
    }

    public void setLandTypeCode(int landTypeCode) {
        this.landTypeCode = landTypeCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Land{" +
                "landId=" + landId +
                ", hasCrop=" + hasCrop +
                ", hasInsect=" + hasInsect +
                ", isWithered=" + isWithered +
                ", isMature=" + isMature +
                ", output=" + output +
                ", nowCropGrowStage=" + nowCropGrowStage +
                ", nextCropGrowStage=" + nextCropGrowStage +
                ", growingSeason=" + growingSeason +
                ", growthTimeOfEachState=" + growthTimeOfEachState +
                ", stateEndTime=" + stateEndTime +
                ", cropId=" + cropId +
                ", landTypeCode=" + landTypeCode +
                ", username='" + username + '\'' +
                '}';
    }
}
