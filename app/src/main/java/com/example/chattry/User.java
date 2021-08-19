package com.example.chattry;

public class User {
    private static String nickname;

    public static String getNickname() {
        return nickname;
    }

    public static void setNickname(String nickname) {
        User.nickname = nickname;
    }
}
