package cn.jxufe.entity;

import cn.jxufe.bean.EntityID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @create: 2022-05-26 22:00
 * @author: lwz
 * @description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
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

}
