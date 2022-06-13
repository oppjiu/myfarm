package cn.jxufe.entity.view;

import cn.jxufe.bean.EntityID;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


/**
 * @create: 2022-05-26 22:31
 * @author: lwz
 * @description:
 **/
@Entity
@Table(name = "V_Land")
public class LandView extends EntityID {
    private int landId;    //土地ID
    private int hasCrop;    //是否已种植
    private int hasInsect;    //是否生虫
    private int isWithered;    //是否枯萎
    private int isMature;    //是否成熟
    private int output;    //作物产量
    private int nowCropGrowStage = 0;    //当前生长阶段
    private int nextCropGrowStage = 0;    //下一生长阶段
    private int growingSeason = 0;    //第几季度
    private int growthTimeOfEachState;    //作物在每种状态下生长的时间
    private Date stateEndTime;    //作物在每种状态下生长
    //外键
    //用户表
    private String username;   //用户名
    private String nickname;   //昵称
//    private int exp;    //经验
//    private int point;  //分数
//    private int money;  //金钱
//    private String headImgUrl;    //头像

    //种子视图
    private int cropId;    //种子ID
    private String cropName;    //种子名称
    private int growSeason; //X季作物
    private int grade;  //种子等级
    private int growthTimeOfEachSeason;  //每季成熟所需时间
    private int harvestScore;  //成熟可获得积分
    private int harvestExp;  //成熟可获经验
    private int harvestNum; //每季成熟可获得收成
    private int purchasePrice;   //种子采购价
    private int salePrice;  //每个收获的果实单价
    private String tips;    //提示信息
    //种子类型
    private int seedTypeCode;   //种子类型
    private String seedType;    //种子类型名称
    //土地需求
    private int landTypeCode;  //土地需求
    private String landType;    //土壤类型名称

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getGrowSeason() {
        return growSeason;
    }

    public void setGrowSeason(int growSeason) {
        this.growSeason = growSeason;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrowthTimeOfEachSeason() {
        return growthTimeOfEachSeason;
    }

    public void setGrowthTimeOfEachSeason(int growthTimeOfEachSeason) {
        this.growthTimeOfEachSeason = growthTimeOfEachSeason;
    }

    public int getHarvestScore() {
        return harvestScore;
    }

    public void setHarvestScore(int harvestScore) {
        this.harvestScore = harvestScore;
    }

    public int getHarvestExp() {
        return harvestExp;
    }

    public void setHarvestExp(int harvestExp) {
        this.harvestExp = harvestExp;
    }

    public int getHarvestNum() {
        return harvestNum;
    }

    public void setHarvestNum(int harvestNum) {
        this.harvestNum = harvestNum;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getSeedTypeCode() {
        return seedTypeCode;
    }

    public void setSeedTypeCode(int seedTypeCode) {
        this.seedTypeCode = seedTypeCode;
    }

    public String getSeedType() {
        return seedType;
    }

    public void setSeedType(String seedType) {
        this.seedType = seedType;
    }

    public int getLandTypeCode() {
        return landTypeCode;
    }

    public void setLandTypeCode(int landTypeCode) {
        this.landTypeCode = landTypeCode;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    @Override
    public String toString() {
        return "LandView{" +
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
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", cropId=" + cropId +
                ", cropName='" + cropName + '\'' +
                ", growSeason=" + growSeason +
                ", grade=" + grade +
                ", growthTimeOfEachSeason=" + growthTimeOfEachSeason +
                ", harvestScore=" + harvestScore +
                ", harvestExp=" + harvestExp +
                ", harvestNum=" + harvestNum +
                ", purchasePrice=" + purchasePrice +
                ", salePrice=" + salePrice +
                ", tips='" + tips + '\'' +
                ", seedTypeCode=" + seedTypeCode +
                ", seedType='" + seedType + '\'' +
                ", landTypeCode=" + landTypeCode +
                ", landType='" + landType + '\'' +
                '}';
    }
}
