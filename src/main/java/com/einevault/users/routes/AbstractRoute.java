package com.einevault.users.routes;

import com.einevault.users.DefaultUserSupplier;
import com.einevault.users.MemDB;
import com.einevault.users.model.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

/**
 * Created by einevea on 16/08/15.
 */
public abstract class AbstractRoute implements Route {

    protected final MemDB memDB;
    private final DefaultUserSupplier defaultUserSupplier = new DefaultUserSupplier();
    private final boolean respJson;

    public AbstractRoute(MemDB memDB){
        this(memDB, true);
    }

    public AbstractRoute(MemDB memDB, boolean respJson){
        this.memDB = memDB;
        this.respJson = respJson;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Object username = request.attribute("username");

        Optional<User> userOpt;
        if(username == null){
            userOpt = Optional.empty();
        }else{
            userOpt = memDB.getUser(username.toString());
        }

        if(respJson){
            response.header("Content-Type","application/json");
        }

        return doHandle(request, response, userOpt.orElseGet(defaultUserSupplier));
    }

    public abstract Object doHandle(Request request, Response response, User user);
}
