package com.einevault.users;

import static spark.Spark.get;

/**
 * Created by einevea on 16/08/15.
 */
public class UsersApp {

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}
