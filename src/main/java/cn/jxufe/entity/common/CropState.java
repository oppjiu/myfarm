package cn.jxufe.entity.common;

import cn.jxufe.bean.EntityID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-05 13:02
 * @author: lwz
 * @description:
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "T_CropState")
public class CropState extends EntityID {
    private int cropStateCode; //作物生长状态code
    private String cropState;  //作物生长状态名称
}
