package com.einevault.users;

/**
 * Created by einevea on 16/08/15.
 */
public class UserPWD {
    private final String user;
    private final String pwd;

    public UserPWD(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public String getPwd() {
        return pwd;
    }
}
