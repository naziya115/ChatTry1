package Models;

public class User {
    //класс данных ;(

    private static String nickname;

    public static String getNickname() {
        return nickname;
    }

    public  void setNickname(String nickname) {
        User.nickname = nickname;
    }
}
