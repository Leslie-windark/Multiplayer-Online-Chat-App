package com.qqcommon;

import java.io.Serializable;

/**
 * @author leslie-windark
 * @version 1.0
 *
 * 网络传对象时，要将其序列化
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userId;  //用户ID
    private String passwd;  //用户密码

    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPasswd(String pwd) {
        this.passwd = pwd;
    }
}
