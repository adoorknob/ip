package duke.ui;

import duke.Echo;
import duke.task.Task;
import duke.task.TaskList;

public class Ui {

    static final String DIVIDER = "____________________________________________________________";
    static final String LOGO = """
             _____             _
            |  __  \\_   _  ___| | __
            | |  | | | | |/ __| |/ /     _
            | |__| | |_| | (__|   <    <(.)___
            |_____/ \\__,_|\\___|_|\\_\\    (____/
            """;

    static final String MESSAGE_WELCOME = "Hello! I'm \n" + LOGO + "\n What can I do for you?";
    static final String MESSAGE_EXIT = "bye bye :(";
    static final String MESSAGE_PRINT_FULL_LIST = "Here are the tasks in your list: ";
    static final String MESSAGE_PRINT_FILTERED_LIST = "Here are the tasks I've found: ";
    static final String MESSAGE_ACKNOWLEDGE_TASK_ADDED = "Got it. I've added this task: ";
    static final String MESSAGE_ACKNOWLEDGE_MARK_COMMAND = "Nice! I've marked this task as done: ";
    static final String MESSAGE_ACKNOWLEDGE_UNMARK_COMMAND = "Ok, I've marked this task as not done yet: ";
    static final String MESSAGE_ACKNOWLEDGE_DELETE_COMMAND = "I've deleted this task: ";

    static final String ERROR_EMPTY_TODO = "u doing nothing ah :/";
    static final String ERROR_INVALID_COMMAND = "huh :V";
    static final String ERROR_FILE_NOT_LOADED = "File not loaded :(";
    static final String ERROR_DATE_TIME_FORMAT = "Invalid datetime format";

    private void printMessage(String message) {
        Echo.echoText(message);
        System.out.println(DIVIDER);
    }

    public void printWelcomeMessage() {
        System.out.println(MESSAGE_WELCOME);
        System.out.println(DIVIDER);
    }

    public void printExitMessage() {
        printMessage(MESSAGE_EXIT);
    }

    public void printEmptyTaskNameError() {
        printMessage(ERROR_EMPTY_TODO);
    }

    public void printParserError(String errorLine) {
        printMessage("Error parsing old file: " + errorLine);
    }

    public void printFileLoadedMessage() {
        printMessage("Old tasks loaded :)");
    }

    public void printInvalidCommandError() {
        printMessage(ERROR_INVALID_COMMAND);
    }

    public void printTaskNotFoundError() {
        printMessage("Task does not exist :(");
    }

    public void printRandomError() {
        printMessage("Error executing command");
    }

    public void printFileLoadError() {
        printMessage(ERROR_FILE_NOT_LOADED);
    }

    public void printDateTimeFormatError(String dateTime) {
        printMessage(ERROR_DATE_TIME_FORMAT + ": " + dateTime);
    }

    public void printList(String acknowledgment, TaskList taskList) {
        printMessage(acknowledgment);
        for (int i = 0; i < taskList.getTasks().size(); i++) {
            System.out.println((i + 1) + ". " + taskList.getTask(i).toTaskString());
        }
        System.out.println(DIVIDER);
    }

    public void printFullList(TaskList taskList) {
        printList(MESSAGE_PRINT_FULL_LIST, taskList);
    }

    public void printFilteredList(TaskList taskList) {
        printList(MESSAGE_PRINT_FILTERED_LIST, taskList);
    }

    public void printTaskCounter(TaskList taskList) {
        printMessage("Now you have " + taskList.getTaskCounter() + " tasks in the list.");
    }

    public void printTaskAddAcknowledgement(Task task) {
        printMessage(MESSAGE_ACKNOWLEDGE_TASK_ADDED + task.toTaskString());
    }

    public void printTaskDeleteAcknowledgement(Task task) {
        printMessage(MESSAGE_ACKNOWLEDGE_DELETE_COMMAND + task.toTaskString());
    }

    public void printTaskMarkAcknowledgement(Task task) {
        printMessage(MESSAGE_ACKNOWLEDGE_MARK_COMMAND + task.toTaskString());
    }

    public void printTaskUnmarkAcknowledgement(Task task) {
        printMessage(MESSAGE_ACKNOWLEDGE_UNMARK_COMMAND + task.toTaskString());
    }
}
