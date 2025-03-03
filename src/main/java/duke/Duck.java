package duke;

import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.filehandler.FileHandler;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.ui.Ui;
import duke.task.TaskList;

import java.io.IOException;
import java.util.*;

public class Duck {
    static final String outputFilePath = "src/main/java/duke/data/duckOutput.txt";

    public static int BY_COMMAND_BUFFER = 4;
    public static int FROM_COMMAND_BUFFER = 6;
    public static int TO_COMMAND_BUFFER = 4;

    static final String COMMAND_LIST = "list";
    static final String COMMAND_MARK = "mark";
    static final String COMMAND_UNMARK = "unmark";
    static final String COMMAND_DELETE= "delete";
    static final ArrayList<String> COMMAND_EXIT_LIST = new ArrayList<>(Arrays.asList(
            "bye",
            "exit",
            "quit"
    ));

    static final String TASK_NAME_TODO = "todo";
    static final String TASK_NAME_DEADLINE = "deadline";
    static final String TASK_NAME_EVENT = "event";

    static final Map<String, ThrowingFunction<String, Task>> addTaskCommandMap = Map.ofEntries(
            Map.entry(TASK_NAME_TODO, TaskList::addTodo),
            Map.entry(TASK_NAME_DEADLINE, TaskList::addDeadline),
            Map.entry(TASK_NAME_EVENT, TaskList::addEvent)
    );

    private final Ui ui;
    private TaskList taskList;
    private boolean isProgramRunning;
    private final FileHandler fileHandler;

    public Duck() {
        ui = new Ui();
        taskList = new TaskList();
        isProgramRunning = true;
        fileHandler = new FileHandler(outputFilePath);
    }

    private void runDuck() {
        try {
            taskList = fileHandler.loadOldFile();
            ui.printList(taskList);
        } catch (IOException e) {
            System.out.println("Unable to load old file. Abort.");
            return;
        }

        ui.printWelcomeMessage();

        while (isProgramRunning) {
            try {
                processUserInput();
                fileHandler.updateFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                break;
            } catch (Exception e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Duck().runDuck();
    }

    private void processUserInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String command = input.toLowerCase();
        if (isExitCommand(command)) {
            exitProgram();
        }

        // switch case for scalability
        switch (command) {
        case COMMAND_LIST: {
            ui.printList(taskList);
            break;
        }
        default: {
            markOrCreateTask(input);
        }
        }
    }

    private void markOrCreateTask(String input) {
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
            ui.printInvalidCommandError();
        }
    }

    private void addToList(String input) throws InvalidCommandException {
        String[] splitInput = input.split(" ");
        String command = splitInput[0];
        String inputWithoutCommand = String.join(" ", Arrays.asList(splitInput).subList(1, splitInput.length));
        Task newTask;

        if (addTaskCommandMap.containsKey(command)) {
            try {
                newTask = addTaskCommandMap.get(command).apply(inputWithoutCommand);
            } catch (EmptyTaskNameException e) {
                ui.printEmptyTaskNameError();
                return;
            } catch (Exception e) {
                ui.printCannotAddTaskError(input);
                throw new InvalidCommandException();
            }
        } else {
            throw new InvalidCommandException();
        }

        taskList.addTask(newTask);
        ui.printTaskAddAcknowledgement(newTask);
        ui.printTaskCounter(taskList);
    }

    private void deleteFromList(int taskId) throws InvalidCommandException {
        if (taskId > taskList.getTaskCounter()) {
            ui.printTaskNotFoundError();
            return;
        }
        ui.printTaskDeleteAcknowledgement(taskList.getTask(taskId - 1));
        taskList.removeTask(taskId - 1);
        ui.printTaskCounter(taskList);
    }

    private void markAsDone(int taskId) {
        if (taskId > taskList.getTaskCounter()) {
            ui.printTaskNotFoundError();
            return;
        }
        taskList.getTask(taskId - 1).markAsComplete();
        ui.printTaskMarkAcknowledgement(taskList.getTask(taskId - 1));
    }

    private void markAsUndone(int taskId) {
        if (taskId > taskList.getTaskCounter()) {
            ui.printTaskNotFoundError();
            return;
        }
        taskList.getTask(taskId - 1).markAsIncomplete();
        ui.printTaskUnmarkAcknowledgement(taskList.getTask(taskId - 1));
    }

    private boolean isExitCommand(String command) {
        return COMMAND_EXIT_LIST.contains(command);
    }

    private void exitProgram() {
        this.isProgramRunning = false;
        ui.printExitMessage();
        System.exit(0);
    }
}
