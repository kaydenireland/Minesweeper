enum TextColor {
    BLACK(30, 40),
    RED(31, 41),
    GREEN(32, 42),
    YELLOW(33, 43),
    BLUE(34, 44),
    PURPLE(35, 45),
    CYAN(36, 46),
    WHITE(37, 47);

    private final int fg;
    private final int bg;

    TextColor(int fg, int bg) {
        this.fg = fg;
        this.bg = bg;
    }

    int fg() { return fg; }
    int bg() { return bg; }
}


enum TextStyle {
    BOLD(1),
    DIM(2),
    ITALIC(3),
    UNDERLINE(4),
    INVERSE(7),
    STRIKETHROUGH(9);

    private final int code;

    TextStyle(int code) {
        this.code = code;
    }

    int code() { return code; }
}


public final class ColorPrinter {

    private static final String ESC = "\u001B[";
    private static final String RESET = ESC + "0m";

    private ColorPrinter() {} // utility class

    public static String format(
            String text,
            TextColor fg,
            TextColor bg,
            TextStyle... styles
    ) {
        StringBuilder sb = new StringBuilder(ESC);
        boolean first = true;

        if (styles != null) {
            for (TextStyle s : styles) {
                if (!first) sb.append(';');
                sb.append(s.code());
                first = false;
            }
        }

        if (fg != null) {
            if (!first) sb.append(';');
            sb.append(fg.fg());
            first = false;
        }

        if (bg != null) {
            if (!first) sb.append(';');
            sb.append(bg.bg());
        }

        sb.append('m')
                .append(text)
                .append(RESET);

        return sb.toString();
    }

    public static void lines(int count) {
        for(int i = 0; i < count; i++) {
            System.out.println();
        }
    }

    public static void print(String text, TextColor fg, TextColor bg, TextStyle... styles) {
        System.out.print(format(text, fg, bg, styles));
    }

    public static void print(String text, TextColor fg) {
        System.out.print(format(text, fg, null, null));
    }

    public static void print(String text, TextColor fg, TextColor bg) {
        System.out.print(format(text, fg, bg, null));
    }

    public static void print(String text, TextColor fg, TextStyle... styles) {
        System.out.print(format(text, fg, null, styles));
    }

    public static void print(String text,TextStyle... styles) {
        System.out.print(format(text, null, null, styles));
    }

    public static void println(String text, TextColor fg, TextColor bg, TextStyle... styles) {
        System.out.println(format(text, fg, bg, styles));
    }

    public static void println(String text, TextColor fg) {
        System.out.print(format(text, fg, null, null));
    }

    public static void println(String text, TextColor fg, TextColor bg) {
        System.out.print(format(text, fg, bg, null));
    }

    public static void println(String text, TextColor fg, TextStyle... styles) {
        System.out.println(format(text, fg, null, styles));
    }

    public static void println(String text,TextStyle... styles) {
        System.out.println(format(text, null, null, styles));
    }
}
