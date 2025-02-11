import java.util.*;
import java.util.function.Function;


public class Duck {
    static final String DIVIDER = "____________________________________________________________";
    static final String LOGO = """
             _____             _
            |  __  \\_   _  ___| | __
            | |  | | | | |/ __| |/ /     _
            | |__| | |_| | (__|   <    <(.)___
            |_____/ \\__,_|\\___|_|\\_\\    (____/
            """;

    static int BY_COMMAND_BUFFER = 4;
    static int FROM_COMMAND_BUFFER = 6;
    static int TO_COMMAND_BUFFER = 4;

    static final String COMMAND_LIST = "list";
    static final String COMMAND_MARK = "mark";
    static final String COMMAND_UNMARK = "unmark";
    static final ArrayList<String> COMMAND_EXIT_LIST = new ArrayList<String>(Arrays.asList(
            "bye",
            "exit",
            "quit"
    ));

    static final String TASK_NAME_TODO = "todo";
    static final String TASK_NAME_DEADLINE = "deadline";
    static final String TASK_NAME_EVENT = "event";

    static final String MESSAGE_WELCOME = "Hello! I'm \n" + LOGO + "\n What can I do for you?";
    static final String MESSAGE_EXIT = "bye bye :(";
    static final String MESSAGE_ACKNOWLEDGE_TASK_ADDED = "Got it. I've added this task:";
    static final String MESSAGE_ACKNOWLEDGE_MARK_COMMAND = "Nice! I've marked this task as done:";
    static final String MESSAGE_ACKNOWLEDGE_UNMARK_COMMAND = "Ok, I've marked this task as not done yet:";

    static final String ERROR_NO_MATCHING_TYPES = "No matching types, please try again :/";
    static final String ERROR_INVALID_INPUT = "Invalid input, please try again :0";

    static final Map<String, Function<String, Task>> addTaskCommandMap = Map.ofEntries(
            Map.entry(TASK_NAME_TODO, Duck::addTodo),
            Map.entry(TASK_NAME_DEADLINE, Duck::addDeadline),
            Map.entry(TASK_NAME_EVENT, Duck::addEvent)
    );

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
        System.out.println(MESSAGE_WELCOME);
        System.out.println(DIVIDER);
    }

    private static void processUserInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(DIVIDER);

        String command = input.toLowerCase();
        if (isExitCommand(command)) {
            exitProgram();
        }

        // switch case for scalability
        switch (command) {
        case COMMAND_LIST: {
            printList();
            break;
        }
        default: {
            markOrCreateTask(input);
        }
        }
    }

    private static void markOrCreateTask(String input) {
        String[] splitInput = input.split(" ");
        try {
            switch (splitInput[0]) {
            case COMMAND_MARK: {
                markAsDone(Integer.parseInt(splitInput[1]));
                break;
            }
            case COMMAND_UNMARK: {
                markAsUndone(Integer.parseInt(splitInput[1]));
                break;
            }
            default: {
                addToList(input);
            }
            }
        } catch (Exception e) {
            System.out.println(ERROR_INVALID_INPUT);
            System.out.println(DIVIDER);
        }
    }

    private static void addToList(String input) {
        String[] splitInput = input.split(" ");
        String command = splitInput[0];
        String inputWithoutCommand = String.join(" ", Arrays.asList(splitInput).subList(1, splitInput.length));
        Task newTask;

        if (addTaskCommandMap.containsKey(command)) {
            newTask = addTaskCommandMap.get(command).apply(inputWithoutCommand);
        } else {
            printNoMatchingTypesError();
            return;
        }

        taskList.add(newTask);
        System.out.println(MESSAGE_ACKNOWLEDGE_TASK_ADDED);
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
        System.out.println(MESSAGE_ACKNOWLEDGE_MARK_COMMAND);
        taskList.get(taskId-1).printTask();
        System.out.println(DIVIDER);
    }

    private static void markAsUndone(int taskId) {
        if (taskId > taskCounter) {
            System.out.println("Task does not exist :(");
            System.out.printf(DIVIDER);
            return;
        }
        taskList.get(taskId - 1).markAsUncomplete();
        System.out.println(MESSAGE_ACKNOWLEDGE_UNMARK_COMMAND);
        taskList.get(taskId-1).printTask();
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
        System.out.println(ERROR_NO_MATCHING_TYPES);
        System.out.println(DIVIDER);
    }

    private static boolean isExitCommand(String command) {
        return COMMAND_EXIT_LIST.contains(command);
    }

    private static void exitProgram() {
        System.out.println(MESSAGE_EXIT);
        System.out.println(DIVIDER);
        System.exit(0);
    }
}
