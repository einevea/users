package com.einevault.users.routes;

import com.einevault.users.MemDB;
import com.einevault.users.model.Role;
import com.einevault.users.model.SimpleUser;
import com.einevault.users.model.User;
import spark.Request;
import spark.Response;

import java.util.Optional;

/**
 * Created by einevea on 16/08/15.
 */
public class GetConnectionsRoute extends AbstractUserRoute {


    public GetConnectionsRoute(MemDB memDB, String userName) {
        super(memDB, userName);
    }

    @Override
    public Object doHandle(Request request, Response response, User user) {
        Optional<User> userOpt = memDB.getUser(userName);

        if(userOpt.isPresent()) {
            if (user.getRole() == Role.admin || userOpt.get().getUsername().equals(user)) {
                return userOpt.get().getConnections();
            } else {
                response.status(403);
                return "Forbidden";
            }
        }else{
            response.status(404);
            return "Not Found";
        }
    }

}
