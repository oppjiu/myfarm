package cn.jxufe.entity.common;

import cn.jxufe.bean.EntityID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-05 13:04
 * @author: lwz
 * @description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "T_LandType")
public class LandType extends EntityID {
    private int landTypeCode;   //土壤类型code
    private String landType;    //土壤类型名称
}
