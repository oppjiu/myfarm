package cn.jxufe.entity.common;

import cn.jxufe.bean.EntityID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-05 13:03
 * @author: lwz
 * @description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "T_SeedType")
public class SeedType extends EntityID {
    private int seedTypeCode = 0;   //种子类型code
    private String seedType = "";    //种子类型名称
}
