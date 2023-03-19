package fr.diskmth.loggy;

import java.util.Arrays;

public class ANSI
{
    /*---------------------------------------- Static constants ----------------------------------------*/
    public static final ANSI RESET = new ANSI("\u001B[0m");
    public static final ANSI BOLD = new ANSI("\u001B[1m");
    public static final ANSI ITALIC = new ANSI("\u001B[3m");
    public static final ANSI UNDERLINE = new ANSI("\u001B[4m");
    public static final ANSI REVERSE = new ANSI("\u001B[7m");
    public static final ANSI STRIKETHROUGH = new ANSI("\u001B[9m");



    public static final ANSI BLACK = new ANSI("\u001B[30m");
    public static final ANSI RED = new ANSI("\u001B[31m");
    public static final ANSI GREEN = new ANSI("\u001B[32m");
    public static final ANSI YELLOW = new ANSI("\u001B[33m");
    public static final ANSI BLUE = new ANSI("\u001B[34m");
    public static final ANSI MAGENTA = new ANSI("\u001B[35m");
    public static final ANSI CYAN = new ANSI("\u001B[36m");
    public static final ANSI WHITE = new ANSI("\u001B[37m");

    public static final ANSI BLACK_BRIGHT = new ANSI("\u001B[90m");
    public static final ANSI RED_BRIGHT = new ANSI("\u001B[91m");
    public static final ANSI GREEN_BRIGHT = new ANSI("\u001B[92m");
    public static final ANSI YELLOW_BRIGHT = new ANSI("\u001B[93m");
    public static final ANSI BLUE_BRIGHT = new ANSI("\u001B[94m");
    public static final ANSI MAGENTA_BRIGHT = new ANSI("\u001B[95m");
    public static final ANSI CYAN_BRIGHT = new ANSI("\u001B[96m");
    public static final ANSI WHITE_BRIGHT = new ANSI("\u001B[97m");



    public static final ANSI BLACK_BG = new ANSI("\u001B[40m");
    public static final ANSI RED_BG = new ANSI("\u001B[41m");
    public static final ANSI GREEN_BG = new ANSI("\u001B[42m");
    public static final ANSI YELLOW_BG = new ANSI("\u001B[43m");
    public static final ANSI BLUE_BG = new ANSI("\u001B[44m");
    public static final ANSI MAGENTA_BG = new ANSI("\u001B[45m");
    public static final ANSI CYAN_BG = new ANSI("\u001B[46m");
    public static final ANSI WHITE_BG = new ANSI("\u001B[47m");

    public static final ANSI BLACK_BRIGHT_BG = new ANSI("\u001B[100m");
    public static final ANSI RED_BRIGHT_BG = new ANSI("\u001B[101m");
    public static final ANSI GREEN_BRIGHT_BG = new ANSI("\u001B[102m");
    public static final ANSI YELLOW_BRIGHT_BG = new ANSI("\u001B[103m");
    public static final ANSI BLUE_BRIGHT_BG = new ANSI("\u001B[104m");
    public static final ANSI MAGENTA_BRIGHT_BG = new ANSI("\u001B[105m");
    public static final ANSI CYAN_BRIGHT_BG = new ANSI("\u001B[106m");
    public static final ANSI WHITE_BRIGHT_BG = new ANSI("\u001B[107m");

    /*---------------------------------------- Static methods ----------------------------------------*/

    public static ANSI multiple(ANSI... formats)
    {
        final StringBuilder concatFormats = new StringBuilder();
        Arrays.asList(formats).forEach(format -> concatFormats.append(format.getCode()));
        return new ANSI(concatFormats.toString());
    }

    public static String format(String message, ANSI... formats)
    {
        final StringBuilder formattedMessage = new StringBuilder();
        Arrays.asList(formats).forEach(format -> formattedMessage.append(format.getCode()));
        return formattedMessage.append(message).append(ANSI.RESET.getCode()).toString();
    }

    public static String clearFormat(String message)
    {
        return message.replaceAll("\033\\[[;\\d]*m", "");
    }

    /*---------------------------------------- Variables and constants ----------------------------------------*/

    protected final String code;

    /*---------------------------------------- Constructors ----------------------------------------*/

    public ANSI(String code)
    {
        this.code = code;
    }

    /*---------------------------------------- Getters ----------------------------------------*/

    public String getCode()
    {
        return code;
    }
}
