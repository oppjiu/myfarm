package cn.jxufe.entity.common;

import cn.jxufe.bean.EntityID;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-05 13:03
 * @author: lwz
 * @description:
 **/
@Entity
@Table(name = "T_SeedType")
public class SeedType extends EntityID {
    private int seedTypeCode;   //种子类型code
    private String seedType;    //种子类型名称

    public int getSeedTypeCode() {
        return seedTypeCode;
    }

    public void setSeedTypeCode(int seedTypeCode) {
        this.seedTypeCode = seedTypeCode;
    }

    public String getSeedType() {
        return seedType;
    }

    public void setSeedType(String seedType) {
        this.seedType = seedType;
    }

    @Override
    public String toString() {
        return "SeedType{" +
                "seedTypeCode=" + seedTypeCode +
                ", seedType='" + seedType + '\'' +
                '}';
    }
}
