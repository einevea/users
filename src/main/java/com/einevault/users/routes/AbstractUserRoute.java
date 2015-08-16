package com.einevault.users.routes;

import com.einevault.users.MemDB;
import com.einevault.users.model.Role;
import com.einevault.users.model.User;
import spark.Request;
import spark.Response;

/**
 * Created by einevea on 16/08/15.
 */
public abstract class AbstractUserRoute extends AbstractRoute  {

    protected final String userName;

    public AbstractUserRoute(MemDB memDB, String userName) {
        super(memDB);
        this.userName = userName;
    }
}
