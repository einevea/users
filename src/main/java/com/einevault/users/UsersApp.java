package com.einevault.users;


import spark.Request;

import java.util.Optional;

import static spark.Spark.*;

/**
 * Created by einevea on 16/08/15.
 */
public class UsersApp {

    public static BasicAuth basicAuth = new BasicAuth();
    public static MemDB memDB = new MemDB();


    public static void main(String[] args) {

        before((request, response) -> {
            boolean authenticated = isAuthenticated(request) ;
            if (!authenticated) {
                response.header("WWW-Authenticate", "Basic realm=\"usersRealm\"");
                halt(401, "Not Authorized status");
            }
        });


        get("/users", (req, res) -> "Hello World");
    }

    private static boolean isAuthenticated(Request request) {
        String authorization = request.headers("Authorization");
        if(authorization == null || authorization.isEmpty()){
            return false;
        }

        Optional<UserPWD> userPWDOpt =  basicAuth.extract(authorization);
        if(!userPWDOpt.isPresent()) {
            return false;
        }

        String pwd = memDB.getPWDForUser(userPWDOpt.get().getUser());
        return checkPWD(pwd, userPWDOpt.get().getPwd());
    }

    private static boolean checkPWD(String pwd, String unsurePwd) {
        return pwd != null && !pwd.isEmpty() && pwd.equals(unsurePwd);
    }

}
