package cn.jxufe.entity.common;

import cn.jxufe.bean.EntityID;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-05 13:02
 * @author: lwz
 * @description:
 **/
@Entity
@Table(name = "T_CropState")
public class CropState extends EntityID {
    private int cropStateCode; //作物生长状态code
    private String cropState;  //作物生长状态名称

    public int getCropStateCode() {
        return cropStateCode;
    }

    public void setCropStateCode(int cropStateCode) {
        this.cropStateCode = cropStateCode;
    }

    public String getCropState() {
        return cropState;
    }

    public void setCropState(String cropState) {
        this.cropState = cropState;
    }

    @Override
    public String toString() {
        return "CropState{" +
                "cropStateCode=" + cropStateCode +
                ", cropState='" + cropState + '\'' +
                '}';
    }
}
