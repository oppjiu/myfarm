package cn.jxufe.entity;

import cn.jxufe.bean.EntityID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-18 10:44
 * @author: lwz
 * @description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "T_SeedBag")
public class SeedBag extends EntityID {
    private int seedNumber = 0;    //种子数量
    //多对多外键
    private int cropId = 0;    //种子ID
    private String username = "";    //用户ID
}
