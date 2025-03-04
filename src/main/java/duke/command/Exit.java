package duke.command;

import duke.Duck;
import duke.task.TaskList;

public class Exit extends DuckCommand {
    public Exit() {
        commandName = "exit";
    }

    public void execute(TaskList taskList) {
        ui.printExitMessage();
        System.exit(0);
    }
}
