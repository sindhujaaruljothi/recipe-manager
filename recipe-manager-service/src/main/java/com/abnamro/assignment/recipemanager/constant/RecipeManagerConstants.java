package com.abnamro.assignment.recipemanager.constant;

public class RecipeManagerConstants {
    public static final String USERID_EXIST = "UserId already exist";
    public static final String USERID_REGEX = "^(.+)@(.+)$";
    public static final String USERID_NOT_VALID = "UserId is not valid";
    public static final String PASSWORD_REGEX = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
    public static final String PASSWORD_NOT_VALID = "Password must have 8 characters with atleast one uppercase,one lowercase,one digit,one special character";
    public static final String USER_NOT_FOUND = "UserId not found";
    public static final String USER_NOT_AUTHORIZED = "User not authorized to perform this function";
    public static final String USER_DISABLED = "USER_DISABLED";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String INVALID_JWT_TOKEN = "Invalid JWT token";
    public static final String JWT_TOKEN_EXPIRED = "JWT token expired";

    private RecipeManagerConstants() {

    }
}
