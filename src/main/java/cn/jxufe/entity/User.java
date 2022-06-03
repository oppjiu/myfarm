package cn.jxufe.entity;

import cn.jxufe.bean.EntityID;
import cn.jxufe.bean.SystemCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @create: 2022-05-11 08:21
 * @author: lwz
 * @description:
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "T_User")
public class User extends EntityID {
    private String username;   //用户名
    private String nickname;   //昵称
    private int exp;    //经验
    private int point;  //分数
    private int money;  //金钱
    private String headImgUrl = SystemCode.DEFAULT_USER_IMAGE_URL;    //头像
}
