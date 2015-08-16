package com.einevault.users;


import com.einevault.users.model.UserPWD;
import com.einevault.users.routes.*;
import spark.Request;

import static com.einevault.users.JsonUtil.json;

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
            boolean requireAuth = !isPublic(request) && !isAuthenticated(request);
            if (requireAuth) {
                response.header("WWW-Authenticate", "Basic realm=\"usersRealm\"");
                halt(401, "Not Authorized status");
            }
        });

        post("/users/:username/connections", "application/json", (req, res) -> new PostConnectionsRoute(memDB, req.params(":username")).handle(req, res), json());
        get("/users/:username/connections","application/json", (req, res) -> new GetConnectionsRoute(memDB, req.params(":username")).handle(req, res), json());

        get("/users/:username","application/json", (req, res) -> new GetUserRoute(memDB, req.params(":username")).handle(req, res), json());

        post("/users","application/json", (req, res) -> new PostUserRoute(memDB).handle(req, res), json());
        get("/users", "application/json", (req, res) -> new ListUsersRoute(memDB).handle(req, res), json());
    }

    private static boolean isPublic(Request request) {
        String requestMethod = request.requestMethod();
        return requestMethod.equals("POST") && request.pathInfo().equals("/users");
    }

    private static boolean isAuthenticated(Request request) {
        request.attribute("username","");
        String authorization = request.headers("Authorization");
        if(authorization == null || authorization.isEmpty()){
            return false;
        }

        Optional<UserPWD> userPWDOpt =  basicAuth.extract(authorization);
        if(!userPWDOpt.isPresent()) {
            return false;
        }

        String userName = userPWDOpt.get().getUsername();
        String pwd = memDB.getPWDForUser(userName);
        boolean isAuthenticated = checkPWD(pwd, userPWDOpt.get().getPwd());
        if(isAuthenticated) {
            request.attribute("username", userName);
        }

        return isAuthenticated;
    }

    private static boolean checkPWD(String pwd, String unsurePwd) {
        return pwd != null && !pwd.isEmpty() && pwd.equals(unsurePwd);
    }

}
