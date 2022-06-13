package cn.jxufe.entity.view;

import cn.jxufe.bean.EntityID;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-26 09:34
 * @author: lwz
 * @description:
 **/
@Entity
@Table(name = "V_SeedBag")
public class SeedBagView extends EntityID {
    private int seedNumber = 0;    //种子数量
    //外键
    //用户表
    private String username = "";    //用户ID
    private String nickname;    //昵称
    //    private int exp;    //经验
//    private int score;  //分数
//    private int money;  //金钱
//    private String headImg; //头像
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
    private int landTypeCode;  //土地需求
    private String landType;    //土壤类型名称
    private int seedTypeCode;   //种子类型
    private String seedType;    //种子类型名称

    public int getSeedNumber() {
        return seedNumber;
    }

    public void setSeedNumber(int seedNumber) {
        this.seedNumber = seedNumber;
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

    @Override
    public String toString() {
        return "SeedBagView{" +
                "seedNumber=" + seedNumber +
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
                ", landTypeCode=" + landTypeCode +
                ", landType='" + landType + '\'' +
                ", seedTypeCode=" + seedTypeCode +
                ", seedType='" + seedType + '\'' +
                '}';
    }
}
