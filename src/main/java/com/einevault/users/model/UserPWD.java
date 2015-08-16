package com.einevault.users.model;

/**
 * Created by einevea on 16/08/15.
 */
public class UserPWD {
    public transient final static UserPWD ANONYMOUS = new UserPWD("","");
    private final String username;
    private final String pwd;

    public UserPWD(String username, String pwd) {
        this.username = username;
        this.pwd = pwd;
    }

    public String getUsername() {
        return username;
    }

    public String getPwd() {
        return pwd;
    }
}
