package com.einevault.users.rutes;

import com.einevault.users.MemDB;
import com.einevault.users.model.Role;
import com.einevault.users.model.SimpleUser;
import com.einevault.users.model.User;
import com.einevault.users.routes.ListUsersRoute;
import static org.mockito.Mockito.*;

import org.junit.Test;
import static org.junit.Assert.*;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by einevea on 16/08/15.
 */
public class ListUsersRouteTest {


    @Test
     public void getListOfUsersAsAdmin(){
        final List<User> usersList = new ArrayList<>();
        usersList.add(new User("Test", Role.register));

        MemDB memDB = mock(MemDB.class);
        when(memDB.getUsers(anyString())).thenReturn(
                usersList.stream()
        );

        Request request = mock(Request.class);
        Response response = mock(Response.class);

        ListUsersRoute listUsersRoute = new ListUsersRoute(memDB);
        Object list = listUsersRoute.doHandle(request, response, new User("Admin", Role.admin));

        assertEquals(usersList, list);
        verify(memDB, times(1)).getUsers(anyString());
        verify(request, times(1)).queryParams("search");
    }

    @Test
    public void getListOfUsersAsRegistered(){
        User testUser = new User("Test", Role.register);
        final List<User> usersList = new ArrayList<>();
        usersList.add(testUser);

        MemDB memDB = mock(MemDB.class);
        when(memDB.getUsers(anyString())).thenReturn(
                usersList.stream()
        );

        Request request = mock(Request.class);
        Response response = mock(Response.class);

        ListUsersRoute listUsersRoute = new ListUsersRoute(memDB);
        Object list = listUsersRoute.doHandle(request, response, new User("Registered", Role.register));


        assertTrue(List.class.isInstance(list));
        List castedList = (List)list;

        assertEquals(1, castedList.size());
        Object user = castedList.get(0);
        assertTrue(SimpleUser.class.isInstance(user));
        assertEquals(testUser.getUsername(), ((SimpleUser)user).getUsername());

        verify(memDB, times(1)).getUsers(anyString());
        verify(request, times(1)).queryParams("search");
    }
}
