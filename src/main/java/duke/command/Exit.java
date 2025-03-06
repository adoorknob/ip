package duke.command;

import duke.Duck;
import duke.task.TaskList;

/**
 * Represents the command to exit the program.
 */

public class Exit extends DuckCommand {
    public Exit() {
        commandName = "exit";
    }

    public void execute(TaskList taskList) {
        ui.printExitMessage();
        System.exit(0);
    }
}
