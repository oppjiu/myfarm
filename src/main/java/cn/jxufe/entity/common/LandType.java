package cn.jxufe.entity.common;

import cn.jxufe.bean.EntityID;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-05 13:04
 * @author: lwz
 * @description:
 **/
@Entity
@Table(name = "T_LandType")
public class LandType extends EntityID {
    private int landTypeCode;   //土壤类型code
    private String landType;    //土壤类型名称

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

    @Override
    public String toString() {
        return "LandType{" +
                "landTypeCode=" + landTypeCode +
                ", landType='" + landType + '\'' +
                '}';
    }
}
