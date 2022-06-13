package cn.jxufe.entity;

import cn.jxufe.bean.EntityID;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-18 10:44
 * @author: lwz
 * @description:
 **/
@Entity
@Table(name = "T_SeedBag")
public class SeedBag extends EntityID {
    private int seedNumber;    //种子数量
    //多对多外键
    private int cropId;    //种子ID
    private String username;    //用户ID

    public int getSeedNumber() {
        return seedNumber;
    }

    public void setSeedNumber(int seedNumber) {
        this.seedNumber = seedNumber;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "SeedBag{" +
                "seedNumber=" + seedNumber +
                ", cropId=" + cropId +
                ", username='" + username + '\'' +
                '}';
    }
}
