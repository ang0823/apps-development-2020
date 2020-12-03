package com.desapp.socialbox.services.network;

public abstract class ApiEndpoint {
    private static String host = "192.168.1.68:8080";
    // private static String host = "10.0.2.2:8008";
    private static String baseUrl = "http://" + host + "/api";

    public static String signUp = baseUrl + "/users/";
}
