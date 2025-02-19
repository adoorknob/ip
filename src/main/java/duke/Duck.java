package duke;

import duke.exception.EmptyTodoException;
import duke.exception.InvalidCommandException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.IOException;
import java.util.*;
import java.io.FileWriter;


public class Duck {
    static final String outputFilePath = "src/main/java/duke/data/duckOutput.txt";
    static String outputList = "";

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
    static final String COMMAND_DELETE= "delete";
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
    static final String MESSAGE_ACKNOWLEDGE_DELETE_COMMAND = "I've deleted this task:";

    static final String ERROR_EMPTY_TODO = "u doing nothing ah :/";
    static final String ERROR_INVALID_COMMAND = "huh :0";

    static final Map<String, ThrowingFunction<String, Task>> addTaskCommandMap = Map.ofEntries(
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
                updateFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                break;
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
            case COMMAND_DELETE: {
                deleteFromList(Integer.parseInt(splitInput[1]));
                break;
            }
            default: {
                addToList(input);
            }
            }
        } catch (InvalidCommandException e) {
            System.out.println(ERROR_INVALID_COMMAND);
            System.out.println(DIVIDER);
        }
    }

    private static void addToList(String input) throws InvalidCommandException {
        String[] splitInput = input.split(" ");
        String command = splitInput[0];
        String inputWithoutCommand = String.join(" ", Arrays.asList(splitInput).subList(1, splitInput.length));
        Task newTask;

        if (addTaskCommandMap.containsKey(command)) {
            try {
                newTask = addTaskCommandMap.get(command).apply(inputWithoutCommand);
            } catch (EmptyTodoException e) {
                printEmptyTodoError();
                return;
            } catch (Exception e) {
                System.out.println("Error adding task: " + input);
                throw new InvalidCommandException();
            }
        } else {
            throw new InvalidCommandException();
        }

        taskList.add(newTask);
        System.out.print(MESSAGE_ACKNOWLEDGE_TASK_ADDED + "\n  ");
        newTask.printTask();
        taskCounter++;
        printTaskCounter();
    }

    private static void deleteFromList(int taskId) throws InvalidCommandException {
        if (taskId > taskCounter) {
            System.out.println("Task does not exist :(");
            System.out.println(DIVIDER);
            return;
        }
        System.out.print(MESSAGE_ACKNOWLEDGE_DELETE_COMMAND + "\n  ");
        taskList.get(taskId-1).printTask();
        taskList.remove(taskId - 1);
        taskCounter--;
        printTaskCounter();
    }

    private static void printTaskCounter() {
        System.out.println("Now you have " + taskCounter + " tasks in the list.");
        System.out.println(DIVIDER);
    }

    private static Task addTodo(String input) throws EmptyTodoException {
        if (input.isEmpty()) {
            throw new EmptyTodoException();
        }
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
        System.out.print(MESSAGE_ACKNOWLEDGE_MARK_COMMAND + "\n  ");
        taskList.get(taskId-1).printTask();
        System.out.println(DIVIDER);
    }

    private static void markAsUndone(int taskId) {
        if (taskId > taskCounter) {
            System.out.println("Task does not exist :(");
            System.out.printf(DIVIDER);
            return;
        }
        taskList.get(taskId - 1).markAsIncomplete();
        System.out.println(MESSAGE_ACKNOWLEDGE_UNMARK_COMMAND);
        taskList.get(taskId-1).printTask();
        System.out.println(DIVIDER);
    }

    private static void printList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.print((i + 1) + ". ");
            taskList.get(i).printTask();
        }
        System.out.println(DIVIDER);
    }

    private static void updateFile() throws IOException {
        FileWriter fw = new FileWriter(outputFilePath);
        updateOutputList();
        fw.write(outputList);
        fw.close();
    }

    private static void updateOutputList() {
        outputList = "";
        for (Task task : taskList) {
            outputList += task.toString() + "\n";
        }
    }

    private static void printEmptyTodoError() {
        System.out.println(ERROR_EMPTY_TODO);
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
