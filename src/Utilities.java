public class Utilities {

    public static int theme = 1;
    public static final int lightTheme = 1;
    public static final int darkTheme = 2;

    public static String removeSpaces(String text) {
        StringBuilder newString = new StringBuilder();

        for(int i = 0; i < text.length(); i++) {
            if (!(text.charAt(i) == ' ')) {
                newString.append(text.charAt(i));
            }
        }

        return newString.toString();
    }
}
