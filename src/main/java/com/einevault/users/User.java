package com.einevault.users;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by einevea on 16/08/15.
 */
public class User {
    private final String username;
    private final Set<User> connections = new HashSet<>();
    private final Role role;

    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public Set<User> getConnections() {
        return connections;
    }

    public Role getRole() {
        return role;
    }
}