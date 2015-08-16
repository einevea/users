package com.einevault.users.routes;

import com.einevault.users.MemDB;
import com.einevault.users.model.Role;
import com.einevault.users.model.SimpleUser;
import com.einevault.users.model.User;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by einevea on 16/08/15.
 */
public class ListUsersRoute extends AbstractRoute {


    public ListUsersRoute(MemDB memDB) {
        super(memDB);
    }

    @Override
    public Object doHandle(Request request, Response response, User user) {
        String searchTerm = getSearchTerm(request);

        Stream<User> usersStream = memDB.getUsers(searchTerm);

        if(user.getRole() == Role.admin){
            return usersStream.collect(Collectors.toList());
        }else{
            List<SimpleUser> simpleUserList = usersStream.map(user1 -> new SimpleUser(user1.getUsername())).collect(Collectors.toList());
            return  simpleUserList;
        }
    }

    private String getSearchTerm(Request request) {
        String search = request.queryParams("search");
        return search == null? "" : search;
    }
}
