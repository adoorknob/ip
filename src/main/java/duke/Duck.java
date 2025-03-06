package duke;

import duke.command.DuckCommand;
import duke.exception.EmptyTaskNameException;
import duke.exception.InvalidCommandException;
import duke.storage.Storage;
import duke.ui.Ui;
import duke.task.TaskList;
import duke.parser.Parser;

import java.io.IOException;
import java.util.*;

/**
 * <h1>Duck, helping you keep your ducks in a row</h1>
 * Duck is a task manager that takes command line input and prints to standard output a list of your tasks, which you can
 * create, read, update and write.
 */

public class Duck {

    public static int BY_COMMAND_BUFFER = 4;
    public static int FROM_COMMAND_BUFFER = 6;
    public static int TO_COMMAND_BUFFER = 4;

    public static final String COMMAND_LIST = "list";
    public static final String COMMAND_MARK = "mark";
    public static final String COMMAND_UNMARK = "unmark";
    public static final String COMMAND_DELETE = "delete";
    public static final String COMMAND_EXIT = "exit";
    public static final ArrayList<String> COMMAND_EXIT_LIST = new ArrayList<>(Arrays.asList(
            "bye",
            "exit",
            "quit"
    ));
    public static final String TASK_NAME_TODO = "todo";
    public static final String TASK_NAME_DEADLINE = "deadline";
    public static final String TASK_NAME_EVENT = "event";

    private final Ui ui;
    private TaskList taskList;
    private boolean isProgramRunning;
    private final Storage storage;

    /**
     * Constructs Duck
     *
     * @param outputFilePath File path to output file to read from/write to
     */
    public Duck(String outputFilePath) {
        ui = new Ui();
        taskList = new TaskList();
        isProgramRunning = true;
        storage = new Storage(outputFilePath);

        try {
            taskList = storage.loadOldFile();
            ui.printList(taskList);
        } catch (IOException e) {
            ui.printFileLoadError();
            taskList = new TaskList();
        }
    }

    /**
     * Runs overall logic of Duck
     */
    private void runDuck() {
        ui.printWelcomeMessage();

        while (isProgramRunning) {
            try {
                DuckCommand command = Parser.getCommand();

                if (command.commandName.isEmpty()) {
                    ui.printInvalidCommandError();
                    continue;
                }

                if (command.commandName.equals(COMMAND_EXIT)) {
                    isProgramRunning = false;
                }

                command.execute(taskList);
                storage.updateFile();
            } catch (InvalidCommandException e) {
                ui.printInvalidCommandError();
            } catch (EmptyTaskNameException e) {
                ui.printEmptyTaskNameError();
            } catch (Exception e) {
                ui.printRandomError();
                System.out.printf(e.getMessage());
            }
        }
    }

    /**
     * Start running Duck
     * @param args Arguments
     */
    public static void main(String[] args) {
        new Duck("src/main/java/duke/data/duckOutput.txt").runDuck();
    }
}
