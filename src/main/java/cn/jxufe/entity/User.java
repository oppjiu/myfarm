package cn.jxufe.entity;

import cn.jxufe.bean.EntityID;
import cn.jxufe.bean.SystemCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-11 08:21
 * @author: lwz
 * @description:
 **/
@Entity
@Table(name = "T_User")
public class User extends EntityID {
    private String username;   //用户名
    private String nickname;   //昵称
    private int exp;    //经验
    private int point;  //分数
    private int money;  //金钱
    private String headImgUrl = SystemCode.DEFAULT_USER_IMAGE_URL;    //头像

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", exp=" + exp +
                ", point=" + point +
                ", money=" + money +
                ", headImgUrl='" + headImgUrl + '\'' +
                '}';
    }
}
