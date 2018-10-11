package com.smartcold.tray.entity.comm;

import java.io.Serializable;

/**
 * Created by admin on 2018/9/5.
 */
public class UserEntity implements Serializable {
    private int id;
    private String username;
    private String password;
    private int type;//用户类型   O 管理员 1 用户  2 仓库人员
    private String token;
    private String systoken;

    //登录安全检查信息
    private boolean loginRisk;//当前登录环境是否正常
    private String lastlogininfo;//上的登录环境信息
    private String cuttlogininfo;//本次登录环境信息


    private static final long serialVersionUID = -2875979349754314486L;

    public UserEntity() {

    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLoginRisk() {
        return loginRisk;
    }

    public void setLoginRisk(boolean loginRisk) {
        this.loginRisk = loginRisk;
    }

    public String getLastlogininfo() {
        return lastlogininfo;
    }

    public void setLastlogininfo(String lastlogininfo) {
        this.lastlogininfo = lastlogininfo;
    }

    public String getCuttlogininfo() {
        return cuttlogininfo;
    }

    public void setCuttlogininfo(String cuttlogininfo) {
        this.cuttlogininfo = cuttlogininfo;
    }

    public String getSystoken() {
        return systoken;
    }

    public void setSystoken(String systoken) {
        this.systoken = systoken;
    }
}
