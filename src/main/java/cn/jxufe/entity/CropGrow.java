package cn.jxufe.entity;

import cn.jxufe.bean.EntityID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-05 08:46
 * @author: lwz
 * @description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
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
}
