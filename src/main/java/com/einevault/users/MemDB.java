package com.einevault.users;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by einevea on 16/08/15.
 */
public class MemDB {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, String> pwds = new HashMap<>();

    public MemDB(){
        User admin = new User("Admin", Role.admin);
        users.put(admin.getUsername(), admin);

        // PWD no secure simple and too sort, should be salted and hashed.
        // The authentication is not a requirement
        pwds.put(admin.getUsername(), "1234");
    }

    public String getPWDForUser(String user) {
        return pwds.get(user);
    }
}
