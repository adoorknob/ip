package duke;

public class Echo {
    static String DIVIDER = "____________________________________________________________";

    public static void echoText(String input) {
        printBubbleSaying(input);
        System.out.println(DIVIDER);
    }

    private static void printTalkingDuckWithLeftMargin(int margin) {
        for (int i = 0; i < margin; i++) {
            System.out.print(" ");
        }
        System.out.println("  _");
        for (int i = 0; i < margin; i++) {
            System.out.print(" ");
        }
        System.out.println(">(.)___");
        for (int i = 0; i < margin; i++) {
            System.out.print(" ");
        }
        System.out.println(" (____/");
    }

    private static void printBubbleSaying(String input) {
        // print top
        System.out.print("  __");
        for (int i = 0; i < input.length(); i++) {
            System.out.print("_");
        }
        System.out.println("__  ");

        // print border
        System.out.print(" /  ");
        for (int i = 0; i < input.length(); i++) {
            System.out.print(" ");
        }
        System.out.println("  \\");

        // print middle + text
        System.out.println("|   " + input + "   |");

        // print bottom border
        System.out.print(" \\  ");
        for (int i = 0; i < input.length(); i++) {
            System.out.print(" ");
        }
        System.out.println("  /");

        // print bottom
        System.out.print("  __");
        for (int i = 0; i < input.length() - 1; i++) {
            System.out.print("_");
        }
        System.out.println("  /");

        // print bubble tail
        System.out.print("    ");
        for (int i = 0; i < input.length() - 1; i++) {
            System.out.print(" ");
        }
        System.out.println("\\/");

        // print duck :)
        printTalkingDuckWithLeftMargin(input.length());
    }
}
