public class User {

    public static String name = "Couldn't Find Name";
    public static String password = "Couldn't Find Password";

    public static void init(String name, String password) {
        User.name = name;
        User.password = password;
    }

    public static String getName() {
        return name;
    }

    public static String getPassword() {
        return password;
    }

}
