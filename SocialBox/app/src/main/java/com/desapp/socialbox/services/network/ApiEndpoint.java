package com.desapp.socialbox.services.network;

public abstract class ApiEndpoint {
    private static String host = "192.168.1.68";
    private static String baseUrl = "http://" + host + "/api/";

    public static String signUp = baseUrl + "/users";
}
