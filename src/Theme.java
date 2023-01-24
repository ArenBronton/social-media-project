import java.awt.*;

public class Theme {

    public static Color defaultBackground;
    public static Color defaultForeground;
    public static Color textColor;
    public static Color textColor2;
    public static Color textAreaBackground;

    public static Color lightThemeDefaultBackground = new Color(238, 238, 238);
    public static Color lightThemeDefaultForeground = Color.LIGHT_GRAY;
    public static Color lightThemeTextColor = Color.BLACK;
    public static Color lightThemeTextColor2 = Color.GRAY;
    public static Color lightThemeTextAreaBackground = new Color(222, 222, 222);

    public static Color darkThemeDefaultBackground = new Color(50, 50, 50);
    public static Color darkThemeDefaultForeground = new Color(63, 63, 63);
    public static Color darkThemeTextColor = Color.WHITE;
    public static Color darkThemeTextColor2 = Color.LIGHT_GRAY;
    public static Color darkThemeTextAreaBackground = new Color(26, 26, 26, 255);

    public static void darkTheme() {
        defaultForeground = darkThemeDefaultForeground;
        defaultBackground = darkThemeDefaultBackground;
        textColor = darkThemeTextColor;
        textColor2 = darkThemeTextColor2;
        textAreaBackground = darkThemeTextAreaBackground;
    }

    public static void lightTheme() {
        defaultForeground = lightThemeDefaultForeground;
        defaultBackground = lightThemeDefaultBackground;
        textColor = lightThemeTextColor;
        textColor2 = lightThemeTextColor2;
        textAreaBackground = lightThemeTextAreaBackground;
    }
}
