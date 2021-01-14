package com.desapp.socialbox.services.network;

public abstract class ApiEndpoint {
    // private static String host = "192.168.1.70:8080";
    private static String host = "10.0.2.2:8080";
    private static String baseUrl = "http://" + host + "/api/";

    public static  String login = baseUrl + "users/login";
    public static String signUp = baseUrl + "users/";
    public  static  String findAccount = baseUrl + "users/nombre";
    public  static  String findAccountByUsername = baseUrl + "users/username";
}
