package com.einevault.users;

import com.einevault.users.model.Role;
import com.einevault.users.model.User;
import com.einevault.users.model.UserPWD;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by einevea on 16/08/15.
 */
public class MemDB {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, String> pwds = new HashMap<>();

    public MemDB(){
        // PWD no secure simple and too sort, should be salted and hashed.
        // The authentication is not a requirement
        User admin = addUser("Admin", "1234", Role.admin);

        //Test Data
        User d1 = addUser("Dani", "1234", Role.register);
        User d2 =addUser("Dani2", "1234", Role.register);
        User d3 = addUser("Dani3", "1234", Role.register);

        d1.addConnection(d2);
        d1.addConnection(d3);
        d2.addConnection(d3);
        d3.addConnection(d1);
    }

    private User addUser(String username, String pwd, Role role) {
        User user = new User(username, role);
        users.put(user.getUsername(), user);
        pwds.put(user.getUsername(), pwd);
        return user;
    }

    public String getPWDForUser(String user) {
        return pwds.get(user);
    }

    public Optional<User> getUser(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public Stream<User> getUsers(String search){
        return users.values().stream().filter(user -> user.getUsername().contains(search) && user.getRole() != Role.admin);
    }

    public Optional<User> createUser( UserPWD userPWD ){
        String username = userPWD.getUsername();
        String pwd = userPWD.getPwd();

        User user = new User(username, Role.register);
        User oldUser = users.putIfAbsent(username, user);
        if(oldUser == null){
            pwds.put(username, pwd);
            return Optional.of(user);
        }else{
            return Optional.empty();
        }
    }
}
