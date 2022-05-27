package cn.jxufe.entity.view;

import cn.jxufe.bean.EntityID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @create: 2022-05-26 22:31
 * @author: lwz
 * @description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "V_Land")
public class LandView extends EntityID {
    private int landId;    //土地ID
    private int hasCrop;    //是否已种植
    private int hasInsect;    //是否生虫
    private int isWithered;    //是否枯萎
    private int output;    //作物产量
    private int growthTimeOfEachState;    //作物在每种状态下生长的时间
    private int stateEndTime;    //作物在每种状态下生长
    //外键
    //用户表
    private String username;   //用户名
    private String nickname;   //昵称
    private int exp;    //经验
    private int point;  //分数
    private int money;  //金钱
    private String headImgUrl;    //头像

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
    private String landType;    //土壤类型名称m
}
