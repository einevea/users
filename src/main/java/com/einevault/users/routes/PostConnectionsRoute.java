package com.einevault.users.routes;

import com.einevault.users.MemDB;
import com.einevault.users.model.Role;
import com.einevault.users.model.SimpleUser;
import com.einevault.users.model.User;
import com.einevault.users.model.UserPWD;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.Optional;

/**
 * Created by einevea on 16/08/15.
 */
public class PostConnectionsRoute extends AbstractUserRoute  {


    public PostConnectionsRoute(MemDB memDB, String userName) {
        super(memDB, userName);
    }

    @Override
    public Object doHandle(Request request, Response response, User user) {

        String body = request.body();
        UserPWD userPWD = new Gson().fromJson(body, UserPWD.class);

        if(userPWD == null || userPWD.getUsername() == null){
            response.status(400);
            return "Bad request";
        }

        Optional<User> toConnectUserOpt = memDB.getUser(userPWD.getUsername());
        Optional<User> userOpt = memDB.getUser(userName);

        if(userOpt.isPresent() && toConnectUserOpt.isPresent()) {
            userOpt.get().addConnection(toConnectUserOpt.get());

            response.header("Location", request.uri()+"/"+userOpt.get().getUsername()+"/"+toConnectUserOpt.get().getUsername());
            response.status(201);
            return "Created";
        }else{
            response.status(404);
            return "Not Found";
        }
    }

}
