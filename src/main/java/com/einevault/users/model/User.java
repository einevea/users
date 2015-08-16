package com.einevault.users.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by einevea on 16/08/15.
 */
public class User extends SimpleUser {
    public static final User ANONYMOUS = new User("", Role.anonymous);

    private final Set<SimpleUser> connections = new HashSet<>();
    private transient final Role role;

    public User(String username, Role role) {
        super(username);
        this.role = role;
    }

    public Set<SimpleUser> getConnections() {
        return connections;
    }

    public void addConnection(User user){
        connections.add(new SimpleUser(user.username));
    }

    public Role getRole() {
        return role;
    }
}