import java.util.Scanner;
import java.util.ArrayList;

public class Duck {
    static String divider = "____________________________________________________________";
    static String logo = " _____             _    \n"
            + "|  __  \\_   _  ___| | __\n"
            + "| |  | | | | |/ __| |/ /   _\n"
            + "| |__| | |_| | (__|   <  <(.)___\n"
            + "|_____/ \\__,_|\\___|_|\\_\\  (____/\n";
    static private ArrayList<String> list = new ArrayList<String>();

    public static void main(String[] args) {
        printWelcomeMessage();
        while (true) {
            processUserInput();
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("Hello! I'm \n" + logo);
        System.out.println("What can I do for you?");
        System.out.println(divider);
    }

    private static void processUserInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(divider);

        if (input.equalsIgnoreCase("bye")) {
            exitProgram();
        }

        if (input.equalsIgnoreCase("list")) {
            printList();
        } else {
            addToList(input);
        }
//        echoText(input);
    }

    private static void echoText(String input) {
        printBubbleSaying(input);
        System.out.println(divider);
    }

    private static void addToList(String input) {
        list.add(input);
        System.out.println("added: " + input);
        System.out.println(divider);
    }

    private static void printList() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
        System.out.println(divider);
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

    private static void exitProgram() {
        System.out.println("bye bye :(");
        System.out.println(divider);
        System.exit(0);
    }
}
