package cn.jxufe.entity.view;

import cn.jxufe.bean.EntityID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-05 18:35
 * @author: lwz
 * @description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "V_CropGrow")
public class CropGrowView extends EntityID {
    private int stageId;  //生长阶段id
    private String stageName;  //生长阶段标题
    private int growTime;  //生长时间
    private double insectChance;    //生虫概率
    private int picWidth;    //图片宽度
    private int picHeight;    //图片高度
    private int picOffsetX;    //图片offsetX
    private int picOffsetY;    //图片offsetY
    //外键
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

    //作物生长状态表
    private int cropStateCode;    //作物生长状态code
    private String cropState = "";  //作物生长状态

}
