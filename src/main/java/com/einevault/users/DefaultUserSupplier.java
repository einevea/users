package com.einevault.users;

import com.einevault.users.model.User;

import java.util.function.Supplier;

/**
 * Created by einevea on 16/08/15.
 */
public class DefaultUserSupplier implements Supplier<User> {
    @Override
    public User get() {
        return User.ANONYMOUS;
    }
}
