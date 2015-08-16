package com.einevault.users.model;

/**
 * Created by einevea on 16/08/15.
 */
public class SimpleUser {
    protected final String username;

    public SimpleUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
