package com.meishikr.app.domain.entity.user;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * 用户。
 *
 * Created by yinhang on 16/2/20.
 */
@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;

    @JSONField(name = "user_id")
    private long userId;

    private String name;

    private String username;

    private String telephone;

    private String email;

    private String password;

    private Date birthday;

    @Generated(hash = 1214773834)
    public User(Long id, long userId, String name, String username,
            String telephone, String email, String password, Date birthday) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
