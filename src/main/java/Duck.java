import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;


public class Duck {
    static int BY_COMMAND_BUFFER = 4;
    static int FROM_COMMAND_BUFFER = 6;
    static int TO_COMMAND_BUFFER = 4;

    static String DIVIDER = "____________________________________________________________";
    static String LOGO = """
             _____             _
            |  __  \\_   _  ___| | __
            | |  | | | | |/ __| |/ /   _
            | |__| | |_| | (__|   <  <(.)___
            |_____/ \\__,_|\\___|_|\\_\\  (____/
            """;
    static private final ArrayList<Task> taskList = new ArrayList<>();
    static private int taskCounter = 0;

    public static void main(String[] args) {
        printWelcomeMessage();
        while (true) {
            try {
                processUserInput();
            } catch (Exception e) {
                break;
            }
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("Hello! I'm \n" + LOGO);
        System.out.println("What can I do for you?");
        System.out.println(DIVIDER);
    }

    private static void processUserInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(DIVIDER);

        String command = input.toLowerCase();

        switch (command) {
        case "bye": {
            exitProgram();
            break;
        }
        case "list": {
            printList();
            break;
        }
        default: {
            String[] splitInput = input.split(" ");
            try {
                switch (splitInput[0]) {
                case "mark": {
                    markAsDone(Integer.parseInt(splitInput[1]));
                    break;
                }
                case "unmark": {
                    markAsUndone(Integer.parseInt(splitInput[1]));
                    break;
                }
                default: {
                    addToList(input);
                }
                }
            } catch (Exception e) {
                System.out.println("Something went wrong :(");
            }
        }
        }
    }


    private static void addToList(String input) {
        String[] splitInput = input.split(" ");
        String inputWithoutCommand = String.join(" ", Arrays.asList(splitInput).subList(1, splitInput.length));
        Task newTask;
        try {
            switch (splitInput[0].toLowerCase()) {
            case "todo": {
                newTask = addTodo(inputWithoutCommand);
                break;
            }
            case "deadline": {
                newTask = addDeadline(inputWithoutCommand);
                break;
            }
            case "event": {
                newTask = addEvent(inputWithoutCommand);
                break;
            }
            default: {
                // no matching types
                printNoMatchingTypesError();
                return;
            }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please try again");
            System.out.println(DIVIDER);
            return;
        }

        taskList.add(newTask);
        System.out.println("Got it. I've added this task: ");
        newTask.printTask();
        taskCounter++;
        System.out.println("Now you have " + taskCounter + " tasks added to the list.");
        System.out.println(DIVIDER);
    }

    private static Task addTodo(String input) {
        return new Todo(input);
    }

    private static Task addDeadline(String input) {
        int byDateIndex = input.indexOf("/by");
        if (byDateIndex == -1) {
            throw new NumberFormatException();
        }
        String byDate = input.substring(byDateIndex + BY_COMMAND_BUFFER);
        String title = input.substring(0, byDateIndex);
        return new Deadline(title, byDate);
    }

    private static Task addEvent(String input) {
        int fromDateTimeIndex = input.indexOf("/from");
        int toDateTimeIndex = input.indexOf("/to");
        if (fromDateTimeIndex == -1 || toDateTimeIndex == -1) {
            throw new NumberFormatException();
        }
        String fromDateTime = input.substring(fromDateTimeIndex + FROM_COMMAND_BUFFER, toDateTimeIndex);
        String toDateTime = input.substring(toDateTimeIndex + TO_COMMAND_BUFFER);
        String title = input.substring(0, fromDateTimeIndex);
        return new Event(title, fromDateTime, toDateTime);
    }

    private static void markAsDone(int taskId) {
        if (taskId > taskCounter) {
            System.out.println("Task does not exist :(");
            System.out.println(DIVIDER);
            return;
        }
        taskList.get(taskId - 1).markAsComplete();
        System.out.println("Nice! I've marked this task as done:\n  [X] " + taskList.get(taskId - 1).getTitle());
        System.out.println(DIVIDER);
    }

    private static void markAsUndone(int taskId) {
        if (taskId > taskCounter) {
            System.out.println("Task does not exist :(");
            System.out.printf(DIVIDER);
            return;
        }
        taskList.get(taskId - 1).markAsUncomplete();
        System.out.println("Ok, I've marked this task as not done yet:\n  [ ] " + taskList.get(taskId - 1).getTitle());
        System.out.println(DIVIDER);
    }

    private static void printList() {
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.print((i + 1) + ". ");
            taskList.get(i).printTask();
        }
        System.out.println(DIVIDER);
    }


    private static void printNoMatchingTypesError() {
        System.out.println("No matching types, please try again :/");
        System.out.println(DIVIDER);
    }

    private static void exitProgram() {
        System.out.println("bye bye :(");
        System.out.println(DIVIDER);
        System.exit(0);
    }
}
