package cn.jxufe.entity;

import cn.jxufe.bean.EntityID;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-05 08:45
 * @author: lwz
 * @description:
 **/
@Entity
@Table(name = "T_Crop")
public class Crop extends EntityID {
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
    //一对一外键
    private int landTypeCode;  //土地类型code
    private int seedTypeCode;   //种子类型code

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

    public int getSeedTypeCode() {
        return seedTypeCode;
    }

    public void setSeedTypeCode(int seedTypeCode) {
        this.seedTypeCode = seedTypeCode;
    }

    @Override
    public String toString() {
        return "Crop{" +
                "cropId=" + cropId +
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
                ", seedTypeCode=" + seedTypeCode +
                '}';
    }
}
