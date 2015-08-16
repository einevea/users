package com.einevault.users.routes;

import com.einevault.users.MemDB;
import com.einevault.users.model.User;
import com.einevault.users.model.UserPWD;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.Optional;

/**
 * Created by einevea on 16/08/15.
 */
public class PostUserRoute extends AbstractRoute  {

    public PostUserRoute(MemDB memDB) {
        super(memDB);
    }

    @Override
    public Object doHandle(Request request, Response response, User user) {
        String body = request.body();
        UserPWD userPWD = new Gson().fromJson(body, UserPWD.class);

        if(userPWD == null || userPWD.getUsername() == null){
            response.status(400);
            return "Bad request";
        }

        Optional<User> userOpt = memDB.createUser(userPWD);

        if(userOpt.isPresent()){
            response.header("Location", request.uri()+"/"+userOpt.get().getUsername());
            response.status(201);
            return "Created";
        }else{
            response.status(409);
            return "Conflict";
        }
    }

}
