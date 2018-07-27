package com.pl.exaco.builder_pro.utils;

public class AuthenticationHelper {

    public static final String HEADER_FIELD = "Auth-Token";
    public static final String TOKEN = "nb5Pq6J4YniMlDwtpE7c5cDN4cTwbB4J";

    public static void Authorize(String token) throws Exception {
        if(!token.equals(TOKEN)){
            throw new Exception("Unauthorized");
        }
    }
}
